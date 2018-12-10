/*
 * Copyright 2010-2018 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license
 * that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.ir.backend.js

import org.jetbrains.kotlin.backend.common.*
import org.jetbrains.kotlin.backend.common.lower.*
import org.jetbrains.kotlin.ir.backend.js.lower.*
import org.jetbrains.kotlin.ir.backend.js.lower.calls.CallsLowering
import org.jetbrains.kotlin.ir.backend.js.lower.coroutines.CoroutineIntrinsicLowering
import org.jetbrains.kotlin.ir.backend.js.lower.coroutines.SuspendFunctionsLowering
import org.jetbrains.kotlin.ir.backend.js.lower.inline.FunctionInlining
import org.jetbrains.kotlin.ir.backend.js.lower.inline.RemoveInlineFunctionsWithReifiedTypeParametersLowering
import org.jetbrains.kotlin.ir.backend.js.lower.inline.ReturnableBlockLowering
import org.jetbrains.kotlin.ir.backend.js.lower.inline.replaceUnboundSymbols
import org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.IrModuleToJsTransformer
import org.jetbrains.kotlin.ir.declarations.IrFile
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment
import org.jetbrains.kotlin.ir.util.deepCopyWithSymbols
import org.jetbrains.kotlin.ir.util.patchDeclarationParents

private fun DeclarationContainerLoweringPass.runOnFilesPostfix(files: Iterable<IrFile>) = files.forEach { runOnFilePostfix(it) }

private fun ClassLoweringPass.runOnFilesPostfix(moduleFragment: IrModuleFragment) = moduleFragment.files.forEach { runOnFilePostfix(it) }

object IrModuleStartPhase : CompilerPhase<JsIrBackendContext, IrModuleFragment> {
    override val name = "IrModuleFragment"
    override val description = "State at start of IrModuleFragment lowering"
    override val prerequisite = emptySet()
    override fun invoke(
            manager: CompilerPhaseManager<JsIrBackendContext, IrModuleFragment>,
            input: IrModuleFragment
    ) = input
}

private fun makeJsModulePhase(
    lowering: (JsIrBackendContext) -> FileLoweringPass,
    description: String,
    name: String,
    prerequisite: Set<CompilerPhase<JsIrBackendContext, IrModuleFragment>> = emptySet()
) = makeModuleLoweringPhase(lowering, description, name, prerequisite)

private fun makeJsPhase(
    op: CompilerPhaseManager<JsIrBackendContext, IrModuleFragment>.(IrModuleFragment) -> Unit,
    description: String,
    name: String,
    prerequisite: Set<CompilerPhase<JsIrBackendContext, IrModuleFragment>> = emptySet()
) = makePhase<JsIrBackendContext, IrModuleFragment>(op, description, name, prerequisite)

private val MoveExternalDeclarationsToSeparatePlacePhase = makeJsModulePhase(
    { MoveExternalDeclarationsToSeparatePlace() },
    name = "MoveExternalDeclarationsToSeparatePlace",
    description = "Move `external` declarations into separate place to make the following lowerings do not care about them"
)

private val ExpectDeclarationsRemovingPhase = makeJsModulePhase(
    ::ExpectDeclarationsRemoving,
    name = "ExpectDeclarationsRemoving",
    description = "Remove expect declaration from module fragment"
)

private val CoroutineIntrinsicLoweringPhase = makeJsModulePhase(
    ::CoroutineIntrinsicLowering,
    name = "CoroutineIntrinsicLowering",
    description = "Replace common coroutine intrinsics with platform specific ones"
)

private val ArrayInlineConstructorLoweringPhase = makeJsModulePhase(
    ::ArrayInlineConstructorLowering,
    name = "ArrayInlineConstructorLowering",
    description = "Replace array constructor with platform specific factory functions"
)

private val LateinitLoweringPhase = makePhase<JsIrBackendContext, IrModuleFragment>(
    { module -> LateinitLowering(context, true).lower(module) },
    name = "LateinitLowering",
    description = "Insert checks for lateinit field references"
)

private val ModuleCopyingPhase = makePhase<JsIrBackendContext, IrModuleFragment>(
    { module -> context.moduleFragmentCopy = module.deepCopyWithSymbols() },
    name = "ModuleCopying",
    description = "<Supposed to be removed> Copy current module to make it accessible from different one",
    prerequisite = setOf(LateinitLoweringPhase)
)

private val FunctionInliningPhase = makeJsPhase(
    { module ->
        FunctionInlining(context).inline(module)
        module.replaceUnboundSymbols(context)
        module.patchDeclarationParents()
    },
    name = "FunctionInliningPhase",
    description = "Perform function inlining",
    prerequisite = setOf(ModuleCopyingPhase, LateinitLoweringPhase, ArrayInlineConstructorLoweringPhase, CoroutineIntrinsicLoweringPhase)
)

private val RemoveInlineFunctionsWithReifiedTypeParametersLoweringPhase = makeJsModulePhase(
    { RemoveInlineFunctionsWithReifiedTypeParametersLowering() },
    name = "RemoveInlineFunctionsWithReifiedTypeParametersLowering",
    description = "Remove Inline functions with reified parameters from context",
    prerequisite = setOf(FunctionInliningPhase)
)

private val ThrowableSuccessorsLoweringPhase = makeJsModulePhase(
    ::ThrowableSuccessorsLowering,
    name = "ThrowableSuccessorsLowering",
    description = "Link kotlin.Throwable and JavaScript Error together to provide proper interop between language and platform exceptions"
)

private val TailrecLoweringPhase = makeJsModulePhase(
    ::TailrecLowering,
    name = "TailrecLowering",
    description = "Replace `tailrec` callsites with equivalent loop"
)

private val UnitMaterializationLoweringPhase = makeJsModulePhase(
    ::UnitMaterializationLowering,
    name = "UnitMaterializationLowering",
    description = "Insert Unit object where it is supposed to be",
    prerequisite = setOf(TailrecLoweringPhase)
)

private val EnumClassLoweringPhase = makeJsModulePhase(
    ::EnumClassLowering,
    name = "EnumClassLowering",
    description = "Transform Enum Class into regular Class"
)

private val EnumUsageLoweringPhase = makeJsModulePhase(
    ::EnumUsageLowering,
    name = "EnumUsageLowering",
    description = "Replace enum access with invocation of corresponding function"
)

private val SharedVariablesLoweringPhase = makeJsModulePhase(
    ::SharedVariablesLowering,
    name = "SharedVariablesLowering",
    description = "Box captured mutable variables"
)

private val ReturnableBlockLoweringPhase = makeJsModulePhase(
    ::ReturnableBlockLowering,
    name = "ReturnableBlockLowering",
    description = "Replace returnable block with do-while loop",
    prerequisite = setOf(FunctionInliningPhase)
)

private val LocalDelegatedPropertiesLoweringPhase = makeJsModulePhase(
    { LocalDelegatedPropertiesLowering() },
    name = "LocalDelegatedPropertiesLowering",
    description = "Transform Local Delegated properties"
)

private val LocalDeclarationsLoweringPhase = makeJsModulePhase(
    ::LocalDeclarationsLowering,
    name = "LocalDeclarationsLowering",
    description = "Move local declarations into nearest declaration container",
    prerequisite = setOf(SharedVariablesLoweringPhase)
)

private val InnerClassesLoweringPhase = makeJsModulePhase(
    ::InnerClassesLowering,
    name = "InnerClassesLowering",
    description = "Capture outer this reference to inner class"
)

private val InnerClassConstructorCallsLoweringPhase = makeJsModulePhase(
    ::InnerClassConstructorCallsLowering,
    name = "InnerClassConstructorCallsLowering",
    description = "Replace inner class constructor invocation"
)

private val SuspendFunctionsLoweringPhase = makeJsModulePhase(
    ::SuspendFunctionsLowering,
    name = "SuspendFunctionsLowering",
    description = "Transform suspend functions into CoroutineImpl instance and build state machine",
    prerequisite = setOf(UnitMaterializationLoweringPhase, CoroutineIntrinsicLoweringPhase)
)

private val CallableReferenceLoweringPhase = makeJsModulePhase(
    ::CallableReferenceLowering,
    name = "CallableReferenceLowering",
    description = "Handle callable references",
    prerequisite = setOf(SuspendFunctionsLoweringPhase, LocalDeclarationsLoweringPhase, LocalDelegatedPropertiesLoweringPhase)
)

private val DefaultArgumentStubGeneratorPhase = makeJsModulePhase(
    ::DefaultArgumentStubGenerator,
    name = "DefaultArgumentStubGenerator",
    description = "Generate synthetic stubs for functions with default parameter values"
)

private val DefaultParameterInjectorPhase = makeJsModulePhase(
    ::DefaultParameterInjector,
    name = "DefaultParameterInjector",
    description = "Replace callsite with default parameters with corresponding stub function",
    prerequisite = setOf(CallableReferenceLoweringPhase, InnerClassesLoweringPhase)
)

private val DefaultParameterCleanerPhase = makeJsModulePhase(
    ::DefaultParameterCleaner,
    name = "DefaultParameterCleaner",
    description = "Clean default parameters up"
)

private val VarargLoweringPhase = makeJsModulePhase(
    ::VarargLowering,
    name = "VarargLowering",
    description = "Lower vararg arguments",
    prerequisite = setOf(CallableReferenceLoweringPhase)
)

private val PropertiesLoweringPhase = makeJsModulePhase(
    { PropertiesLowering() },
    name = "PropertiesLowering",
    description = "Move fields and accessors out from its property"
)

private val InitializersLoweringPhase = makeJsPhase(
    { module -> InitializersLowering(context, JsLoweredDeclarationOrigin.CLASS_STATIC_INITIALIZER, false).lower(module) },
    name = "InitializersLowering",
    description = "Merge init block and field initializers into [primary] constructor",
    prerequisite = setOf(EnumClassLoweringPhase)
)

private val MultipleCatchesLoweringPhase = makeJsModulePhase(
    ::MultipleCatchesLowering,
    name = "MultipleCatchesLowering",
    description = "Replace multiple catches with single one"
)

private val BridgesConstructionPhase = makeJsModulePhase(
    ::BridgesConstruction,
    name = "BridgesConstruction",
    description = "Generate bridges",
    prerequisite = setOf(SuspendFunctionsLoweringPhase)
)

private val TypeOperatorLoweringPhase = makeJsModulePhase(
    ::TypeOperatorLowering,
    name = "TypeOperatorLowering",
    description = "Lower IrTypeOperator with corresponding logic",
    prerequisite = setOf(BridgesConstructionPhase, RemoveInlineFunctionsWithReifiedTypeParametersLoweringPhase)
)

private val SecondaryCtorLoweringPhase = makeJsPhase(
    { module ->
        SecondaryCtorLowering(context).run {
            constructorProcessorLowering.runOnFilesPostfix(module.files + context.dependencies.flatMap { it.files })
            constructorRedirectorLowering.lower(module)
        }
    },
    name = "SecondaryCtorLoweringPhase",
    description = "Generate static functions for each secondary constructor and replace usages",
    prerequisite = setOf(InnerClassesLoweringPhase)
)

private val InlineClassLoweringPhase = makeJsPhase(
    { module ->
        InlineClassLowering(context).run {
            inlineClassDeclarationLowering.runOnFilesPostfix(module)
            inlineClassUsageLowering.lower(module)
        }
    },
    name = "InlineClassLowering",
    description = "Handle inline classes"
)

private val AutoboxingTransformerPhase = makeJsModulePhase(
    ::AutoboxingTransformer,
    name = "AutoboxingTransformer",
    description = "Insert box/unbox intrinsics"
)

private val BlockDecomposerLoweringPhase = makeJsPhase(
    { module ->
        BlockDecomposerLowering(context).lower(module)
        module.patchDeclarationParents()
    },
    name = "BlockDecomposerLowering",
    description = "Transform statement-like-expression nodes into pure-statement to make it easily transform into JS",
    prerequisite = setOf(TypeOperatorLoweringPhase, SuspendFunctionsLoweringPhase)
)

private val ClassReferenceLoweringPhase = makeJsModulePhase(
    ::ClassReferenceLowering,
    name = "ClassReferenceLowering",
    description = "Handle class references"
)

private val PrimitiveCompanionLoweringPhase = makeJsModulePhase(
    ::PrimitiveCompanionLowering,
    name = "PrimitiveCompanionLowering",
    description = "Replace common companion object access with platform one"
)

private val ConstLoweringPhase = makeJsModulePhase(
    ::ConstLowering,
    name = "ConstLowering",
    description = "Wrap Long and Char constants into constructor invocation"
)

private val CallsLoweringPhase = makeJsModulePhase(
    ::CallsLowering,
    name = "CallsLowering",
    description = "Handle intrinsics"
)

object IrModuleEndPhase : CompilerPhase<JsIrBackendContext, IrModuleFragment> {
    override val name = "IrModuleFragment"
    override val description = "State at end of IrModuleFragment lowering"
    override val prerequisite = emptySet()
    override fun invoke(
            manager: CompilerPhaseManager<JsIrBackendContext, IrModuleFragment>,
            input: IrModuleFragment
    ) = input
}

private val IrToJsPhase = makeJsPhase(
    { module -> context.jsProgram = IrModuleToJsTransformer(context).let { module.accept(it, null) } },
    name = "IrModuleToJsTransformer",
    description = "Generate JsAst from IrTree"
)

val jsPhases = listOf(
    IrModuleStartPhase,
    MoveExternalDeclarationsToSeparatePlacePhase,
    ExpectDeclarationsRemovingPhase,
    CoroutineIntrinsicLoweringPhase,
    ArrayInlineConstructorLoweringPhase,
    LateinitLoweringPhase,
    ModuleCopyingPhase,
    FunctionInliningPhase,
    RemoveInlineFunctionsWithReifiedTypeParametersLoweringPhase,
    ThrowableSuccessorsLoweringPhase,
    TailrecLoweringPhase,
    UnitMaterializationLoweringPhase,
    EnumClassLoweringPhase,
    EnumUsageLoweringPhase,
    SharedVariablesLoweringPhase,
    ReturnableBlockLoweringPhase,
    LocalDelegatedPropertiesLoweringPhase,
    LocalDeclarationsLoweringPhase,
    InnerClassesLoweringPhase,
    InnerClassConstructorCallsLoweringPhase,
    SuspendFunctionsLoweringPhase,
    CallableReferenceLoweringPhase,
    DefaultArgumentStubGeneratorPhase,
    DefaultParameterInjectorPhase,
    DefaultParameterCleanerPhase,
    VarargLoweringPhase,
    PropertiesLoweringPhase,
    InitializersLoweringPhase,
    MultipleCatchesLoweringPhase,
    BridgesConstructionPhase,
    TypeOperatorLoweringPhase,
    SecondaryCtorLoweringPhase,
    InlineClassLoweringPhase,
    AutoboxingTransformerPhase,
    BlockDecomposerLoweringPhase,
    ClassReferenceLoweringPhase,
    PrimitiveCompanionLoweringPhase,
    ConstLoweringPhase,
    CallsLoweringPhase,
    IrModuleEndPhase,
    IrToJsPhase
)