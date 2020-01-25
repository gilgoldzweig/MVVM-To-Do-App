package com.gilgoldzweig.todo.ui.todo.list

import android.app.Activity
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.gilgoldzweig.todo.R
import com.gilgoldzweig.todo.models.TodoRecord
import com.gilgoldzweig.todo.ui.todo.details.TodoDetailsActivity
import com.gilgoldzweig.todo.ui.todo.list.adapters.TodoListAdapter
import com.gilgoldzweig.todo.consts.Constants
import kotlinx.android.synthetic.main.activity_todo_list.*

class TodoListActivity : AppCompatActivity(), TodoListAdapter.TodoEvents,
    SearchView.OnQueryTextListener {

    private lateinit var todoViewModel: TodoListViewModel

    private lateinit var searchView: SearchView
    private lateinit var todoAdapter: TodoListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_list)
        todoViewModel = ViewModelProviders.of(this).get(TodoListViewModel::class.java)

        todo_list_activity_toolbar.inflateMenu(R.menu.menu_todo_list)
        val menu = todo_list_activity_toolbar.menu
        val searchItem = menu.findItem(R.id.todo_list_menu_search)

        searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(this)
        searchView.isIconified = false

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))

        //Setting up RecyclerView
        todoAdapter = TodoListAdapter(this)
        todo_list_activity_items_rcv.adapter = todoAdapter


        //Setting up ViewModel and LiveData
        todoViewModel.todoListLiveData.observe(this, Observer {
          Log.d("test", it.toString())
            todoAdapter.setAllTodoItems(it)
        })

        //FAB click listener
        todo_list_activity_create_fab.setOnClickListener {
            resetSearchView()
            val intent = Intent(this@TodoListActivity, TodoDetailsActivity::class.java)
            startActivityForResult(intent, Constants.CREATE_TODO_INTENT_KEY)
        }

    }

    override fun onQueryTextSubmit(query: String?): Boolean = true

    override fun onQueryTextChange(newText: String?): Boolean {
        todoAdapter.filter.filter(newText)
        return true
    }

    /**
     * RecyclerView Item callbacks
     * */
    //Callback when user clicks on Delete note
    override fun onDeleteClicked(todoRecord: TodoRecord) {
        todoViewModel.deleteTodo(todoRecord)
    }

    //Callback when user clicks on view note
    override fun onViewClicked(todoRecord: TodoRecord) {
        resetSearchView()
        val intent = Intent(this@TodoListActivity, TodoDetailsActivity::class.java)
        intent.putExtra(Constants.UPDATE_TODO_INTENT_BUNDLE_KEY, todoRecord)
        startActivityForResult(intent, Constants.UPDATE_TODO_INTENT_KEY)
    }


    /**
     * Activity result callback
     * Triggers when Save button clicked from @CreateTodoActivity
     * */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            val todoRecord = data?.getParcelableExtra<TodoRecord>(Constants.UPDATE_TODO_INTENT_BUNDLE_KEY)!!
            when (requestCode) {
                Constants.CREATE_TODO_INTENT_KEY -> {
                    todoViewModel.saveTodo(todoRecord)
                }
                Constants.UPDATE_TODO_INTENT_KEY -> {
                    todoViewModel.updateTodo(todoRecord)
                }
            }
        }
    }

    override fun onBackPressed() {
        resetSearchView()
        super.onBackPressed()
    }

    private fun resetSearchView() {
        if (!searchView.isIconified) {
            searchView.isIconified = true
            return
        }
    }
}
