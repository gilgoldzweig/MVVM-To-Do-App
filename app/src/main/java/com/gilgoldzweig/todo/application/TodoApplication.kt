package com.gilgoldzweig.todo.application

import android.app.Application
import com.gilgoldzweig.todo.db.DatabaseModule

class TodoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        DatabaseModule.initialize(this)
    }
}