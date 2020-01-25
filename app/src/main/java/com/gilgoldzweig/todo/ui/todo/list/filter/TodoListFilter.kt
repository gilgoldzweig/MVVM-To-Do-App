package com.gilgoldzweig.todo.ui.todo.list.filter

import android.widget.Filter
import com.gilgoldzweig.todo.models.TodoRecord

class TodoListFilter(
    var originalList: List<TodoRecord>,
    val onListFiltered: (List<TodoRecord>) -> Unit
) : Filter() {

    override fun performFiltering(query: CharSequence?): FilterResults {
        val result = if (query.isNullOrEmpty()) {
            originalList
        } else {
            originalList.filter {
                it.title.contains(query, true) || it.content.contains(query, true)
            }
        }

        val filterResults = FilterResults()
        filterResults.values = result
        return filterResults
    }

    override fun publishResults(p0: CharSequence?, result: FilterResults?) {
        if (result == null) return
        val filteredList = result.values as List<TodoRecord>
        onListFiltered(filteredList)
    }
}