package com.endiel.taskmanager.ui.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.endiel.taskmanager.data.local.TaskDataBase
import com.endiel.taskmanager.ui.components.AddTaskDialog
import com.endiel.taskmanager.ui.components.AddTaskViewModel
import com.endiel.taskmanager.ui.components.AddTaskViewModelFactory
import com.endiel.taskmanager.ui.components.TaskItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(modifier: Modifier) {
    // This is to hold and remember the value for the FAB
    val context = LocalContext.current
    val db = TaskDataBase.getDatabase(context)
    val dao = db.taskDao()
    var showDialog by remember { mutableStateOf(false) }
    val viewModel: AddTaskViewModel = viewModel(factory = AddTaskViewModelFactory(dao))

    /**
     * This is a StateFlow(a stream of data over time)
     * "Whenever tasks change, I emit a new list"
     * collectAsState = Converts StateFlow to Compose State(coz compose only understands States
     * not Flow directly)
     */
    val tasks by viewModel.task.collectAsState()
    var checked by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text( text = "Task manager",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold )
                        },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },

        floatingActionButton = {
            FloatingActionButton(
                onClick = { showDialog = true }
            ) { Icon(Icons.Default.Add, contentDescription = "Add Task") }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { paddingValues ->
        // Main content
        LazyColumn(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
            items(tasks) { task ->
                TaskItem(
                    task = task,
                    onToggle = { viewModel.toggleTask(it) },
                    onDelete = { viewModel.deleteTask(task) }
                )
            }
        }
        // SHOW THE TASK DIALOG
        // Check if the dialog is clicked and it's value is true
        if (showDialog) {
            AddTaskDialog(
                onDismiss = { showDialog = false },
                onAddTask = { title ->
                    viewModel.addTask(title)
                    showDialog = false
                }
            )
        }
    }
}