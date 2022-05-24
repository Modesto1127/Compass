package me.modesto.compass.compiler

import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSType
import com.google.devtools.ksp.validate
import com.squareup.kotlinpoet.ksp.KotlinPoetKspPreview
import com.squareup.kotlinpoet.ksp.toClassName
import me.modesto.compass.DestMeta
import me.modesto.compass.anno.DestAnno
import me.modesto.compass.compiler.utils.CacheHelper
import me.modesto.compass.compiler.utils.GenerateHelper
import me.modesto.compass.compiler.utils.findAnnotationWithType
import me.modesto.compass.compiler.utils.getMember
import java.nio.file.Paths

/**
 * Description.
 *
 * @author Created by Luqian Ma in 2022/5/18
 */
@OptIn(KotlinPoetKspPreview::class)
class CompassProcessor(private val env: SymbolProcessorEnvironment) : SymbolProcessor {

    private lateinit var moduleName: String
    private var logger: KSPLogger? = null
    private var load = false

    private lateinit var cacheHelper: CacheHelper
    private lateinit var generateHelper: GenerateHelper
    private val cacheMap: MutableMap<String, DestMeta> by lazy { mutableMapOf() }
    private lateinit var destAnnoType: KSType

    override fun process(resolver: Resolver): List<KSAnnotated> {
        if (load) return emptyList()
        // 获取当前模块的名字
        moduleName = env.options[MODULE_NAME] ?: throw CompassProcessorException("No parameter defined: $MODULE_NAME")
        // 判断是否开启日志输出
        val loggable = (env.options[LOGGABLE] ?: "false") == ENABLE_FLAG
        if (loggable) {
            logger = env.logger
        }
        // 此处拿到缓存文件的保存目录
        val outputPath =
            Paths.get(env.options[OUTPUT_DIR] ?: throw CompassProcessorException("No parameter defined: $OUTPUT_DIR"))
        // 创建缓存保存辅助器对象
        cacheHelper = CacheHelper(logger, outputPath)
        generateHelper = GenerateHelper(moduleName, logger, env.codeGenerator)

        destAnnoType = resolver.getClassDeclarationByName(
            resolver.getKSNameFromString(DestAnno::class.java.name)
        )?.asType(emptyList()) ?: kotlin.run {
            logger?.error("JsonClass type not found on the classpath.")
            return emptyList()
        }

        val symbols = resolver.getSymbolsWithAnnotation(DestAnno::class.java.name)
            .filterIsInstance<KSClassDeclaration>()
        val ret = symbols.filter { !it.validate() }.toList()

        cacheHelper.release(cacheMap)
        symbols
            .filter { it.validate() }
            .forEach {
                add(it)
            }
        cacheHelper.save(cacheMap)
        generateHelper.register(cacheMap)
        generateHelper.generate()
        load = true
        return ret
    }

    private fun add(it: KSClassDeclaration) {
        val destAnno = it.findAnnotationWithType(destAnnoType) ?: return
        val path = destAnno.getMember<String>("path")
        val priority = destAnno.getMember<Int>("priority")
        val destName = it.toClassName().canonicalName
        logger?.warn("path: $path, priority: $priority, dest: $destName")
        cacheMap["${path}_$priority"] = DestMeta(dest = destName)
    }

}

class CompassProcessorProvider : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
        return CompassProcessor(environment)
    }
}