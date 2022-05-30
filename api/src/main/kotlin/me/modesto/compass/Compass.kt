package me.modesto.compass

/**
 * Description.
 *
 * @author Created by Luqian Ma in 2022/5/18
 */
object Compass {

    private val map: MutableMap<String, DestMeta> = mutableMapOf()

    private fun initIfNeed() {
        if (map.isEmpty()) CompassRegistry.initAll()
    }

    fun registerDest(path: String, destMeta: DestMeta) {
        map[path] = destMeta
    }

    fun dest(dest: String, priority: Int = 0): DestRequest {
        initIfNeed()
        val realDest = "${dest}_$priority"
        if (map.containsKey(realDest)) {
            return DestRequest(map[realDest]!!)
        } else {
            throw RuntimeException("Wrong destination")
        }
    }

}