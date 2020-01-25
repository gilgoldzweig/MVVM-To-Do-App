package com.gilgoldzweig.todo.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.gilgoldzweig.todo.models.TodoRecord

@Dao
interface TodoDao {

    @Insert
    fun saveTodo(todoRecord: TodoRecord)

    @Delete
    fun deleteTodo(todoRecord: TodoRecord)

    @Update
    fun updateTodo(todoRecord: TodoRecord)

    @Query("SELECT * FROM todo ORDER BY id DESC")
    fun getAllTodoList(): LiveData<List<TodoRecord>>
}