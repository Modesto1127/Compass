package me.modesto.compass.compiler.helper

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.TypeSpec
import me.modesto.compass.compiler.COMPASS_MODULE_NAME_PREFIX
import me.modesto.compass.compiler.PACKAGE_NAME
import me.modesto.compass.compiler.REGISTRY_NAME

/**
 * Description.
 *
 * @author Created by Luqian Ma in 2022/5/30
 */
internal class InitGenerator(private val codeGenerator: CodeGenerator,
                             private val logger: KSPLogger?) {

    private val initMethod: FunSpec.Builder = FunSpec.builder("initAll")

    fun generate(modules: ArrayList<String>) {
        modules.forEach { module ->
            logger?.warn("register: $module")
            initMethod.addStatement("${COMPASS_MODULE_NAME_PREFIX}_${module}.initModule()")
        }
        val file = FileSpec.builder(PACKAGE_NAME, REGISTRY_NAME)
                .addType(
                    TypeSpec.objectBuilder(REGISTRY_NAME)
                            .addFunction(initMethod.build())
                            .build()
                )
                .build()
        codeGenerator.createNewFile(dependencies = Dependencies.ALL_FILES,
                                    packageName = PACKAGE_NAME,
                                    fileName = REGISTRY_NAME,
                                    extensionName = "kt"
        ).bufferedWriter().use { writer ->
            try {
                file.writeTo(writer)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                writer.flush()
            }
        }
    }

}