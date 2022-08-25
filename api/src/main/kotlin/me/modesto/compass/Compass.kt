package me.modesto.compass

/**
 * 路由组件的入口
 *
 * @author Created by Luqian Ma in 2022/5/18
 */
object Compass {

    private lateinit var wrongDestRequest: DestRequest

    // 路由表
    private val map: MutableMap<String, DestMeta> = mutableMapOf()

    /**
     * 初始化路由表
     *
     * @author Created by Luqian Ma in 2022/5/31
     */
    private fun initIfNeed() {
        if (map.isEmpty()) CompassRegistry.initAll()
    }

    /**
     * 注册路由
     *
     * @param dest [String]   路由地址
     * @param meta [DestMeta] 路由地址的元数据
     * @author Created by Luqian Ma in 2022/5/31
     */
    fun registerDest(dest: String, meta: DestMeta) {
        map[dest] = meta
    }

    /**
     * 根据传入的路由地址获取路由请求
     *
     * @author Created by Luqian Ma in 2022/5/31
     */
    fun dest(dest: String, priority: Int = 0): DestRequest {
        initIfNeed()
        val realDest = "${dest}_$priority"
        return if (map.containsKey(realDest)) {
            DestRequest(map[realDest]!!)
        } else {
            WrongDestRequest("dest: $dest, priority: $priority")
        }
    }

}