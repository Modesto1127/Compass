package me.modesto.compass.compiler.helper

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.ksp.KotlinPoetKspPreview
import com.squareup.kotlinpoet.ksp.toClassName
import me.modesto.compass.compiler.*

/**
 * Description.
 *
 * @author Created by Luqian Ma in 2022/5/29
 */
internal class ModuleGenerator(private val codeGenerator: CodeGenerator,
                               private val moduleName: String,
                               private val logger: KSPLogger?) {

    private val initMethod = FunSpec.builder("initModule")

    @OptIn(KotlinPoetKspPreview::class)
    fun generate(destinations: Sequence<KSClassDeclaration>) {
        destinations.forEach { dest ->
            val destAnno = dest.findAnnotationWithType(COMPASS_DEST_ANNO_SHORT_NAME)
            val path = destAnno?.getMember<String>(VALUE_DEST)
            val priority = destAnno?.getMember<Int>(VALUE_PRIORITY)
            initMethod.addStatement("Compass.registerDest(%S, %T(%S))",
                                    "${path}_$priority",
                                    ClassName(PACKAGE_NAME, TYPE_DEST_META),
                                    dest.toClassName().canonicalName
            )
        }
        val file = FileSpec.builder(PACKAGE_NAME, moduleName)
                .addType(
                    TypeSpec.objectBuilder(moduleName)
                            .addFunction(initMethod.build())
                            .build()
                )
                .build()
        codeGenerator.createNewFile(dependencies = Dependencies.ALL_FILES,
                                    packageName = PACKAGE_NAME,
                                    fileName = moduleName,
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