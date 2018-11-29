/*
 * Copyright 2010-2017 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jetbrains.kotlin.checkers;

import com.google.common.collect.LinkedListMultimap;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiErrorElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.Function;
import com.intellij.util.SmartList;
import com.intellij.util.containers.ContainerUtil;
import com.intellij.util.containers.Stack;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.impl.ModuleDescriptorImpl;
import org.jetbrains.kotlin.diagnostics.*;
import org.jetbrains.kotlin.diagnostics.rendering.AbstractDiagnosticWithParametersRenderer;
import org.jetbrains.kotlin.diagnostics.rendering.DefaultErrorMessages;
import org.jetbrains.kotlin.diagnostics.rendering.DiagnosticRenderer;
import org.jetbrains.kotlin.diagnostics.rendering.Renderers;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtReferenceExpression;
import org.jetbrains.kotlin.resolve.AnalyzingUtils;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.MultiTargetPlatform;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowValueFactory;
import org.jetbrains.kotlin.util.slicedMap.WritableSlice;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckerTestUtil {
    public static final Comparator<ActualDiagnostic> DIAGNOSTIC_COMPARATOR = (o1, o2) -> {
        List<TextRange> ranges1 = o1.diagnostic.getTextRanges();
        List<TextRange> ranges2 = o2.diagnostic.getTextRanges();
        int minNumberOfRanges = ranges1.size() < ranges2.size() ? ranges1.size() : ranges2.size();
        for (int i = 0; i < minNumberOfRanges; i++) {
            TextRange range1 = ranges1.get(i);
            TextRange range2 = ranges2.get(i);
            int startOffset1 = range1.getStartOffset();
            int startOffset2 = range2.getStartOffset();
            if (startOffset1 != startOffset2) {
                // Start early -- go first
                return startOffset1 - range2.getStartOffset();
            }
            int endOffset1 = range1.getEndOffset();
            int endOffset2 = range2.getEndOffset();
            if (endOffset1 != endOffset2) {
                // start at the same offset, the one who end later is the outer, i.e. goes first
                return endOffset2 - endOffset1;
            }
        }
        return ranges1.size() - ranges2.size();
    };

    private static final String IGNORE_DIAGNOSTIC_PARAMETER = "IGNORE";
    private static final String SHOULD_BE_ESCAPED = "\\)\\(;";
    private static final String DIAGNOSTIC_PARAMETER = "(?:(?:\\\\[" + SHOULD_BE_ESCAPED + "])|[^" + SHOULD_BE_ESCAPED + "])+";
    private static final String INDIVIDUAL_DIAGNOSTIC = "(\\w+;)?(\\w+:)?(\\w+)(\\(" + DIAGNOSTIC_PARAMETER + "(;\\s*" + DIAGNOSTIC_PARAMETER + ")*\\))?";
    private static final Pattern RANGE_START_OR_END_PATTERN = Pattern.compile("(<!" +
                                                                              INDIVIDUAL_DIAGNOSTIC + "(,\\s*" +
                                                                              INDIVIDUAL_DIAGNOSTIC + ")*!>)|(<!>)");
    private static final Pattern INDIVIDUAL_DIAGNOSTIC_PATTERN = Pattern.compile(INDIVIDUAL_DIAGNOSTIC);
    private static final Pattern INDIVIDUAL_PARAMETER_PATTERN = Pattern.compile(DIAGNOSTIC_PARAMETER);

    private static final String NEW_INFERENCE_PREFIX = "NI";
    private static final String OLD_INFERENCE_PREFIX = "OI";

    @NotNull
    public static List<ActualDiagnostic> getDiagnosticsIncludingSyntaxErrors(
            @NotNull BindingContext bindingContext,
            @NotNull List<Pair<MultiTargetPlatform, BindingContext>> implementingModulesBindings,
            @NotNull PsiElement root,
            boolean markDynamicCalls,
            @Nullable List<DeclarationDescriptor> dynamicCallDescriptors,
            boolean withNewInference,
            LanguageVersionSettings languageVersionSettings,
            DataFlowValueFactory dataFlowValueFactory,
            ModuleDescriptorImpl moduleDescriptor
    ) {
        List<ActualDiagnostic> result = getDiagnosticsIncludingSyntaxErrors(
                bindingContext,
                root,
                markDynamicCalls,
                dynamicCallDescriptors,
                null,
                withNewInference,
                languageVersionSettings,
                dataFlowValueFactory,
                moduleDescriptor
        );

        List<Pair<MultiTargetPlatform, BindingContext>> sortedBindings = CollectionsKt.sortedWith(
                implementingModulesBindings,
                (o1, o2) -> o1.getFirst().compareTo(o2.getFirst())
        );

        for (Pair<MultiTargetPlatform, BindingContext> binding : sortedBindings) {
            MultiTargetPlatform platform = binding.getFirst();
            assert platform instanceof MultiTargetPlatform.Specific : "Implementing module must have a specific platform: " + platform;

            result.addAll(
                    getDiagnosticsIncludingSyntaxErrors(
                            binding.getSecond(),
                            root,
                            markDynamicCalls,
                            dynamicCallDescriptors,
                            ((MultiTargetPlatform.Specific) platform).getPlatform(),
                            withNewInference,
                            languageVersionSettings,
                            dataFlowValueFactory,
                            moduleDescriptor
                    )
            );
        }

        return result;
    }

    @NotNull
    public static List<ActualDiagnostic> getDiagnosticsIncludingSyntaxErrors(
            @NotNull BindingContext bindingContext,
            @NotNull PsiElement root,
            boolean markDynamicCalls,
            @Nullable List<DeclarationDescriptor> dynamicCallDescriptors,
            @Nullable String platform,
            boolean withNewInference,
            LanguageVersionSettings languageVersionSettings,
            DataFlowValueFactory dataFlowValueFactory,
            ModuleDescriptorImpl moduleDescriptor
    ) {
        List<ActualDiagnostic> diagnostics = new ArrayList<>();
        for (Diagnostic diagnostic : bindingContext.getDiagnostics().all()) {
            if (PsiTreeUtil.isAncestor(root, diagnostic.getPsiElement(), false)) {
                diagnostics.add(new ActualDiagnostic(diagnostic, platform, withNewInference));
            }
        }

        for (PsiErrorElement errorElement : AnalyzingUtils.getSyntaxErrorRanges(root)) {
            diagnostics.add(new ActualDiagnostic(new SyntaxErrorDiagnostic(errorElement), platform, withNewInference));
        }

        diagnostics.addAll(
                getDebugInfoDiagnostics(
                        root,
                        bindingContext,
                        markDynamicCalls,
                        dynamicCallDescriptors,
                        platform,
                        withNewInference,
                        languageVersionSettings,
                        dataFlowValueFactory,
                        moduleDescriptor
                )
        );
        return diagnostics;
    }

    @SuppressWarnings("TestOnlyProblems")
    @NotNull
    private static List<ActualDiagnostic> getDebugInfoDiagnostics(
            @NotNull PsiElement root,
            @NotNull BindingContext bindingContext,
            boolean markDynamicCalls,
            @Nullable List<DeclarationDescriptor> dynamicCallDescriptors,
            @Nullable String platform,
            boolean withNewInference,
            LanguageVersionSettings languageVersionSettings,
            DataFlowValueFactory dataFlowValueFactory,
            ModuleDescriptorImpl moduleDescriptor
    ) {
        List<ActualDiagnostic> debugAnnotations = new ArrayList<>();

        DebugInfoUtil.markDebugAnnotations(root, bindingContext, new DebugInfoUtil.DebugInfoReporter() {
            @Override
            public void reportElementWithErrorType(@NotNull KtReferenceExpression expression) {
                newDiagnostic(expression, DebugInfoDiagnosticFactory0.ELEMENT_WITH_ERROR_TYPE);
            }

            @Override
            public void reportMissingUnresolved(@NotNull KtReferenceExpression expression) {
                newDiagnostic(expression, DebugInfoDiagnosticFactory0.MISSING_UNRESOLVED);
            }

            @Override
            public void reportUnresolvedWithTarget(@NotNull KtReferenceExpression expression, @NotNull String target) {
                newDiagnostic(expression, DebugInfoDiagnosticFactory0.UNRESOLVED_WITH_TARGET);
            }

            @Override
            public void reportDynamicCall(@NotNull KtElement element, DeclarationDescriptor declarationDescriptor) {
                if (dynamicCallDescriptors != null) {
                    dynamicCallDescriptors.add(declarationDescriptor);
                }

                if (markDynamicCalls) {
                    newDiagnostic(element, DebugInfoDiagnosticFactory0.DYNAMIC);
                }
            }

            private void newDiagnostic(KtElement element, DebugInfoDiagnosticFactory0 factory) {
                debugAnnotations.add(new ActualDiagnostic(new DebugInfoDiagnostic(element, factory), platform, withNewInference));
            }
        });

        // this code is used in tests and in internal action 'copy current file as diagnostic test'
        //noinspection unchecked

        List<Pair<? extends WritableSlice<? extends KtExpression, ?>, DebugInfoDiagnosticFactory>> factoryList = Arrays.asList(
                TuplesKt.to(BindingContext.EXPRESSION_TYPE_INFO, DebugInfoDiagnosticFactory1.EXPRESSION_TYPE),
                TuplesKt.to(BindingContext.SMARTCAST, DebugInfoDiagnosticFactory0.SMARTCAST),
                TuplesKt.to(BindingContext.IMPLICIT_RECEIVER_SMARTCAST, DebugInfoDiagnosticFactory0.IMPLICIT_RECEIVER_SMARTCAST),
                TuplesKt.to(BindingContext.SMARTCAST_NULL, DebugInfoDiagnosticFactory0.CONSTANT),
                TuplesKt.to(BindingContext.LEAKING_THIS, DebugInfoDiagnosticFactory0.LEAKING_THIS),
                TuplesKt.to(BindingContext.IMPLICIT_EXHAUSTIVE_WHEN, DebugInfoDiagnosticFactory0.IMPLICIT_EXHAUSTIVE)
        );

        for (Pair<? extends WritableSlice<? extends KtExpression, ?>, ? extends DebugInfoDiagnosticFactory> factory : factoryList) {
            List<? extends KtExpression> test = bindingContext.getSliceContents(factory.getFirst()).keySet().asList();

            for (KtExpression expression : test) {
                if (PsiTreeUtil.isAncestor(root, expression, false)) {
                    Diagnostic diagnostic = factory.getSecond().createDiagnostic(expression, bindingContext, dataFlowValueFactory, languageVersionSettings, moduleDescriptor);
                    ActualDiagnostic t = new ActualDiagnostic(diagnostic, platform, withNewInference);

                    debugAnnotations.add(t);
                }
            }
        }

        return debugAnnotations;
    }

    public interface DiagnosticDiffCallbacks {
        void missingDiagnostic(TextDiagnostic diagnostic, int expectedStart, int expectedEnd);

        void wrongParametersDiagnostic(TextDiagnostic expectedDiagnostic, TextDiagnostic actualDiagnostic, int start, int end);

        void unexpectedDiagnostic(TextDiagnostic diagnostic, int actualStart, int actualEnd);
    }

    public static Map<AbstractTestDiagnostic, TextDiagnostic> diagnosticsDiff(
            List<DiagnosedRange> expected,
            Collection<ActualDiagnostic> actual,
            DiagnosticDiffCallbacks callbacks
    ) {
        Map<AbstractTestDiagnostic, TextDiagnostic> diagnosticToExpectedDiagnostic = new HashMap<>();

        assertSameFile(actual);

        Iterator<DiagnosedRange> expectedDiagnostics = expected.iterator();
        List<ActualDiagnosticDescriptor> sortedDiagnosticDescriptors = getActualSortedDiagnosticDescriptors(actual);
        Iterator<ActualDiagnosticDescriptor> actualDiagnostics = sortedDiagnosticDescriptors.iterator();

        DiagnosedRange currentExpected = safeAdvance(expectedDiagnostics);
        ActualDiagnosticDescriptor currentActual = safeAdvance(actualDiagnostics);
        while (currentExpected != null || currentActual != null) {
            if (currentExpected != null) {
                if (currentActual == null) {
                    missingDiagnostics(callbacks, currentExpected);
                    currentExpected = safeAdvance(expectedDiagnostics);
                }
                else {
                    int expectedStart = currentExpected.getStart();
                    int actualStart = currentActual.getStart();
                    int expectedEnd = currentExpected.getEnd();
                    int actualEnd = currentActual.getEnd();
                    if (expectedStart < actualStart) {
                        missingDiagnostics(callbacks, currentExpected);
                        currentExpected = safeAdvance(expectedDiagnostics);
                    }
                    else if (expectedStart > actualStart) {
                        unexpectedDiagnostics(currentActual, callbacks);
                        currentActual = safeAdvance(actualDiagnostics);
                    }
                    else if (expectedEnd > actualEnd) {
                        assert expectedStart == actualStart;
                        missingDiagnostics(callbacks, currentExpected);
                        currentExpected = safeAdvance(expectedDiagnostics);
                    }
                    else if (expectedEnd < actualEnd) {
                        assert expectedStart == actualStart;
                        unexpectedDiagnostics(currentActual, callbacks);
                        currentActual = safeAdvance(actualDiagnostics);
                    }
                    else {
                        compareDiagnostics(callbacks, currentExpected, currentActual, diagnosticToExpectedDiagnostic);
                        currentExpected = safeAdvance(expectedDiagnostics);
                        currentActual = safeAdvance(actualDiagnostics);
                    }
                }
            } else {
                //noinspection ConstantConditions
                assert (currentActual != null);

                unexpectedDiagnostics(currentActual, callbacks);
                currentActual = safeAdvance(actualDiagnostics);
            }
        }

        return diagnosticToExpectedDiagnostic;
    }

    private static void compareDiagnostics(
            @NotNull DiagnosticDiffCallbacks callbacks,
            @NotNull DiagnosedRange currentExpected,
            @NotNull ActualDiagnosticDescriptor currentActual,
            @NotNull Map<AbstractTestDiagnostic, TextDiagnostic> diagnosticToInput
    ) {
        int expectedStart = currentExpected.getStart();
        int expectedEnd = currentExpected.getEnd();

        int actualStart = currentActual.getStart();
        int actualEnd = currentActual.getEnd();
        assert expectedStart == actualStart && expectedEnd == actualEnd;

        Map<AbstractTestDiagnostic, TextDiagnostic> actualDiagnostics = currentActual.getTextDiagnosticsMap();
        List<TextDiagnostic> expectedDiagnostics = currentExpected.getDiagnostics();
        Set<String> diagnosticNames = new HashSet<>();

        for (TextDiagnostic expectedDiagnostic : expectedDiagnostics) {
            Map.Entry<AbstractTestDiagnostic, TextDiagnostic> actualDiagnosticEntry = CollectionsKt.firstOrNull(
                    actualDiagnostics.entrySet(), entry -> {
                        TextDiagnostic actualDiagnostic = entry.getValue();
                        return expectedDiagnostic.getDescription().equals(actualDiagnostic.getDescription()) &&
                               expectedDiagnostic.inferenceCompatibility.isCompatible(actualDiagnostic.inferenceCompatibility);
                    }
            );

            if (actualDiagnosticEntry != null) {
                AbstractTestDiagnostic actualDiagnostic = actualDiagnosticEntry.getKey();
                TextDiagnostic actualTextDiagnostic = actualDiagnosticEntry.getValue();

                if (!compareTextDiagnostic(expectedDiagnostic, actualTextDiagnostic)) {
                    callbacks.wrongParametersDiagnostic(expectedDiagnostic, actualTextDiagnostic, expectedStart, expectedEnd);
                }

                actualDiagnostics.remove(actualDiagnostic);
                diagnosticNames.add(actualDiagnostic.getName());
                actualDiagnostic.enhanceInferenceCompatibility(expectedDiagnostic.inferenceCompatibility);

                diagnosticToInput.put(actualDiagnostic, expectedDiagnostic);
            } else {
                callbacks.missingDiagnostic(expectedDiagnostic, expectedStart, expectedEnd);
            }
        }

        for (AbstractTestDiagnostic unexpectedDiagnostic : actualDiagnostics.keySet()) {
            TextDiagnostic textDiagnostic = actualDiagnostics.get(unexpectedDiagnostic);

            if (hasExplicitDefinitionOnlyOption(unexpectedDiagnostic) && !diagnosticNames.contains(unexpectedDiagnostic.getName()))
                continue;

            callbacks.unexpectedDiagnostic(textDiagnostic, actualStart, actualEnd);
        }
    }

    private static boolean compareTextDiagnostic(@NotNull TextDiagnostic expected, @NotNull TextDiagnostic actual) {
        if (!expected.getDescription().equals(actual.getDescription())) return false;

        if (expected.getParameters() == null) return true;
        if (actual.getParameters() == null || expected.getParameters().size() != actual.getParameters().size()) return false;

        for (int index = 0; index < expected.getParameters().size(); index++) {
            String expectedParameter = expected.getParameters().get(index);
            String actualParameter = actual.getParameters().get(index);
            if (!expectedParameter.equals(IGNORE_DIAGNOSTIC_PARAMETER) && !expectedParameter.equals(actualParameter)) {
                return false;
            }
        }
        return true;
    }

    private static void assertSameFile(Collection<ActualDiagnostic> actual) {
        if (actual.isEmpty()) return;
        PsiFile file = CollectionsKt.first(actual).getFile();
        for (ActualDiagnostic actualDiagnostic : actual) {
            assert actualDiagnostic.getFile().equals(file)
                    : "All diagnostics should come from the same file: " + actualDiagnostic.getFile() + ", " + file;
        }
    }

    private static void unexpectedDiagnostics(ActualDiagnosticDescriptor descriptor, DiagnosticDiffCallbacks callbacks) {
        for (AbstractTestDiagnostic diagnostic : descriptor.diagnostics) {
            if (hasExplicitDefinitionOnlyOption(diagnostic))
                continue;

            callbacks.unexpectedDiagnostic(TextDiagnostic.asTextDiagnostic(diagnostic), descriptor.getStart(), descriptor.getEnd());
        }
    }

    private static void missingDiagnostics(DiagnosticDiffCallbacks callbacks, DiagnosedRange currentExpected) {
        for (TextDiagnostic diagnostic : currentExpected.getDiagnostics()) {
            callbacks.missingDiagnostic(diagnostic, currentExpected.getStart(), currentExpected.getEnd());
        }
    }

    private static <T> T safeAdvance(Iterator<T> iterator) {
        return iterator.hasNext() ? iterator.next() : null;
    }

    public static String parseDiagnosedRanges(String text, List<DiagnosedRange> result) {
        Matcher matcher = RANGE_START_OR_END_PATTERN.matcher(text);

        Stack<DiagnosedRange> opened = new Stack<>();

        int offsetCompensation = 0;

        while (matcher.find()) {
            int effectiveOffset = matcher.start() - offsetCompensation;
            String matchedText = matcher.group();
            if ("<!>".equals(matchedText)) {
                opened.pop().setEnd(effectiveOffset);
            }
            else {
                Matcher diagnosticTypeMatcher = INDIVIDUAL_DIAGNOSTIC_PATTERN.matcher(matchedText);
                DiagnosedRange range = new DiagnosedRange(effectiveOffset);
                while (diagnosticTypeMatcher.find()) {
                    range.addDiagnostic(diagnosticTypeMatcher.group());
                }
                opened.push(range);
                result.add(range);
            }
            offsetCompensation += matchedText.length();
        }

        assert opened.isEmpty() : "Stack is not empty";

        matcher.reset();
        return matcher.replaceAll("");
    }

    private static boolean hasExplicitDefinitionOnlyOption(AbstractTestDiagnostic diagnostic) {
        if (!(diagnostic instanceof ActualDiagnostic))
            return false;
        DiagnosticFactory factory = ((ActualDiagnostic) diagnostic).diagnostic.getFactory();
        return factory instanceof DebugInfoDiagnosticFactory && ((DebugInfoDiagnosticFactory)factory).getWithExplicitDefinitionOnly();
    }

    public static StringBuffer addDiagnosticMarkersToText(@NotNull PsiFile psiFile, @NotNull Collection<ActualDiagnostic> diagnostics) {
        return addDiagnosticMarkersToText(psiFile, diagnostics, Collections.emptyMap(), PsiElement::getText, Collections.emptyList(), false);
    }

    public static StringBuffer addDiagnosticMarkersToText(
            @NotNull PsiFile psiFile,
            @NotNull Collection<ActualDiagnostic> diagnostics,
            @NotNull Map<AbstractTestDiagnostic, TextDiagnostic> diagnosticToExpectedDiagnostic,
            @NotNull Function<PsiFile, String> getFileText,
            @NotNull Collection<PositionalTextDiagnostic> uncheckedDiagnostics,
            boolean withNewInferenceDirective
    ) {
        String text = getFileText.fun(psiFile);
        StringBuffer result = new StringBuffer();
        diagnostics = CollectionsKt.filter(diagnostics, actualDiagnostic -> psiFile.equals(actualDiagnostic.getFile()));
        if (diagnostics.isEmpty() && uncheckedDiagnostics.isEmpty()) {
            result.append(text);
            return result;
        }

        List<AbstractDiagnosticDescriptor> diagnosticDescriptors = getSortedDiagnosticDescriptors(diagnostics, uncheckedDiagnostics);

        Stack<AbstractDiagnosticDescriptor> opened = new Stack<>();
        ListIterator<AbstractDiagnosticDescriptor> iterator = diagnosticDescriptors.listIterator();
        AbstractDiagnosticDescriptor currentDescriptor = iterator.next();

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            while (!opened.isEmpty() && i == opened.peek().end) {
                closeDiagnosticString(result);
                opened.pop();
            }
            while (currentDescriptor != null && i == currentDescriptor.start) {
                boolean isSkip = openDiagnosticsString(result, currentDescriptor, diagnosticToExpectedDiagnostic, withNewInferenceDirective);

                if (currentDescriptor.getEnd() == i && !isSkip) {
                    closeDiagnosticString(result);
                } else if (!isSkip) {
                    opened.push(currentDescriptor);
                }
                if (iterator.hasNext()) {
                    currentDescriptor = iterator.next();
                }
                else {
                    currentDescriptor = null;
                }
            }
            result.append(c);
        }

        if (currentDescriptor != null) {
            assert currentDescriptor.start == text.length();
            assert currentDescriptor.end == text.length();
            boolean isSkip = openDiagnosticsString(result, currentDescriptor, diagnosticToExpectedDiagnostic, withNewInferenceDirective);

            if (!isSkip)
                opened.push(currentDescriptor);
        }

        while (!opened.isEmpty() && text.length() == opened.peek().end) {
            closeDiagnosticString(result);
            opened.pop();
        }

        assert opened.isEmpty() : "Stack is not empty: " + opened;

        return result;
    }

    private static boolean openDiagnosticsString(
            StringBuffer result,
            AbstractDiagnosticDescriptor currentDescriptor,
            Map<AbstractTestDiagnostic, TextDiagnostic> diagnosticToExpectedDiagnostic,
            boolean withNewInferenceDirective
    ) {
        boolean isSkip = true;
        List<String> diagnosticsAsText = new ArrayList<>();

        if (currentDescriptor instanceof TextDiagnosticDescriptor) {
            diagnosticsAsText.add(((TextDiagnosticDescriptor) currentDescriptor).getTextDiagnostic().asString());
        } else if (currentDescriptor instanceof ActualDiagnosticDescriptor) {
            List<AbstractTestDiagnostic> diagnostics = ((ActualDiagnosticDescriptor) currentDescriptor).getDiagnostics();

            for (AbstractTestDiagnostic diagnostic : diagnostics) {
                TextDiagnostic expectedDiagnostic = diagnosticToExpectedDiagnostic.get(diagnostic);
                if (expectedDiagnostic != null) {
                    TextDiagnostic actualTextDiagnostic = TextDiagnostic.asTextDiagnostic(diagnostic);
                    if (compareTextDiagnostic(expectedDiagnostic, actualTextDiagnostic)) {
                        diagnosticsAsText.add(expectedDiagnostic.asString());
                    } else {
                        diagnosticsAsText.add(actualTextDiagnostic.asString());
                    }
                } else if (!hasExplicitDefinitionOnlyOption(diagnostic)) {
                    StringBuilder diagnosticText = new StringBuilder();
                    if (withNewInferenceDirective && diagnostic.getInferenceCompatibility().abbreviation != null) {
                        diagnosticText.append(diagnostic.getInferenceCompatibility().abbreviation);
                        diagnosticText.append(";");
                    }
                    if (diagnostic.getPlatform() != null) {
                        diagnosticText.append(diagnostic.getPlatform());
                        diagnosticText.append(":");
                    }
                    diagnosticsAsText.add(diagnosticText + diagnostic.getName());
                }
            }
        } else {
            throw new IllegalStateException("Unknown diagnostic descriptor: " + currentDescriptor);
        }

        if (diagnosticsAsText.size() != 0) {
            Collections.sort(diagnosticsAsText);
            result.append("<!");
            result.append(String.join(", ", diagnosticsAsText));
            result.append("!>");
            isSkip = false;
        }

        return isSkip;
    }

    private static void closeDiagnosticString(StringBuffer result) {
        result.append("<!>");
    }

    public static class AbstractDiagnosticForTests implements Diagnostic {
        private final PsiElement element;
        private final DiagnosticFactory<?> factory;

        public AbstractDiagnosticForTests(@NotNull PsiElement element, @NotNull DiagnosticFactory<?> factory) {
            this.element = element;
            this.factory = factory;
        }

        @NotNull
        @Override
        public DiagnosticFactory<?> getFactory() {
            return factory;
        }

        @NotNull
        @Override
        public Severity getSeverity() {
            return Severity.ERROR;
        }

        @NotNull
        @Override
        public PsiElement getPsiElement() {
            return element;
        }

        @NotNull
        @Override
        public List<TextRange> getTextRanges() {
            return Collections.singletonList(element.getTextRange());
        }

        @NotNull
        @Override
        public PsiFile getPsiFile() {
            return element.getContainingFile();
        }

        @Override
        public boolean isValid() {
            return true;
        }
    }

    public static class SyntaxErrorDiagnosticFactory extends DiagnosticFactory<SyntaxErrorDiagnostic> {
        public static final SyntaxErrorDiagnosticFactory INSTANCE = new SyntaxErrorDiagnosticFactory();

        private SyntaxErrorDiagnosticFactory() {
            super(Severity.ERROR);
        }

        @NotNull
        @Override
        public String getName() {
            return "SYNTAX";
        }
    }

    public static class SyntaxErrorDiagnostic extends AbstractDiagnosticForTests {
        public SyntaxErrorDiagnostic(@NotNull PsiErrorElement errorElement) {
            super(errorElement, SyntaxErrorDiagnosticFactory.INSTANCE);
        }
    }

    interface DebugInfoDiagnosticFactory {
        boolean getWithExplicitDefinitionOnly();

        Diagnostic createDiagnostic(
                KtExpression expression,
                BindingContext bindingContext,
                DataFlowValueFactory dataFlowValueFactory,
                LanguageVersionSettings languageVersionSettings,
                ModuleDescriptorImpl moduleDescriptor
        );
    }

    public static class DebugInfoDiagnosticFactory0 extends DiagnosticFactory0<PsiElement> implements DebugInfoDiagnosticFactory {
        public static final DebugInfoDiagnosticFactory0 SMARTCAST = new DebugInfoDiagnosticFactory0("SMARTCAST", Severity.INFO);
        public static final DebugInfoDiagnosticFactory0
                IMPLICIT_RECEIVER_SMARTCAST = new DebugInfoDiagnosticFactory0("IMPLICIT_RECEIVER_SMARTCAST", Severity.INFO);
        public static final DebugInfoDiagnosticFactory0 CONSTANT = new DebugInfoDiagnosticFactory0("CONSTANT", Severity.INFO);
        public static final DebugInfoDiagnosticFactory0 LEAKING_THIS = new DebugInfoDiagnosticFactory0("LEAKING_THIS");
        public static final DebugInfoDiagnosticFactory0
                IMPLICIT_EXHAUSTIVE = new DebugInfoDiagnosticFactory0("IMPLICIT_EXHAUSTIVE", Severity.INFO);
        public static final DebugInfoDiagnosticFactory0 ELEMENT_WITH_ERROR_TYPE = new DebugInfoDiagnosticFactory0("ELEMENT_WITH_ERROR_TYPE");
        public static final DebugInfoDiagnosticFactory0 UNRESOLVED_WITH_TARGET = new DebugInfoDiagnosticFactory0("UNRESOLVED_WITH_TARGET");
        public static final DebugInfoDiagnosticFactory0 MISSING_UNRESOLVED = new DebugInfoDiagnosticFactory0("MISSING_UNRESOLVED");
        public static final DebugInfoDiagnosticFactory0 DYNAMIC = new DebugInfoDiagnosticFactory0("DYNAMIC", Severity.INFO);

        private final String name;
        private final boolean withExplicitDefinitionOnly;

        @Override
        public Diagnostic createDiagnostic(
                KtExpression expression,
                BindingContext bindingContext,
                DataFlowValueFactory dataFlowValueFactory,
                LanguageVersionSettings languageVersionSettings,
                ModuleDescriptorImpl moduleDescriptor
        ) {
            return new DebugInfoDiagnostic(expression, this);
        }

        @Override
        public boolean getWithExplicitDefinitionOnly() {
            return withExplicitDefinitionOnly;
        }

        private DebugInfoDiagnosticFactory0(String name, Severity severity) {
            super(severity, PositioningStrategies.DEFAULT);
            this.name = name;
            this.withExplicitDefinitionOnly = false;
        }

        private DebugInfoDiagnosticFactory0(String name, Severity severity, boolean withExplicitDefinitionOnly) {
            super(severity, PositioningStrategies.DEFAULT);
            this.name = name;
            this.withExplicitDefinitionOnly = withExplicitDefinitionOnly;
        }

        private DebugInfoDiagnosticFactory0(String name) {
            this(name, Severity.ERROR);
        }

        @NotNull
        @Override
        public String getName() {
            return "DEBUG_INFO_" + name;
        }
    }

    public static class DebugInfoDiagnosticFactory1 extends DiagnosticFactory1<PsiElement, String> implements DebugInfoDiagnosticFactory {
        public static final DebugInfoDiagnosticFactory1 EXPRESSION_TYPE =
                create("EXPRESSION_TYPE", Severity.INFO, true);

        final private String name;
        final private Boolean withExplicitDefinitionOnly;

        @Override
        public Diagnostic createDiagnostic(
                KtExpression expression,
                BindingContext bindingContext,
                DataFlowValueFactory dataFlowValueFactory,
                LanguageVersionSettings languageVersionSettings,
                ModuleDescriptorImpl moduleDescriptor
        ) {
            return this.on(
                    expression,
                    "\"" + Renderers.renderExpressionType(expression, bindingContext, dataFlowValueFactory, languageVersionSettings, moduleDescriptor) + "\""
            );
        }

        @NotNull
        @Override
        public String getName() {
            return "DEBUG_INFO_" + name;
        }

        @Override
        public boolean getWithExplicitDefinitionOnly() {
            return withExplicitDefinitionOnly;
        }

        protected DebugInfoDiagnosticFactory1(String name, Severity severity) {
            super(severity, PositioningStrategies.DEFAULT);
            this.name = name;
            this.withExplicitDefinitionOnly = false;
        }

        protected DebugInfoDiagnosticFactory1(String name, Severity severity, boolean withExplicitDefinitionOnly) {
            super(severity, PositioningStrategies.DEFAULT);
            this.name = name;
            this.withExplicitDefinitionOnly = withExplicitDefinitionOnly;
        }

        public static DebugInfoDiagnosticFactory1 create(String name, Severity severity) {
            return new DebugInfoDiagnosticFactory1(name, severity);
        }

        public static DebugInfoDiagnosticFactory1 create(String name, Severity severity, boolean withExplicitDefinitionOnly) {
            return new DebugInfoDiagnosticFactory1(name, severity, withExplicitDefinitionOnly);
        }
    }

    public static class DebugInfoDiagnostic extends AbstractDiagnosticForTests {
        public DebugInfoDiagnostic(@NotNull KtElement element, @NotNull DiagnosticFactory factory) {
            super(element, factory);
        }
    }

    private static List<ActualDiagnosticDescriptor> getActualSortedDiagnosticDescriptors(
            @NotNull Collection<ActualDiagnostic> diagnostics
    ) {
        return CollectionsKt.filterIsInstance(
                getSortedDiagnosticDescriptors(diagnostics, Collections.emptyList()),
                ActualDiagnosticDescriptor.class
        );
    }

    @NotNull
    private static List<AbstractDiagnosticDescriptor> getSortedDiagnosticDescriptors(
            @NotNull Collection<ActualDiagnostic> diagnostics,
            @NotNull Collection<PositionalTextDiagnostic> uncheckedDiagnostics
    ) {
        List<ActualDiagnostic> validDiagnostics = CollectionsKt.filter(diagnostics, actualDiagnostic -> actualDiagnostic.diagnostic.isValid());
        List<AbstractDiagnosticDescriptor> diagnosticDescriptors = groupDiagnosticsByTextRange(validDiagnostics, uncheckedDiagnostics);
        diagnosticDescriptors.sort((d1, d2) -> (d1.start != d2.start) ? d1.start - d2.start : d2.end - d1.end);
        return diagnosticDescriptors;
    }

    @NotNull
    private static List<AbstractDiagnosticDescriptor> groupDiagnosticsByTextRange(
            @NotNull Collection<ActualDiagnostic> diagnostics,
            @NotNull Collection<PositionalTextDiagnostic> uncheckedDiagnostics
    ) {
        LinkedListMultimap<TextRange, AbstractTestDiagnostic> diagnosticsGroupedByRanges = LinkedListMultimap.create();
        for (ActualDiagnostic actualDiagnostic : diagnostics) {
            Diagnostic diagnostic = actualDiagnostic.diagnostic;
            for (TextRange textRange : diagnostic.getTextRanges()) {
                diagnosticsGroupedByRanges.put(textRange, actualDiagnostic);
            }
        }

        for (PositionalTextDiagnostic uncheckedDiagnostic : uncheckedDiagnostics) {
            TextRange range = new TextRange(uncheckedDiagnostic.getStart(), uncheckedDiagnostic.getEnd());
            diagnosticsGroupedByRanges.put(range, uncheckedDiagnostic.getDiagnostic());
        }

        return CollectionsKt.map(diagnosticsGroupedByRanges.keySet(), range -> {
            List<AbstractTestDiagnostic> abstractDiagnostics = diagnosticsGroupedByRanges.get(range);

            Comparator<AbstractTestDiagnostic> comparator = Comparator.comparing(AbstractTestDiagnostic::getInferenceCompatibility);
            boolean needSortingByName = CollectionsKt.any(
                    abstractDiagnostics,
                    diagnostic -> diagnostic.getInferenceCompatibility() != TextDiagnostic.InferenceCompatibility.ALL
            );
            if (needSortingByName) {
                comparator = comparator.thenComparing(Comparator.comparing(AbstractTestDiagnostic::getName));
            }

            abstractDiagnostics.sort(comparator);

            return new ActualDiagnosticDescriptor(range.getStartOffset(), range.getEndOffset(), abstractDiagnostics);
        });
    }

    private static abstract class AbstractDiagnosticDescriptor {
        private final int start;
        private final int end;

        AbstractDiagnosticDescriptor(int start, int end) {
            this.start = start;
            this.end = end;
        }

        public int getStart() {
            return start;
        }

        public int getEnd() {
            return end;
        }

        public TextRange getTextRange() {
            return new TextRange(start, end);
        }
    }

    private static class ActualDiagnosticDescriptor extends AbstractDiagnosticDescriptor {
        private final List<AbstractTestDiagnostic> diagnostics;

        ActualDiagnosticDescriptor(int start, int end, List<AbstractTestDiagnostic> diagnostics) {
            super(start, end);
            this.diagnostics = diagnostics;
        }

        public List<AbstractTestDiagnostic> getDiagnostics() {
            return diagnostics;
        }

        public Map<AbstractTestDiagnostic, TextDiagnostic> getTextDiagnosticsMap() {
            Map<AbstractTestDiagnostic, TextDiagnostic> diagnosticMap = new TreeMap<>();
            for (AbstractTestDiagnostic diagnostic : diagnostics) {
                diagnosticMap.put(diagnostic, TextDiagnostic.asTextDiagnostic(diagnostic));
            }

            return diagnosticMap;
        }
    }

    private static class TextDiagnosticDescriptor extends AbstractDiagnosticDescriptor {
        private final PositionalTextDiagnostic positionalTextDiagnostic;

        TextDiagnosticDescriptor(PositionalTextDiagnostic positionalTextDiagnostic) {
            super(positionalTextDiagnostic.getStart(), positionalTextDiagnostic.getEnd());
            this.positionalTextDiagnostic = positionalTextDiagnostic;
        }

        public TextDiagnostic getTextDiagnostic() {
            return positionalTextDiagnostic.getDiagnostic();
        }
    }

    public interface AbstractTestDiagnostic extends Comparable<AbstractTestDiagnostic> {
        String getName();

        String getPlatform();

        TextDiagnostic.InferenceCompatibility getInferenceCompatibility();

        void enhanceInferenceCompatibility(TextDiagnostic.InferenceCompatibility inferenceCompatibility);

        @Override
        int compareTo(@NotNull AbstractTestDiagnostic diagnostic);
    }

    public static class ActualDiagnostic implements AbstractTestDiagnostic {
        public final Diagnostic diagnostic;
        public final String platform;
        public TextDiagnostic.InferenceCompatibility inferenceCompatibility;

        @Override
        public int compareTo(@NotNull AbstractTestDiagnostic diagnostic) {
            if (this.diagnostic instanceof DiagnosticWithParameters1 && diagnostic instanceof ActualDiagnostic && ((ActualDiagnostic) diagnostic).diagnostic instanceof DiagnosticWithParameters1) {
                return (getName() + ((DiagnosticWithParameters1) this.diagnostic).getA()).compareTo(diagnostic.getName() + ((DiagnosticWithParameters1) ((ActualDiagnostic) diagnostic).diagnostic).getA());
            } else if (this.diagnostic instanceof DiagnosticWithParameters1) {
                return (getName() + ((DiagnosticWithParameters1) this.diagnostic).getA()).compareTo(diagnostic.getName());
            } else if (diagnostic instanceof ActualDiagnostic && ((ActualDiagnostic) diagnostic).diagnostic instanceof DiagnosticWithParameters1) {
                return getName().compareTo(diagnostic.getName() + ((DiagnosticWithParameters1) ((ActualDiagnostic) diagnostic).diagnostic).getA());
            } else {
                return getName().compareTo(diagnostic.getName());
            }
        }

        ActualDiagnostic(@NotNull Diagnostic diagnostic, @Nullable String platform, boolean withNewInference) {
            this.diagnostic = diagnostic;
            this.platform = platform;
            this.inferenceCompatibility = withNewInference ?
                                          TextDiagnostic.InferenceCompatibility.NEW :
                                          TextDiagnostic.InferenceCompatibility.OLD;
        }

        @Override
        @NotNull
        public String getName() {
            return diagnostic.getFactory().getName();
        }

        @Override
        public String getPlatform() {
            return platform;
        }

        @NotNull
        public PsiFile getFile() {
            return diagnostic.getPsiFile();
        }

        @Override
        public TextDiagnostic.InferenceCompatibility getInferenceCompatibility() {
            return inferenceCompatibility;
        }

        @Override
        public void enhanceInferenceCompatibility(TextDiagnostic.InferenceCompatibility inferenceCompatibility) {
            this.inferenceCompatibility = inferenceCompatibility;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof ActualDiagnostic)) return false;

            ActualDiagnostic other = (ActualDiagnostic) obj;
            // '==' on diagnostics is intentional here
            return other.diagnostic == diagnostic &&
                   (other.platform == null ? platform == null : other.platform.equals(platform)) &&
                   (other.inferenceCompatibility == inferenceCompatibility);
        }

        @Override
        public int hashCode() {
            int result = System.identityHashCode(diagnostic);
            result = 31 * result + (platform != null ? platform.hashCode() : 0);
            result = 31 * result + inferenceCompatibility.hashCode();
            return result;
        }

        @Override
        public String toString() {
            String inferenceAbbreviation = inferenceCompatibility.abbreviation;
            return (inferenceAbbreviation != null ? inferenceAbbreviation + ";" : "") +
                   (platform != null ? platform + ":" : "") +
                   diagnostic.toString();
        }
    }

    public static class TextDiagnostic implements AbstractTestDiagnostic {
        public enum InferenceCompatibility {
            NEW(NEW_INFERENCE_PREFIX), OLD(OLD_INFERENCE_PREFIX), ALL(null);

            @Nullable String abbreviation;

            InferenceCompatibility(@Nullable String abbreviation) {
                this.abbreviation = abbreviation;
            }

            public boolean isCompatible(InferenceCompatibility other) {
                return this == other || this == ALL || other == ALL;
            }
        }

        @Override
        public int compareTo(@NotNull AbstractTestDiagnostic diagnostic) {
            return getName().compareTo(diagnostic.getName());
        }

        @NotNull
        private static TextDiagnostic parseDiagnostic(String text) {
            Matcher matcher = INDIVIDUAL_DIAGNOSTIC_PATTERN.matcher(text);
            if (!matcher.find())
                throw new IllegalArgumentException("Could not parse diagnostic: " + text);

            InferenceCompatibility inference = computeInferenceCompatibility(extractDataBefore(matcher.group(1), ";"));
            String platform = extractDataBefore(matcher.group(2), ":");

            String name = matcher.group(3);
            String parameters = matcher.group(4);
            if (parameters == null) {
                return new TextDiagnostic(name, platform, null, inference);
            }

            List<String> parsedParameters = new SmartList<>();
            Matcher parametersMatcher = INDIVIDUAL_PARAMETER_PATTERN.matcher(parameters);
            while (parametersMatcher.find())
                parsedParameters.add(unescape(parametersMatcher.group().trim()));
            return new TextDiagnostic(name, platform, parsedParameters, inference);
        }

        private static InferenceCompatibility computeInferenceCompatibility(@Nullable String abbreviation) {
            if (abbreviation == null) return InferenceCompatibility.ALL;
            return ArraysKt.single(InferenceCompatibility.values(), inference -> abbreviation.equals(inference.abbreviation));
        }

        private static String extractDataBefore(@Nullable String prefix, @NotNull String anchor) {
            assert prefix == null || prefix.endsWith(anchor) : prefix;
            return prefix == null ? null : StringsKt.substringBeforeLast(prefix, anchor, prefix);
        }

        private static @NotNull String escape(@NotNull String s) {
            return s.replaceAll("([" + SHOULD_BE_ESCAPED + "])", "\\\\$1");
        }

        private static @NotNull String unescape(@NotNull String s) {
            return s.replaceAll("\\\\([" + SHOULD_BE_ESCAPED + "])", "$1");
        }

        public static TextDiagnostic asTextDiagnostic(@NotNull AbstractTestDiagnostic abstractTestDiagnostic) {
            if (abstractTestDiagnostic instanceof ActualDiagnostic) {
                return asTextDiagnostic((ActualDiagnostic) abstractTestDiagnostic);
            }

            return (TextDiagnostic) abstractTestDiagnostic;
        }

        @NotNull
        @SuppressWarnings("unchecked")
        public static TextDiagnostic asTextDiagnostic(@NotNull ActualDiagnostic actualDiagnostic) {
            Diagnostic diagnostic = actualDiagnostic.diagnostic;
            //noinspection TestOnlyProblems
            DiagnosticRenderer renderer = DefaultErrorMessages.getRendererForDiagnostic(diagnostic);
            String diagnosticName = actualDiagnostic.getName();
            if (renderer instanceof AbstractDiagnosticWithParametersRenderer) {
                Object[] renderParameters = ((AbstractDiagnosticWithParametersRenderer) renderer).renderParameters(diagnostic);
                List<String> parameters = ContainerUtil.map(renderParameters, Object::toString);
                return new TextDiagnostic(diagnosticName, actualDiagnostic.platform, parameters, actualDiagnostic.inferenceCompatibility);
            }
            return new TextDiagnostic(diagnosticName, actualDiagnostic.platform, null, actualDiagnostic.inferenceCompatibility);
        }

        @NotNull
        private final String name;
        @Nullable
        private final String platform;
        @Nullable
        private final List<String> parameters;
        @NotNull
        private InferenceCompatibility inferenceCompatibility;

        public TextDiagnostic(
                @NotNull String name,
                @Nullable String platform,
                @Nullable List<String> parameters,
                @Nullable InferenceCompatibility inference
        ) {
            this.name = name;
            this.platform = platform;
            this.parameters = parameters;
            this.inferenceCompatibility = inference != null ? inference : InferenceCompatibility.ALL;
        }

        @NotNull
        @Override
        public String getName() {
            return name;
        }

        @Override
        @Nullable
        public String getPlatform() {
            return platform;
        }

        @NotNull
        public String getDescription() {
            return (platform != null ? platform + ":" : "") + name;
        }

        @Nullable
        public List<String> getParameters() {
            return parameters;
        }

        @NotNull
        @Override
        public InferenceCompatibility getInferenceCompatibility() {
            return inferenceCompatibility;
        }

        @Override
        public void enhanceInferenceCompatibility(InferenceCompatibility inferenceCompatibility) {
            this.inferenceCompatibility = inferenceCompatibility;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            TextDiagnostic that = (TextDiagnostic) o;

            if (!name.equals(that.name)) return false;
            if (platform != null ? !platform.equals(that.platform) : that.platform != null) return false;
            if (parameters != null ? !parameters.equals(that.parameters) : that.parameters != null) return false;
            if (inferenceCompatibility != that.inferenceCompatibility) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = name.hashCode();
            result = 31 * result + (platform != null ? platform.hashCode() : 0);
            result = 31 * result + (parameters != null ? parameters.hashCode() : 0);
            result = 31 * result + inferenceCompatibility.hashCode();
            return result;
        }

        @NotNull
        public String asString() {
            StringBuilder result = new StringBuilder();
            if (inferenceCompatibility.abbreviation != null) {
                result.append(inferenceCompatibility.abbreviation);
                result.append(";");
            }
            if (platform != null) {
                result.append(platform);
                result.append(":");
            }
            result.append(name);
            if (parameters != null) {
                result.append("(");
                result.append(StringUtil.join(parameters, TextDiagnostic::escape, "; "));
                result.append(")");
            }
            return result.toString();
        }

        @Override
        public String toString() {
            return asString();
        }
    }

    public static class DiagnosedRange {
        private final int start;
        private int end;
        private final List<TextDiagnostic> diagnostics = ContainerUtil.newSmartList();

        protected DiagnosedRange(int start) {
            this.start = start;
        }

        public int getStart() {
            return start;
        }

        public int getEnd() {
            return end;
        }

        public List<TextDiagnostic> getDiagnostics() {
            return diagnostics;
        }

        public void setEnd(int end) {
            this.end = end;
        }

        public void addDiagnostic(String diagnostic) {
            diagnostics.add(TextDiagnostic.parseDiagnostic(diagnostic));
        }
    }
}
