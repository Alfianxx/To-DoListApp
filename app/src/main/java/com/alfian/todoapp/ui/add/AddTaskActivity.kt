package com.alfian.todoapp.ui.add

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.alfian.todoapp.R
import com.alfian.todoapp.data.Task
import com.alfian.todoapp.databinding.ActivityAddTaskBinding
import com.alfian.todoapp.ui.ViewModelFactory
import com.alfian.todoapp.utils.DatePickerFragment
import java.text.SimpleDateFormat
import java.util.*

class AddTaskActivity : AppCompatActivity(), DatePickerFragment.DialogDateListener {
    private var dueDateMillis: Long = System.currentTimeMillis()

    private lateinit var binding: ActivityAddTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.add_task)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_save -> {
                //TODO 12 : Create AddTaskViewModel and insert new task to database //Done

                val factory = ViewModelFactory.getInstance(this)
                val addTaskViewModel = ViewModelProvider(this, factory).get(AddTaskViewModel::class.java)

                binding.apply {
                    val title = addEdTitle.text.toString()
                    val description = addEdDescription.text.toString()
                    val dueDate = dueDateMillis

                    when {
                        title.isEmpty() -> {
                            binding.addEdTitle.error = getString(R.string.empty)
                        }
                        description.isEmpty() -> {
                            binding.addEdDescription.error = getString(R.string.empty)
                        }
                        else -> {
                            val task = Task(title = title, description = description, dueDateMillis = dueDate, isCompleted = false)
                            addTaskViewModel.insertNewTask(task)
                            Toast.makeText(this@AddTaskActivity, getString(R.string.task_added), Toast.LENGTH_SHORT).show()
                            finish()
                        }
                    }
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun showDatePicker(view: View) {
        val dialogFragment = DatePickerFragment()
        dialogFragment.show(supportFragmentManager, "datePicker")
    }

    override fun onDialogDateSet(tag: String?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        findViewById<TextView>(R.id.add_tv_due_date).text = dateFormat.format(calendar.time)

        dueDateMillis = calendar.timeInMillis
    }
}