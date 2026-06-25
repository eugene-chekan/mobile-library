package com.course.app

import android.app.Application
import com.course.app.di.AppContainer

class LibraryApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}
