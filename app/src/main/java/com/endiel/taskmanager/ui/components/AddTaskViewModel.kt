package com.endiel.taskmanager.ui.components

import androidx.lifecycle.ViewModel
import com.endiel.taskmanager.data.local.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AddTaskViewModel : ViewModel() {
    /**
     * List<Task> = I'm storing a list of Task objects eg. [Task("Study"), Task("Build app)]
     * StateFlow = Holds data and notifies UI when data changes
     * MutableStateFlow = Can change value
     * StateFlow = Read only
     * _tasks = Internal so only ViewModel can change it
     * task = External so it only be read by UI
     * emptyList() = Initial value
     * SIMPLE ANALOGY
     * MutableStateFlow = Live scoreboard
     * _state.value = data on the board
     * UI = Audience watching the board
     * When score changes -> Audience sees it instantly
     */
    private val _tasks = MutableStateFlow<List<Task>>(emptyList()) /* Means I have a
    containter that holds a list of Tasks, starts empty and will notify the UI whenever it changes*/
    val tasks = _tasks.asStateFlow()

    fun addTask(title: String) {
        val newTask = Task(id = (_tasks.value.size + 1), title = title)
        _tasks.value = _tasks.value + newTask
    }

    fun toggleTask(task: Task) {
        _tasks.value = _tasks.value.map {
            if (it.id == task.id) {
                it.copy(isCompleted = !it.isCompleted)
            } else it
        }
    }
}