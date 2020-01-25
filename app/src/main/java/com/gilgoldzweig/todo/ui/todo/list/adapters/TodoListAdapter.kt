package com.gilgoldzweig.todo.ui.todo.list.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.gilgoldzweig.todo.R
import com.gilgoldzweig.todo.models.TodoRecord
import com.gilgoldzweig.todo.ui.todo.list.filter.TodoListFilter
import kotlinx.android.synthetic.main.todo_item.view.*

class TodoListAdapter(todoEvents: TodoEvents) : RecyclerView.Adapter<TodoListAdapter.ViewHolder>(),
    Filterable {

    private var originalList = ArrayList<TodoRecord>()
    private var filteredList: List<TodoRecord> = ArrayList<TodoRecord>()

    private var filter = TodoListFilter(originalList) {
            val result = DiffUtil.calculateDiff(TodoDiffCallback(filteredList, it))
            filteredList = it
            result.dispatchUpdatesTo(this)
        }
    private val listener: TodoEvents = todoEvents

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.todo_item, parent, false)
        )
    }

    override fun getItemCount(): Int = filteredList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(filteredList[position], listener)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(todo: TodoRecord, listener: TodoEvents) {
            itemView.todo_item_title_text.text = todo.title
            itemView.todo_item_description_text.text = todo.content

            itemView.todo_item_delete_image.setOnClickListener {
                listener.onDeleteClicked(todo)
            }

            itemView.setOnClickListener {
                listener.onViewClicked(todo)
            }
        }
    }

    /**
     * Search Filter implementation
     * */
    override fun getFilter(): Filter = filter

    /**
     * Activity uses this method to update todoList with the help of LiveData
     * */
    fun setAllTodoItems(todoItems: List<TodoRecord>) {
        originalList = ArrayList(todoItems)
        filteredList = ArrayList(todoItems)
        filter.originalList = originalList
        notifyDataSetChanged()
    }

    /**
     * RecycleView touch event callbacks
     * */
    interface TodoEvents {
        fun onDeleteClicked(todoRecord: TodoRecord)
        fun onViewClicked(todoRecord: TodoRecord)
    }
}
