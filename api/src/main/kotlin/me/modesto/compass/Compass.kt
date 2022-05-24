package me.modesto.compass

import android.util.Log

/**
 * Description.
 *
 * @author Created by Luqian Ma in 2022/5/18
 */
object Compass {

    private val map: MutableMap<String, DestStruct> = mutableMapOf()

    fun init(storeList: List<IRouteStore>) {
        storeList.forEach { store ->
            store.getMap().forEach { (path, dest) ->
                Log.d("MLQ", "register: $path")
                map[path] = DestStruct(dest)
            }
        }
    }

    fun dest(dest: String, priority: Int = 0): DestStruct {
        val path = "${dest}_$priority"
        if (map.containsKey(path)) {
            return map[path]!!
        } else {
            throw RuntimeException("")
        }
    }

}