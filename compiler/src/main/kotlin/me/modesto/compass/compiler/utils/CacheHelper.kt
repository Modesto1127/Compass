package me.modesto.compass.compiler.utils

import com.google.devtools.ksp.processing.KSPLogger
import me.modesto.compass.DestMeta
import me.modesto.compass.compiler.SPLIT_ITEM
import me.modesto.compass.compiler.SPLIT_KEY_VALUE
import me.modesto.compass.compiler.SPLIT_VALUE
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardOpenOption
import kotlin.io.path.createDirectories
import kotlin.io.path.exists

/**
 * Description.
 *
 * @author Created by Luqian Ma in 2022/5/18
 */
class CacheHelper(private val logger: KSPLogger?,
                  private val outputPath: Path) {

    fun release(map: MutableMap<String, DestMeta>) {
        if (outputPath.exists()) {
            val cacheFileContent = Files.readString(outputPath).trimEnd()
            logger?.warn("cache file content:\n$cacheFileContent")
            cacheFileContent.split(SPLIT_ITEM).forEach { item ->
                val pair = item.split(SPLIT_KEY_VALUE)
                val key = pair[0]
                val valueArray = pair[1].split(SPLIT_VALUE)
                val dest = valueArray[0].trim()
                map[key] = DestMeta(dest = dest)
                logger?.warn("register from cache: $key: $dest")
            }
        }
    }

    fun save(map: Map<String, DestMeta>) {
        logger?.warn("output path: $outputPath")
        if (!outputPath.parent.exists()) outputPath.parent.createDirectories()
        val cacheStr = mutableListOf<String>()
        map.forEach { (key, value) ->
            val content = "$key: ${value.dest}#"
            logger?.warn(content)
            cacheStr.add(content)
        }
        Files.write(outputPath, cacheStr, StandardOpenOption.CREATE)
    }

}