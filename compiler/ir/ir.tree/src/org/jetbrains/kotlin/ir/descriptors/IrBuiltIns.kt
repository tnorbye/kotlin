/*
 * Copyright 2010-2018 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license
 * that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.ir.descriptors

import org.jetbrains.kotlin.builtins.KotlinBuiltIns
import org.jetbrains.kotlin.descriptors.*
import org.jetbrains.kotlin.descriptors.annotations.Annotations
import org.jetbrains.kotlin.descriptors.impl.SimpleFunctionDescriptorImpl
import org.jetbrains.kotlin.descriptors.impl.TypeParameterDescriptorImpl
import org.jetbrains.kotlin.descriptors.impl.ValueParameterDescriptorImpl
import org.jetbrains.kotlin.incremental.components.NoLookupLocation
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction
import org.jetbrains.kotlin.ir.declarations.impl.IrExternalPackageFragmentImpl
import org.jetbrains.kotlin.ir.symbols.impl.IrExternalPackageFragmentSymbolImpl
import org.jetbrains.kotlin.ir.types.withHasQuestionMark
import org.jetbrains.kotlin.ir.util.DeclarationStubGenerator
import org.jetbrains.kotlin.ir.util.SymbolTable
import org.jetbrains.kotlin.ir.util.TypeTranslator
import org.jetbrains.kotlin.name.ClassId
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.name.FqNameUnsafe
import org.jetbrains.kotlin.name.Name
import org.jetbrains.kotlin.types.KotlinType
import org.jetbrains.kotlin.types.KotlinTypeFactory
import org.jetbrains.kotlin.types.SimpleType
import org.jetbrains.kotlin.types.Variance
import org.jetbrains.kotlin.types.checker.KotlinTypeChecker
import org.jetbrains.kotlin.types.typeUtil.makeNullable

@Suppress("DEPRECATION", "MemberVisibilityCanBePrivate", "unused")
class IrBuiltIns(
    private val moduleDescriptor: ModuleDescriptor,
    private val typeTranslator: TypeTranslator,
    outerSymbolTable: SymbolTable? = null
) {
    val languageVersionSettings = typeTranslator.languageVersionSettings

    @Deprecated("Use 'findTopLevelBuiltInClass(FqName)'")
    val builtIns: KotlinBuiltIns = moduleDescriptor.builtIns

    private val builtInsModule = builtIns.builtInsModule

    private val packageFragment = IrBuiltinsPackageFragmentDescriptorImpl(builtInsModule, KOTLIN_INTERNAL_IR_FQN)
    private val irBuiltInsExternalPackageFragment = IrExternalPackageFragmentImpl(IrExternalPackageFragmentSymbolImpl(packageFragment))

    private val symbolTable = outerSymbolTable ?: SymbolTable()
    private val stubBuilder =
        DeclarationStubGenerator(builtInsModule, symbolTable, IrDeclarationOrigin.IR_BUILTINS_STUB, languageVersionSettings)

    private fun ClassDescriptor.toIrSymbol() = symbolTable.referenceClass(this)
    private fun ClassConstructorDescriptor.toIrSymbol() = symbolTable.referenceConstructor(this)
    private fun KotlinType.toIrType() = typeTranslator.translateType(this)

    private inline fun ClassDescriptor.findFirstFunction(name: String, predicate: (CallableMemberDescriptor) -> Boolean) =
        unsubstitutedMemberScope.getContributedFunctions(Name.identifier(name), NoLookupLocation.FROM_BACKEND)
            .first(predicate)
            .let { symbolTable.referenceSimpleFunction(it) }

    fun defineOperator(name: String, returnType: KotlinType, valueParameterTypes: List<KotlinType>): IrSimpleFunction {
        val operatorDescriptor = IrSimpleBuiltinOperatorDescriptorImpl(packageFragment, Name.identifier(name), returnType)
        for ((i, valueParameterType) in valueParameterTypes.withIndex()) {
            operatorDescriptor.addValueParameter(
                IrBuiltinValueParameterDescriptorImpl(operatorDescriptor, Name.identifier("arg$i"), i, valueParameterType)
            )
        }
        return addStubToPackageFragment(operatorDescriptor)
    }

    fun findTopLevelBuiltInClass(fqn: FqName) =
        moduleDescriptor.findClassAcrossModuleDependencies(ClassId.topLevel(fqn))
            ?: builtIns.getBuiltInClassByFqName(fqn)

    fun findTopLevelBuiltInClass(fqn: FqNameUnsafe) =
        findTopLevelBuiltInClass(fqn.toSafe())

    private fun addStubToPackageFragment(descriptor: SimpleFunctionDescriptor): IrSimpleFunction {
        val irSimpleFunction = stubBuilder.generateFunctionStub(descriptor)
        irBuiltInsExternalPackageFragment.declarations.add(irSimpleFunction)
        irSimpleFunction.parent = irBuiltInsExternalPackageFragment
        return irSimpleFunction
    }

    private fun <T : SimpleFunctionDescriptor> T.addStub(): IrSimpleFunction =
        addStubToPackageFragment(this)

    private fun defineComparisonOperator(name: String, operandType: KotlinType) =
        defineOperator(name, bool, listOf(operandType, operandType))

    private fun List<SimpleType>.defineComparisonOperatorForEachType(name: String) =
        associate { it to defineComparisonOperator(name, it) }

    private val anyClassDescriptor = findTopLevelBuiltInClass(KotlinBuiltIns.FQ_NAMES.any)
    @Deprecated("Descriptor-based API")
    val any = anyClassDescriptor.defaultType
    @Deprecated("Descriptor-based API")
    val anyN = any.makeNullableAsSpecified(true)
    val anyType = any.toIrType()
    val anyClass = anyClassDescriptor.toIrSymbol()
    val anyNType = anyType.withHasQuestionMark(true)
    val anyConstructor = anyClassDescriptor.constructors.single().toIrSymbol()

    private val booleanClassDescriptor = findTopLevelBuiltInClass(KotlinBuiltIns.FQ_NAMES._boolean)
    @Deprecated("Descriptor-based API")
    val bool = booleanClassDescriptor.defaultType
    val booleanType = bool.toIrType()
    val booleanClass = booleanClassDescriptor.toIrSymbol()

    private val charClassDescriptor = findTopLevelBuiltInClass(KotlinBuiltIns.FQ_NAMES._char)
    @Deprecated("Descriptor-based API")
    val char = charClassDescriptor.defaultType
    val charType = char.toIrType()
    val charClass = charClassDescriptor.toIrSymbol()

    private val numberClassDescriptor = findTopLevelBuiltInClass(KotlinBuiltIns.FQ_NAMES.number)
    @Deprecated("Descriptor-based API")
    val number = numberClassDescriptor.defaultType
    val numberType = number.toIrType()
    val numberClass = numberClassDescriptor.toIrSymbol()

    private val byteClassDescriptor = findTopLevelBuiltInClass(KotlinBuiltIns.FQ_NAMES._byte)
    @Deprecated("Descriptor-based API")
    val byte = byteClassDescriptor.defaultType
    val byteType = byte.toIrType()
    val byteClass = byteClassDescriptor.toIrSymbol()

    private val shortClassDescriptor = findTopLevelBuiltInClass(KotlinBuiltIns.FQ_NAMES._short)
    @Deprecated("Descriptor-based API")
    val short = shortClassDescriptor.defaultType
    val shortType = short.toIrType()
    val shortClass = shortClassDescriptor.toIrSymbol()

    private val intClassDescriptor = findTopLevelBuiltInClass(KotlinBuiltIns.FQ_NAMES._int)
    @Deprecated("Descriptor-based API")
    val int = intClassDescriptor.defaultType
    val intType = int.toIrType()
    val intClass = intClassDescriptor.toIrSymbol()
    val intTimes = intClassDescriptor.findFirstFunction("times") { KotlinTypeChecker.DEFAULT.equalTypes(it.valueParameters[0].type, int) }
    val intPlus = intClassDescriptor.findFirstFunction("plus") { KotlinTypeChecker.DEFAULT.equalTypes(it.valueParameters[0].type, int) }

    private val longClassDescriptor = findTopLevelBuiltInClass(KotlinBuiltIns.FQ_NAMES._long)
    @Deprecated("Descriptor-based API")
    val long = longClassDescriptor.defaultType
    val longType = long.toIrType()
    val longClass = longClassDescriptor.toIrSymbol()

    private val floatClassDescriptor = findTopLevelBuiltInClass(KotlinBuiltIns.FQ_NAMES._float)
    @Deprecated("Descriptor-based API")
    val float = floatClassDescriptor.defaultType
    val floatType = float.toIrType()
    val floatClass = floatClassDescriptor.toIrSymbol()

    private val doubleClassDescriptor = findTopLevelBuiltInClass(KotlinBuiltIns.FQ_NAMES._double)
    @Deprecated("Descriptor-based API")
    val double = doubleClassDescriptor.defaultType
    val doubleType = double.toIrType()
    val doubleClass = doubleClassDescriptor.toIrSymbol()

    private val nothingClassDescriptor = findTopLevelBuiltInClass(KotlinBuiltIns.FQ_NAMES.nothing)
    @Deprecated("Descriptor-based API")
    val nothing = nothingClassDescriptor.defaultType
    @Deprecated("Descriptor-based API")
    val nothingN = nothing.makeNullableAsSpecified(true)
    val nothingType = nothing.toIrType()
    val nothingClass = nothingClassDescriptor.toIrSymbol()
    val nothingNType = nothingType.withHasQuestionMark(true)

    private val unitClassDescriptor = findTopLevelBuiltInClass(KotlinBuiltIns.FQ_NAMES.unit)
    @Deprecated("Descriptor-based API")
    val unit = unitClassDescriptor.defaultType
    val unitType = unit.toIrType()
    val unitClass = unitClassDescriptor.toIrSymbol()

    private val stringClassDescriptor = findTopLevelBuiltInClass(KotlinBuiltIns.FQ_NAMES.string)
    @Deprecated("Descriptor-based API")
    val string = stringClassDescriptor.defaultType
    val stringType = string.toIrType()
    val stringClass = stringClassDescriptor.toIrSymbol()

    private val charSequenceClassDescriptor = findTopLevelBuiltInClass(KotlinBuiltIns.FQ_NAMES.charSequence)
    val charSequenceClass = charSequenceClassDescriptor.toIrSymbol()
    val charSequence = charSequenceClassDescriptor.defaultType.toIrType()

    private val arrayClassDescriptor = findTopLevelBuiltInClass(KotlinBuiltIns.FQ_NAMES.array)
    val arrayClass = arrayClassDescriptor.toIrSymbol()

    private val throwableClassDescriptor = findTopLevelBuiltInClass(KotlinBuiltIns.FQ_NAMES.throwable)
    val throwableType = throwableClassDescriptor.defaultType.toIrType()
    val throwableClass = throwableClassDescriptor.toIrSymbol()

    private val kCallableClassDescriptor = findTopLevelBuiltInClass(KotlinBuiltIns.FQ_NAMES.kCallable)
    val kCallableClass = kCallableClassDescriptor.toIrSymbol()

    private val kPropertyClassDescriptor = findTopLevelBuiltInClass(KotlinBuiltIns.FQ_NAMES.kPropertyFqName)
    val kPropertyClass = kPropertyClassDescriptor.toIrSymbol()

    private val enumClassDescriptor = findTopLevelBuiltInClass(KotlinBuiltIns.FQ_NAMES._enum)
    val enumClass = enumClassDescriptor.toIrSymbol()
    val enumConstructor = enumClassDescriptor.constructors.single().toIrSymbol()

    @Deprecated("Descriptor-based API")
    val primitiveTypes = listOf(bool, char, byte, short, int, long, float, double)
    @Deprecated("Descriptor-based API")
    val primitiveTypesWithComparisons = listOf(int, long, float, double)
    @Deprecated("Descriptor-based API")
    val primitiveFloatingPointTypes = listOf(float, double)

    val lessFunByOperandType = primitiveTypesWithComparisons.defineComparisonOperatorForEachType("less")
    val lessOrEqualFunByOperandType = primitiveTypesWithComparisons.defineComparisonOperatorForEachType("lessOrEqual")
    val greaterOrEqualFunByOperandType = primitiveTypesWithComparisons.defineComparisonOperatorForEachType("greaterOrEqual")
    val greaterFunByOperandType = primitiveTypesWithComparisons.defineComparisonOperatorForEachType("greater")

    val ieee754equalsFunByOperandType =
        primitiveFloatingPointTypes.associate {
            it to defineOperator("ieee754equals", bool, listOf(it.makeNullable(), it.makeNullable()))
        }

    val eqeqeqFun = defineOperator("EQEQEQ", bool, listOf(anyN, anyN))
    val eqeqFun = defineOperator("EQEQ", bool, listOf(anyN, anyN))
    val throwNpeFun = defineOperator("THROW_NPE", nothing, listOf())
    val throwCceFun = defineOperator("THROW_CCE", nothing, listOf())
    val throwIseFun = defineOperator("THROW_ISE", nothing, listOf())
    val booleanNotFun = defineOperator("NOT", bool, listOf(bool))
    val noWhenBranchMatchedExceptionFun = defineOperator("noWhenBranchMatchedException", nothing, listOf())
    val illegalArgumentExceptionFun = defineOperator("illegalArgumentException", nothing, listOf(string))

    @Deprecated("Descriptor-based API")
    val eqeqeq = eqeqeqFun.descriptor
    @Deprecated("Descriptor-based API")
    val eqeq = eqeqFun.descriptor
    @Deprecated("Descriptor-based API")
    val throwNpe = throwNpeFun.descriptor
    @Deprecated("Descriptor-based API")
    val throwCce = throwCceFun.descriptor
    @Deprecated("Descriptor-based API")
    val booleanNot = booleanNotFun.descriptor
    @Deprecated("Descriptor-based API")
    val noWhenBranchMatchedException = noWhenBranchMatchedExceptionFun.descriptor
    @Deprecated("Descriptor-based API")
    val illegalArgumentException = illegalArgumentExceptionFun.descriptor

    val eqeqeqSymbol = eqeqeqFun.symbol
    val eqeqSymbol = eqeqFun.symbol
    val throwNpeSymbol = throwNpeFun.symbol
    val throwCceSymbol = throwCceFun.symbol
    val throwIseSymbol = throwIseFun.symbol
    val booleanNotSymbol = booleanNotFun.symbol
    val noWhenBranchMatchedExceptionSymbol = noWhenBranchMatchedExceptionFun.symbol
    val illegalArgumentExceptionSymbol = illegalArgumentExceptionFun.symbol

    val enumValueOfFun = createEnumValueOfFun()
    @Deprecated("Descriptor-based API")
    val enumValueOf = enumValueOfFun.descriptor
    val enumValueOfSymbol = enumValueOfFun.symbol

    private fun createEnumValueOfFun(): IrSimpleFunction =
        SimpleFunctionDescriptorImpl.create(
            packageFragment,
            Annotations.EMPTY,
            Name.identifier("enumValueOf"),
            CallableMemberDescriptor.Kind.SYNTHESIZED,
            SourceElement.NO_SOURCE
        ).apply {
            val typeParameterT = TypeParameterDescriptorImpl.createWithDefaultBound(
                this, Annotations.EMPTY, true, Variance.INVARIANT, Name.identifier("T"), 0
            )

            val valueParameterName = ValueParameterDescriptorImpl(
                this, null, 0, Annotations.EMPTY, Name.identifier("name"), string,
                false, false, false, null, SourceElement.NO_SOURCE
            )

            val returnType = KotlinTypeFactory.simpleType(Annotations.EMPTY, typeParameterT.typeConstructor, listOf(), false)

            initialize(null, null, listOf(typeParameterT), listOf(valueParameterName), returnType, Modality.FINAL, Visibilities.PUBLIC)
        }.addStub()

    val dataClassArrayMemberHashCodeFun = defineOperator("dataClassArrayMemberHashCode", int, listOf(any))
    @Deprecated("Descriptor-based API")
    val dataClassArrayMemberHashCode = dataClassArrayMemberHashCodeFun.descriptor
    val dataClassArrayMemberHashCodeSymbol = dataClassArrayMemberHashCodeFun.symbol

    val dataClassArrayMemberToStringFun = defineOperator("dataClassArrayMemberToString", string, listOf(anyN))
    @Deprecated("Descriptor-based API")
    val dataClassArrayMemberToString = dataClassArrayMemberToStringFun.descriptor
    val dataClassArrayMemberToStringSymbol = dataClassArrayMemberToStringFun.symbol

    companion object {
        val KOTLIN_INTERNAL_IR_FQN = FqName("kotlin.internal.ir")
    }
}
