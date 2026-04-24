package com.endiel.taskmanager.ui.components

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.endiel.taskmanager.data.local.TaskDao

class AddTaskViewModelFactory(private val dao: TaskDao): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AddTaskViewModel(dao) as T
    }
}