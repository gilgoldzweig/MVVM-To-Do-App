package com.gilgoldzweig.todo.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gilgoldzweig.todo.models.TodoRecord

@Database(entities = [TodoRecord::class], version = 1, exportSchema = false)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
}

