package me.modesto.compass.anno

/**
 * Description.
 *
 * @property dest     [String] Destination path
 * @property priority [Int]    Priority
 * @property desc     [String] Description
 * @author Created by Luqian Ma in 2022/5/18
 */
annotation class DestAnno(val dest: String,
                          val priority: Int = 0,
                          val desc: String = "")