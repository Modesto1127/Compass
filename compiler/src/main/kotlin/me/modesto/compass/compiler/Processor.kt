package me.modesto.compass.compiler

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import me.modesto.compass.compiler.helper.*

/**
 * Description.
 *
 * @author Created by Luqian Ma in 2022/5/29
 */
class Processor(private val codeGenerator: CodeGenerator,
                private val logger: KSPLogger? = null,
                private val options: Map<String, String>) : SymbolProcessor {

    override fun process(resolver: Resolver): List<KSAnnotated> {
        // 获取当前 Module 的命名
        val compassModule = resolver.getCompassModule()
        warn("module size: ${compassModule.toList().size}")
        // 如果没有定义 CompassModule  直接返回不做处理
        if (!compassModule.iterator().hasNext()) {
            warn("You did not define ModuleAnno")
            return emptyList()
        }
        // 如果多次定义 CompassModule  抛出错误
        if (SIZE_COMPASS_MODULE != compassModule.toList().size) {
            throw CompassProcessorException("CompassModule cannot be defined more than once")
        }
        val moduleName = compassModule.getModuleName()
        if (null == moduleName) {
            warn("CompassModule value must not be blank")
            return emptyList()
        }
        warn("Current module name: $moduleName")

        // 拿到所有的 CompassDest
        val destinations = resolver.getCompassDestinations()
        warn("destination size: ${destinations.toList().size}")
        // 如果没有定义 CompassDest  直接返回  不做处理
        if (!destinations.iterator().hasNext()) {
            warn("You did not define DestAnno")
            return emptyList()
        }
        ModuleGenerator(codeGenerator, moduleName, logger).generate(destinations)

        // 获取所有 Module 的命名
        val compassRegistry = resolver.getCompassRegistry()
        warn("registry size: ${compassRegistry.toList().size}")
        if (!compassRegistry.iterator().hasNext()) {
            warn("You did not define RegistryAnno")
            return emptyList()
        }
        if (SIZE_COMPASS_REGISTRY != compassRegistry.toList().size) {
            throw CompassProcessorException("CompassRegistry cannot be defined more than once")
        }
        val modules = compassRegistry.getModules()
        if (null == modules) {
            warn("CompassModule value must not be empty")
            return emptyList()
        }
        InitGenerator(codeGenerator, logger).generate(modules)
        return emptyList()
    }

    private fun warn(msg: String) {
        logger?.warn(msg)
    }

    private fun error(msg: String) {
        logger?.error(msg)
    }

}