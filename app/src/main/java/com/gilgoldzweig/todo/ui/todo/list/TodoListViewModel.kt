package com.gilgoldzweig.todo.ui.todo.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.gilgoldzweig.todo.db.DatabaseModule
import com.gilgoldzweig.todo.db.TodoDao
import com.gilgoldzweig.todo.models.TodoRecord
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class TodoListViewModel : ViewModel() {

    private var executorService: ExecutorService = Executors.newCachedThreadPool()

    private val todoDao: TodoDao = DatabaseModule.todoDao

    val todoListLiveData: LiveData<List<TodoRecord>> by lazy {
        todoDao.getAllTodoList()
    }

    fun saveTodo(todo: TodoRecord) {
        executorService.execute {
            todoDao.saveTodo(todo)
        }
    }

    fun updateTodo(todo: TodoRecord) {
        executorService.execute {
            todoDao.updateTodo(todo)
        }
    }


    fun deleteTodo(todo: TodoRecord) {
        executorService.execute {
            todoDao.deleteTodo(todo)
        }
    }
}