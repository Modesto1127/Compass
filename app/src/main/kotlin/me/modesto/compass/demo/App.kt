package me.modesto.compass.demo

import android.app.Application
import me.modesto.compass.Compass
import me.modesto.compass.store.AppRouteStore
import me.modesto.compass.store.SampleOneRouteStore

/**
 * Description.
 *
 * @author Created by Luqian Ma in 2022/5/24
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Compass.init(listOf(AppRouteStore(), SampleOneRouteStore()))
    }

}