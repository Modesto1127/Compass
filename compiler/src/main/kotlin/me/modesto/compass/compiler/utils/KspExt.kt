package me.modesto.compass.compiler.utils

import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSType
import com.squareup.kotlinpoet.ksp.KotlinPoetKspPreview
import com.squareup.kotlinpoet.ksp.toClassName

/**
 * Description.
 *
 * @author Created by Luqian Ma in 2022/5/18
 */
internal fun KSAnnotated.findAnnotationWithType(target: KSType): KSAnnotation? {
    return annotations.find { it.annotationType.resolve() == target }
}

@OptIn(KotlinPoetKspPreview::class)
internal inline fun <reified T> KSAnnotation.getMember(name: String): T {
    val matchingArg = arguments.find { it.name?.asString() == name }
        ?: error(
            "No member name found for '$name'. All arguments: ${arguments.map { it.name?.asString() }}"
        )
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
        is KSType -> argValue.toClassName() as T
        else -> {
            argValue as? T ?: error("No value found for $name. Was ${matchingArg.value}")
        }
    }
}