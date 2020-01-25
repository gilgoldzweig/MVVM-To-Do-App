package com.gilgoldzweig.todo.db

import android.app.Application
import androidx.room.Room

object DatabaseModule {
    private const val DATABASE_NAME = "todo_db"
    private lateinit var todoDatabase: TodoDatabase
    lateinit var todoDao: TodoDao

    fun initialize(application: Application) {
        todoDatabase = Room.databaseBuilder(application, TodoDatabase::class.java, DATABASE_NAME)
            .build()

        todoDao = todoDatabase.todoDao()
    }
}