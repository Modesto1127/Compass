package me.modesto.compass.compiler

import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider

/**
 * Description.
 *
 * @author Created by Luqian Ma in 2022/5/29
 */
class ProcessorProvider : SymbolProcessorProvider {

    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
        val logEnable = (environment.options[LOG_ENABLE] ?: "false") == ENABLE_FLAG
        return Processor(codeGenerator = environment.codeGenerator,
                         logger = if (logEnable) environment.logger else null,
                         options = environment.options
        )
    }

}