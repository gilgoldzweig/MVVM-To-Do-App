package com.gilgoldzweig.todo.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "todo")
@Parcelize()
data class TodoRecord(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,

    val title: String = "",

    val content: String = ""
) : Parcelable