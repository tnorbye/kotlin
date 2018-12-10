/*
 * Copyright 2010-2018 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license
 * that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.backend.common

import org.jetbrains.kotlin.config.CommonConfigurationKeys
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.config.CompilerConfigurationKey
import org.jetbrains.kotlin.ir.IrElement
import org.jetbrains.kotlin.ir.declarations.IrFile
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment
import org.jetbrains.kotlin.ir.util.dump
import kotlin.system.measureTimeMillis

interface CompilerPhase<Context : BackendContext, Data> {
    val name: String
    val description: String
    val prerequisite: Set<CompilerPhase<*, *>>
        get() = emptySet()

    fun invoke(manager: CompilerPhaseManager<Context, Data>, input: Data): Data
}

private typealias AnyPhase = CompilerPhase<*, *>

class CompilerPhases(
    val phaseList: List<AnyPhase>,
    config: CompilerConfiguration,
    val startPhaseMarker: AnyPhase? = null,
    val endPhaseMarker: AnyPhase? = null
) {

    val phases = phaseList.associate { it.name to it }

    val enabled = computeEnabled(config)
    val verbose = phaseSetFromConfiguration(config, CommonConfigurationKeys.VERBOSE_PHASES)

    val toDumpStateBefore: MutableSet<AnyPhase>
    val toDumpStateAfter: MutableSet<AnyPhase>

    val toValidateStateBefore: MutableSet<AnyPhase>
    val toValidateStateAfter: MutableSet<AnyPhase>

    init {
        with(CommonConfigurationKeys) {
            val beforeDumpSet = phaseSetFromConfiguration(config, PHASES_TO_DUMP_STATE_BEFORE)
            val afterDumpSet = phaseSetFromConfiguration(config, PHASES_TO_DUMP_STATE_AFTER)
            val bothDumpSet = phaseSetFromConfiguration(config, PHASES_TO_DUMP_STATE)
            toDumpStateBefore = (beforeDumpSet + bothDumpSet).toMutableSet()
            toDumpStateAfter = (afterDumpSet + bothDumpSet).toMutableSet()
            val beforeValidateSet = phaseSetFromConfiguration(config, PHASES_TO_VALIDATE_BEFORE)
            val afterValidateSet = phaseSetFromConfiguration(config, PHASES_TO_VALIDATE_AFTER)
            val bothValidateSet = phaseSetFromConfiguration(config, PHASES_TO_VALIDATE)
            toValidateStateBefore = (beforeValidateSet + bothValidateSet).toMutableSet()
            toValidateStateAfter = (afterValidateSet + bothValidateSet).toMutableSet()
        }
    }

    fun known(name: String): String {
        if (phases[name] == null) {
            error("Unknown phase: $name. Use -Xlist-phases to see the list of phases.")
        }
        return name
    }

    fun list() {
        phaseList.forEach { phase ->
            val enabled = if (phase in enabled) "(Enabled)" else ""
            val verbose = if (phase in verbose) "(Verbose)" else ""

            println(String.format("%1$-30s %2$-50s %3$-10s", "${phase.name}:", phase.description, "$enabled $verbose"))
        }
    }

    private fun computeEnabled(config: CompilerConfiguration) =
        with(CommonConfigurationKeys) {
            val disabledPhases = phaseSetFromConfiguration(config, DISABLED_PHASES)
            (phases.values - disabledPhases).toMutableSet()
        }

    private fun phaseSetFromConfiguration(config: CompilerConfiguration, key: CompilerConfigurationKey<Set<String>>): Set<AnyPhase> {
        val phaseNames = config.get(key) ?: emptySet()
        if ("ALL" in phaseNames) return phases.values.toMutableSet()
        return phaseNames.map { phases[it]!! }.toMutableSet()
    }
}


interface PhaseRunner<Context : BackendContext, Data> {
    fun runBefore(manager: CompilerPhaseManager<Context, Data>, phase: CompilerPhase<Context, Data>, depth: Int, context: Context, data: Data)
    fun runBody(manager: CompilerPhaseManager<Context, Data>, phase: CompilerPhase<Context, Data>, context: Context, source: Data): Data
    fun runAfter(manager: CompilerPhaseManager<Context, Data>, phase: CompilerPhase<Context, Data>, depth: Int, context: Context, data: Data)
}

abstract class DefaultPhaseRunner<Context : CommonBackendContext, Data>(private val validator: (data: Data, context: Context) -> Unit = { _, _ -> }) :
    PhaseRunner<Context, Data> {

    enum class BeforeOrAfter { BEFORE, AFTER }

    private var inVerbosePhase = false

    final override fun runBefore(manager: CompilerPhaseManager<Context, Data>, phase: CompilerPhase<Context, Data>, depth: Int, context: Context, data: Data) {
        checkAndRun(phase, phases(context).toDumpStateBefore) { dumpElement(data, phase, context, BeforeOrAfter.BEFORE) }
        checkAndRun(phase, phases(context).toValidateStateBefore) { validator(data, context) }
    }

    final override fun runBody(manager: CompilerPhaseManager<Context, Data>, phase: CompilerPhase<Context, Data>, context: Context, source: Data): Data {
        val phases = phases(context)
        val runner = when {
            phase === phases.startPhaseMarker -> ::justRun
            phase === phases.endPhaseMarker -> ::justRun
            needProfiling(context) -> ::runAndProfile
            else -> ::justRun
        }

        inVerbosePhase = phase in phases(context).verbose

        val result = runner(manager, phase, source)

        inVerbosePhase = false

        return result
    }

    final override fun runAfter(manager: CompilerPhaseManager<Context, Data>, phase: CompilerPhase<Context, Data>, depth: Int, context: Context, data: Data) {
        checkAndRun(phase, phases(context).toDumpStateAfter) { dumpElement(data, phase, context, BeforeOrAfter.AFTER) }
        checkAndRun(phase, phases(context).toValidateStateAfter) { validator(data, context) }
    }

    protected abstract fun phases(context: Context): CompilerPhases
    protected abstract fun elementName(input: Data): String
    protected abstract fun configuration(context: Context): CompilerConfiguration

    private fun needProfiling(context: Context) = configuration(context).getBoolean(CommonConfigurationKeys.PROFILE_PHASES)

    private fun checkAndRun(phase: CompilerPhase<*, Data>, set: Set<AnyPhase>, block: () -> Unit) {
        if (phase in set) block()
    }

    protected abstract fun dumpElement(input: Data, phase: CompilerPhase<Context, Data>, context: Context, beforeOrAfter: BeforeOrAfter)

    private fun runAndProfile(manager: CompilerPhaseManager<Context, Data>, phase: CompilerPhase<Context, Data>, source: Data): Data {
        var result: Data = source
        val msec = measureTimeMillis { result = phase.invoke(manager, source) }
        println("${phase.description}: $msec msec")
        return result
    }

    private fun justRun(manager: CompilerPhaseManager<Context, Data>, phase: CompilerPhase<Context, Data>, source: Data) =
        phase.invoke(manager, source)
}

abstract class DefaultIrPhaseRunner<Context : CommonBackendContext, Data : IrElement>(validator: (data: Data, context: Context) -> Unit = { _, _ -> }) :
    DefaultPhaseRunner<Context, Data>(validator) {

    open fun separator(title: String) = println("\n\n--- $title ----------------------\n")

    private fun shouldBeDumped(context: Context, input: Data) =
        elementName(input) !in configuration(context).get(CommonConfigurationKeys.EXCLUDED_ELEMENTS_FROM_DUMPING, emptySet())

    override fun dumpElement(input: Data, phase: CompilerPhase<Context, Data>, context: Context, beforeOrAfter: BeforeOrAfter) {
        val phases = phases(context)
        val startPhaseMarker = phases.startPhaseMarker
        val endPhaseMarker = phases.endPhaseMarker

        // Exclude nonsensical combinations
        if (phase === startPhaseMarker && beforeOrAfter == BeforeOrAfter.AFTER) return
        if (phase === endPhaseMarker && beforeOrAfter == BeforeOrAfter.BEFORE) return

        if (!shouldBeDumped(context, input)) return

        val title = when (phase) {
            startPhaseMarker -> "IR for ${elementName(input)} at the start of lowering process"
            endPhaseMarker -> "IR for ${elementName(input)} at the end of lowering process"
            else -> {
                val beforeOrAfterStr = beforeOrAfter.name.toLowerCase()
                "IR for ${elementName(input)} $beforeOrAfterStr ${phase.description}"
            }
        }
        separator(title)
        println(input.dump())
    }
}

class CompilerPhaseManager<Context : BackendContext, Data>(
    val context: Context,
    val phases: CompilerPhases,
    val data: Data,
    private val phaseRunner: PhaseRunner<Context, Data>,
    val parent: CompilerPhaseManager<Context, *>? = null
) {
    val depth: Int = parent?.depth?.inc() ?: 0

    private val previousPhases = mutableSetOf<CompilerPhase<Context, Data>>()

    fun <NewData> createChildManager(
        newData: NewData,
        newPhaseRunner: PhaseRunner<Context, NewData>
    ) = CompilerPhaseManager(
        context, phases, newData, newPhaseRunner, parent = this
    )

    fun createChildManager() = createChildManager(data, phaseRunner)

    private fun checkPrerequisite(phase: CompilerPhase<*, *>): Boolean =
        previousPhases.contains(phase) || parent?.checkPrerequisite(phase) == true

    fun phase(phase: CompilerPhase<Context, Data>, source: Data): Data {

        if (phase !in phases.enabled) return source

        phase.prerequisite.forEach {
            if (!checkPrerequisite(it))
                throw Error("$phase requires $it")
        }

        previousPhases.add(phase)

        phaseRunner.runBefore(this, phase, depth, context, source)
        val result = phaseRunner.runBody(this, phase, context, source)
        phaseRunner.runAfter(this, phase, depth, context, result)
        return result
    }
}

fun <Context : BackendContext, Data> makePhase(
    op: CompilerPhaseManager<Context, Data>.(Data) -> Unit,
    description: String,
    name: String,
    prerequisite: Set<AnyPhase> = emptySet()
) = object : CompilerPhase<Context, Data> {
    override val name = name
    override val description = description
    override val prerequisite = prerequisite

    override fun invoke(manager: CompilerPhaseManager<Context, Data>, input: Data): Data {
        manager.op(input)
        return input
    }

    override fun toString() = "Compiler Phase @$name"
}

fun <Context : BackendContext> makeFileLoweringPhase(
    lowering: (Context) -> FileLoweringPass,
    description: String,
    name: String,
    prerequisite: Set<AnyPhase> = emptySet()
) = makePhase<Context, IrFile>(
    op = { irFile -> lowering(context).lower(irFile) },
    name = name,
    description = description,
    prerequisite = prerequisite
)

fun <Context : BackendContext> makeModuleLoweringPhase(
    lowering: (Context) -> FileLoweringPass,
    description: String,
    name: String,
    prerequisite: Set<AnyPhase> = emptySet()
) = makePhase<Context, IrModuleFragment>(
    op = { irModule -> lowering(context).lower(irModule) },
    name = name,
    description = description,
    prerequisite = prerequisite
)

fun <Context : BackendContext, Data> CompilerPhaseManager<Context, Data>.runPhases(
    phaseList: List<CompilerPhase<Context, Data>>
): Data =
        phaseList.fold(data) { m, p -> phase(p, m) }