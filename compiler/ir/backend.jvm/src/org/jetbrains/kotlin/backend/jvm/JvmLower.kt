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

package org.jetbrains.kotlin.backend.jvm

import org.jetbrains.kotlin.backend.common.BackendContext
import org.jetbrains.kotlin.backend.common.CompilerPhase
import org.jetbrains.kotlin.backend.common.CompilerPhaseManager
import org.jetbrains.kotlin.backend.common.runPhases
import org.jetbrains.kotlin.ir.declarations.IrFile
import org.jetbrains.kotlin.ir.util.PatchDeclarationParentsVisitor
import org.jetbrains.kotlin.ir.visitors.acceptVoid

fun <Context : BackendContext> makePatchParentsPhase(number: Int) = object : CompilerPhase<Context, IrFile> {
    override val name: String = "PatchParents$number"
    override val description: String = "Patch parent references in IrFile, pass $number"
    override val prerequisite: Set<CompilerPhase<Context, *>> = emptySet()

    override fun invoke(manager: CompilerPhaseManager<Context, IrFile>, input: IrFile): IrFile {
        input.acceptVoid(PatchDeclarationParentsVisitor())
        return input
    }
}

class JvmLower(val context: JvmBackendContext) {
    fun lower(irFile: IrFile) {
        // TODO run lowering passes as callbacks in bottom-up visitor
        context.rootPhaseManager(irFile).runPhases(jvmPhases)
    }
}
