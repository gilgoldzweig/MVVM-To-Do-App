package com.gilgoldzweig.todo.ui.todo.details

import android.content.Intent
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.gilgoldzweig.todo.R
import com.gilgoldzweig.todo.consts.Constants
import com.gilgoldzweig.todo.models.TodoRecord
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.activity_create_todo.*

class TodoDetailsActivity : AppCompatActivity() {

    private var todoRecord: TodoRecord? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_todo)

        //Prepopulate existing title and content from intent
        todoRecord = intent.getParcelableExtra(Constants.UPDATE_TODO_INTENT_BUNDLE_KEY)

        val toolbarTitle =
            if (todoRecord == null) {
                "New todo item"
            } else {
                prePopulateData(todoRecord!!)
                "View / Edit todo"
            }

        with(create_todo_activity_toolbar) {
            title = toolbarTitle
            inflateMenu(R.menu.menu_todo_content)
            setNavigationOnClickListener { onBackPressed() }
            setOnMenuItemClickListener { menuItem ->
                if (menuItem.itemId == R.id.todo_content_menu_save) saveTodo()
                true
            }
        }
    }

    private fun prePopulateData(todoRecord: TodoRecord) {
        create_todo_activity_title_input.setText(todoRecord.title)
        create_todo_activity_description_input.setText(todoRecord.content)
    }

    /**
     * Sends the updated information back to calling Activity
     * */
    private fun saveTodo() {
        if (validateFields()) {
            val todo = TodoRecord(
                title = create_todo_activity_title_input.text.toString(),
                content = create_todo_activity_description_input.text.toString()
            )
            val intent = Intent()
            intent.putExtra(Constants.UPDATE_TODO_INTENT_BUNDLE_KEY, todo)
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    /**
     * Validation of EditText
     * */
    private fun validateFields(): Boolean =
        create_todo_activity_title_input.validateField(R.string.pleaseEnterTitle) ||
                create_todo_activity_description_input.validateField((R.string.pleaseEnterContent))

    fun TextInputEditText.validateField(@StringRes errorString: Int): Boolean {
        return if (text.isNullOrEmpty()) {
            error = getString(errorString)
            requestFocus()
            false
        } else {
            error = null
            true
        }
    }
}