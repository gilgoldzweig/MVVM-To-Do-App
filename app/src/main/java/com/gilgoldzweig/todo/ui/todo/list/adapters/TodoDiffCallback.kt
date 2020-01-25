package com.gilgoldzweig.todo.ui.todo.list.adapters

import androidx.recyclerview.widget.DiffUtil
import com.gilgoldzweig.todo.models.TodoRecord

class TodoDiffCallback(val old: List<TodoRecord>, val new: List<TodoRecord>) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        old[oldItemPosition] == new[newItemPosition]

    override fun getOldListSize(): Int = old.size

    override fun getNewListSize(): Int = new.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        areItemsTheSame(oldItemPosition, newItemPosition)
}