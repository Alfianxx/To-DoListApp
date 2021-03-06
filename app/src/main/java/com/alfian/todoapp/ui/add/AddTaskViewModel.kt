package com.alfian.todoapp.ui.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alfian.todoapp.data.Task
import com.alfian.todoapp.data.TaskRepository
import kotlinx.coroutines.launch

class AddTaskViewModel(private val taskRepository: TaskRepository) : ViewModel() {

    fun insertNewTask(task: Task) {
        viewModelScope.launch {
            taskRepository.insertTask(task)
        }
    }

}