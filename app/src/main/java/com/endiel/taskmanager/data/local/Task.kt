package com.endiel.taskmanager.data.local

data class Task (
    val id: Int,
    val title: String,
    val isCompleted: Boolean = false
)