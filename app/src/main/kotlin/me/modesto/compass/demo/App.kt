package me.modesto.compass.demo

import android.app.Application
import me.modesto.compass.anno.ModuleAnno
import me.modesto.compass.anno.RegistryAnno

/**
 * Description.
 *
 * @author Created by Luqian Ma in 2022/5/24
 */
@RegistryAnno(["App", "SampleOne"])
@ModuleAnno("App")
class App : Application()