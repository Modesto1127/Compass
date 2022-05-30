package me.modesto.compass.demo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Description.
 *
 * @author Created by Luqian Ma in 2022/5/30
 */
@Parcelize
data class Testcase(val name: String,
                    val auto: Boolean,
                    val title: String) : Parcelable