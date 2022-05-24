package me.modesto.compass.compiler.utils

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import me.modesto.compass.DestMeta
import me.modesto.compass.IRouteStore
import me.modesto.compass.compiler.DEFAULT_PACKAGE
import kotlin.concurrent.thread

/**
 * Description.
 *
 * @author Created by Luqian Ma in 2022/5/18
 */
class GenerateHelper(private val moduleName: String,
                     private val logger: KSPLogger?,
                     private val codeGenerator: CodeGenerator) {

    private val getMapFunc: FunSpec.Builder = FunSpec.builder("getMap")
        .addModifiers(KModifier.OVERRIDE)
        .returns(Map::class.parameterizedBy(String::class, DestMeta::class))
        .addStatement("val map = mutableMapOf<String, DestMeta>()")

    fun register(map: Map<String, DestMeta>) {
        map.forEach { (path, dest) ->
            getMapFunc.addStatement("map[%S] = %T(%S)", path, DestMeta::class, dest.dest)
        }
    }

    fun generate() {
        val className = "${moduleName}RouteStore"
        val mapClass = ClassName(DEFAULT_PACKAGE, className)
        val file = FileSpec.builder(DEFAULT_PACKAGE, "${moduleName}RouteStore")
            .addType(
                TypeSpec.classBuilder(className)
                    .addSuperinterface(IRouteStore::class)
                    .addFunction(getMapFunc
                                     .addStatement("return map")
                                     .build()
                    )
                    .build()
            )
            .build()
        thread(true) {
            codeGenerator.createNewFile(dependencies = Dependencies.ALL_FILES,
                                        packageName = DEFAULT_PACKAGE,
                                        fileName = className,
                                        extensionName = "kt"
            ).bufferedWriter().use { writer ->
                try {
                    file.writeTo(writer)
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    writer.flush()
                    writer.close()
                }
            }
        }
    }

}