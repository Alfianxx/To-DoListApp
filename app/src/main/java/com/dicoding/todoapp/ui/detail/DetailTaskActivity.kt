package com.dicoding.todoapp.ui.detail

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dicoding.todoapp.R
import com.dicoding.todoapp.databinding.ActivityTaskDetailBinding
import com.dicoding.todoapp.ui.ViewModelFactory
import com.dicoding.todoapp.utils.DateConverter
import com.dicoding.todoapp.utils.TASK_ID

class DetailTaskActivity : AppCompatActivity() {

    private lateinit var detailTaskViewModel: DetailTaskViewModel
    private lateinit var binding: ActivityTaskDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelFactory.getInstance(this)
        detailTaskViewModel = ViewModelProvider(this, factory).get(DetailTaskViewModel::class.java)

        //TODO 11 : Show detail task and implement delete action    //Done
        val taskId = intent.getIntExtra(TASK_ID, 0)

        detailTaskViewModel.setTaskId(taskId)
        detailTaskViewModel.task.observe(this, { task ->
            if (task != null) {
                binding.detailEdTitle.setText(task.title)
                binding.detailEdDescription.setText(task.description)
                binding.detailEdDueDate.setText(DateConverter.convertMillisToString(task.dueDateMillis))

                binding.btnDeleteTask.setOnClickListener {
                    showAlertDialog()
                }
            } else {
                Toast.makeText(this, R.string.task_deleted, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun showAlertDialog() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        with(alertDialogBuilder) {
            setTitle(getString(R.string.delete))
            setMessage(getString(R.string.dialog_message))
            setCancelable(false)
            setPositiveButton("yes") { _, _ ->
                detailTaskViewModel.deleteTask()
                finish()
            }
            setNegativeButton("no") { dialog, _ ->
                dialog.cancel()
            }
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }

    }
}