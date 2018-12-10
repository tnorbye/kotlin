/*
 * Copyright 2010-2018 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license
 * that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.backend.jvm

import org.jetbrains.kotlin.backend.common.*
import org.jetbrains.kotlin.backend.common.lower.*
import org.jetbrains.kotlin.backend.jvm.lower.*
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor
import org.jetbrains.kotlin.descriptors.Visibilities
import org.jetbrains.kotlin.ir.declarations.IrFile
import org.jetbrains.kotlin.name.NameUtils

object IrFileStartPhase : CompilerPhase<JvmBackendContext, IrFile> {
    override val name = "IrFileStart"
    override val description = "State at start of IrFile lowering"
    override val prerequisite = emptySet()
    override fun invoke(manager: CompilerPhaseManager<JvmBackendContext, IrFile>, input: IrFile) = input
}

private fun makeJvmPhase(
    lowering: (JvmBackendContext) -> FileLoweringPass,
    description: String,
    name: String,
    prerequisite: Set<CompilerPhase<JvmBackendContext, IrFile>> = emptySet()
) = makeFileLoweringPhase(lowering, description, name, prerequisite)

private val JvmCoercionToUnitPhase = makeJvmPhase(
    ::JvmCoercionToUnitPatcher,
    name = "JvmCoercionToUnit",
    description = "Insert conversions to unit after IrCalls where needed"
)

private val FileClassPhase = makeJvmPhase(
    ::FileClassLowering,
    name = "FileClass",
    description = "Put file level function and property declaration into a class"
)

private val KCallableNamePropertyPhase = makeJvmPhase(
    ::KCallableNamePropertyLowering,
    name = "KCallableNameProperty",
    description = "Replace name references for callables with constants"
)

private val LateinitPhase = makePhase<JvmBackendContext, IrFile>(
    { irFile -> LateinitLowering(context, true).lower(irFile) },
    name = "Lateinit",
    description = "Insert checks for lateinit field references"
)

private val MoveCompanionObjectFieldsPhase = makeJvmPhase(
    ::MoveCompanionObjectFieldsLowering,
    name = "MoveCompanionObjectFields",
    description = "Move companion object fields to static fields of companion's owner"
)


private val ConstAndJvmFieldPropertiesPhase = makeJvmPhase(
    ::ConstAndJvmFieldPropertiesLowering,
    name = "ConstAndJvmFieldProperties",
    description = "Substitute calls to const and Jvm>Field properties with const/field access"
)


private val PropertiesPhase = makeJvmPhase(
    ::PropertiesLowering,
    name = "Properties",
    description = "move fields and accessors for properties to their classes"
)


private val AnnotationPhase = makeJvmPhase(
    ::AnnotationLowering,
    name = "Annotation",
    description = "Remove constructors from annotation classes"
)

private val DefaultArgumentStubPhase = makePhase<JvmBackendContext, IrFile>(
    { irFile -> DefaultArgumentStubGenerator(context, false).lower(irFile) },
    name = "DefaultArgumentsStubGenerator",
    description = "Generate synthetic stubs for functions with default parameter values"
)

private val InterfacePhase = makeJvmPhase(
    ::InterfaceLowering,
    name = "Interface",
    description = "Move default implementations of interface members to DefaultImpls class"
)

private val InterfaceDelegationPhase = makeJvmPhase(
    ::InterfaceDelegationLowering,
    name = "InterfaceDelegation",
    description = "Delegate calls to interface members with default implementations to DefaultImpls"
)

private val SharedVariablesPhase = makeJvmPhase(
    ::SharedVariablesLowering,
    name = "SharedVariables",
    description = "Transform shared variables"
)

private val LocalDeclarationsPhase = makePhase<JvmBackendContext, IrFile>(
    { irFile ->
        LocalDeclarationsLowering(context, object : LocalNameProvider {
            override fun localName(descriptor: DeclarationDescriptor): String =
                NameUtils.sanitizeAsJavaIdentifier(super.localName(descriptor))
        }, Visibilities.PUBLIC, true).lower(irFile)
    },
    name = "JvmLocalDeclarations",
    description = "Move local declarations to classes",
    prerequisite = setOf(SharedVariablesPhase)
)

private val CallableReferencePhase = makeJvmPhase(
    ::CallableReferenceLowering,
    name = "CallableReference",
    description = "Handle callable references"
)

private val FunctionNVarargInvokePhase = makeJvmPhase(
    ::FunctionNVarargInvokeLowering,
    name = "FunctionNVarargInvoke",
    description = "Handle invoke functions with large number of arguments"
)


private val InnerClassesPhase = makeJvmPhase(
    ::InnerClassesLowering,
    name = "InnerClasses",
    description = "Move inner classes to toplevel"
)

private val InnerClassConstructorCallsPhase = makeJvmPhase(
    ::InnerClassConstructorCallsLowering,
    name = "InnerClassConstructorCalls",
    description = "Handle constructor calls for inner classes"
)


private val EnumClassPhase = makeJvmPhase(
    ::EnumClassLowering,
    name = "EnumClass",
    description = "Handle enum classes"
)


private val ObjectClassPhase = makeJvmPhase(
    ::ObjectClassLowering,
    name = "ObjectClass",
    description = "Handle object classes"
)

private val InitializersPhase = makePhase<JvmBackendContext, IrFile>(
    { file -> InitializersLowering(context, JvmLoweredDeclarationOrigin.CLASS_STATIC_INITIALIZER, true).lower(file) },
    name = "Initializers",
    description = "Handle initializer statements"
)


private val SingletonReferencesPhase = makeJvmPhase(
    ::SingletonReferencesLowering,
    name = "SingletonReferences",
    description = "Handle singleton references"
)


private val SyntheticAccessorPhase = makeJvmPhase(
    ::SyntheticAccessorLowering,
    name = "SyntheticAccessor",
    description = "Introduce synthetic accessors",
    prerequisite = setOf(ObjectClassPhase)
)

private val BridgePhase = makeJvmPhase(
    ::BridgeLowering,
    name = "Bridge",
    description = "Generate bridges"
)


private val JvmOverloadsAnnotationPhase = makeJvmPhase(
    ::JvmOverloadsAnnotationLowering,
    name = "JvmOverloadsAnnotation",
    description = "Handle JvmOverloads annotations"
)


private val JvmStaticAnnotationPhase = makeJvmPhase(
    ::JvmStaticAnnotationLowering,
    name = "JvmStaticAnnotation",
    description = "Handle JvmStatic annotations"
)


private val StaticDefaultFunctionPhase = makeJvmPhase(
    ::StaticDefaultFunctionLowering,
    name = "StaticDefaultFunction",
    description = "Generate static functions for default parameters"
)


private val TailrecPhase = makeJvmPhase(
    ::TailrecLowering,
    name = "Tailrec",
    description = "Handle tailrec calls"
)


private val ToArrayPhase = makeJvmPhase(
    ::ToArrayLowering,
    name = "ToArray",
    description = "Handle toArray functions"
)

object IrFileEndPhase : CompilerPhase<JvmBackendContext, IrFile> {
    override val name = "IrFileEnd"
    override val description = "State at end of IrFile lowering"
    override val prerequisite = emptySet()
    override fun invoke(manager: CompilerPhaseManager<JvmBackendContext, IrFile>, input: IrFile) = input
}

val jvmPhases: List<CompilerPhase<JvmBackendContext, IrFile>> = listOf(
    IrFileStartPhase,

    JvmCoercionToUnitPhase,
    FileClassPhase,
    KCallableNamePropertyPhase,

    LateinitPhase,

    MoveCompanionObjectFieldsPhase,
    ConstAndJvmFieldPropertiesPhase,
    PropertiesPhase,
    AnnotationPhase,

    DefaultArgumentStubPhase,

    InterfacePhase,
    InterfaceDelegationPhase,
    SharedVariablesPhase,

    makePatchParentsPhase(1),

    LocalDeclarationsPhase,
    CallableReferencePhase,
    FunctionNVarargInvokePhase,

    InnerClassesPhase,
    InnerClassConstructorCallsPhase,

    makePatchParentsPhase(2),

    EnumClassPhase,
    ObjectClassPhase,
    InitializersPhase,
    SingletonReferencesPhase,
    SyntheticAccessorPhase,
    BridgePhase,
    JvmOverloadsAnnotationPhase,
    JvmStaticAnnotationPhase,
    StaticDefaultFunctionPhase,

    TailrecPhase,
    ToArrayPhase,

    makePatchParentsPhase(3),

    IrFileEndPhase
)
