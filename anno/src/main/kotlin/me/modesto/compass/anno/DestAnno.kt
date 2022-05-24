package me.modesto.compass.anno

/**
 * Description.
 *
 * @property path     [String] Destination path
 * @property priority [Int]    Priority
 * @property desc     [String] Description
 * @author Created by Luqian Ma in 2022/5/18
 */
annotation class DestAnno(val path: String,
                          val priority: Int = 0,
                          val desc: String = "")