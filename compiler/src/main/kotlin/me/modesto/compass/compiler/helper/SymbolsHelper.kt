package me.modesto.compass.compiler.helper

import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSType
import com.squareup.kotlinpoet.ksp.KotlinPoetKspPreview
import com.squareup.kotlinpoet.ksp.toClassName
import me.modesto.compass.compiler.*

/**
 * Description.
 *
 * @author Created by Luqian Ma in 2022/5/30
 */
internal fun Resolver.getCompassDestinations(): Sequence<KSClassDeclaration> {
    return getSymbolsWithAnnotation(COMPASS_DEST_ANNO_NAME)
            .filterIsInstance<KSClassDeclaration>()
}

internal fun Resolver.getCompassModule(): Sequence<KSClassDeclaration> {
    return getSymbolsWithAnnotation(COMPASS_MODULE_ANNO_NAME)
            .filterIsInstance<KSClassDeclaration>()
}

internal fun Resolver.getCompassRegistry(): Sequence<KSClassDeclaration> {
    return getSymbolsWithAnnotation(COMPASS_REGISTRY_ANNO_NAME)
            .filterIsInstance<KSClassDeclaration>()
}

fun Sequence<KSClassDeclaration>.getModuleName(): String? {
    val moduleAnno = this.iterator().next().findAnnotationWithType(COMPASS_MODULE_ANNO_SHORT_NAME) ?: return null
    val moduleAnnoValue = moduleAnno.getMember<String>(VALUE_MODULE_NAME)
    return if (moduleAnnoValue.isNotBlank()) "${COMPASS_MODULE_NAME_PREFIX}_$moduleAnnoValue" else null
}

fun Sequence<KSClassDeclaration>.getModules(): ArrayList<String>? {
    val registryAnno = this.iterator().next().findAnnotationWithType(COMPASS_REGISTRY_ANNO_SHORT_NAME) ?: return null
    val modules = registryAnno.getMember<ArrayList<String>>(VALUE_MODULES)
    return modules.ifEmpty { null }
}

internal fun KSAnnotated.findAnnotationWithType(annoShortName: String): KSAnnotation? {
    return annotations.find { it.shortName.getShortName() == annoShortName }
}

@OptIn(KotlinPoetKspPreview::class)
internal inline fun <reified T> KSAnnotation.getMember(name: String): T {
    val matchingArg = arguments.find { it.name?.asString() == name }
        ?: error("No member name found for '$name'. All arguments: ${arguments.map { it.name?.asString() }}")
    return when (val argValue = matchingArg.value) {
        is List<*> -> {
            if (argValue.isEmpty()) {
                argValue as T
            } else {
                val first = argValue[0]
                if (first is KSType) {
                    argValue.map { (it as KSType).toClassName() } as T
                } else {
                    argValue as T
                }
            }
        }
        is KSType  -> argValue.toClassName() as T
        else       -> {
            argValue as? T ?: error("No value found for $name. Was ${matchingArg.value}")
        }
    }
}