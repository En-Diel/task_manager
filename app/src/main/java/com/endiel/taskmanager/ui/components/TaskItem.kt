package com.endiel.taskmanager.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.endiel.taskmanager.data.local.Task

@Composable
fun TaskItem(
    task: Task,
    onToggle: (Task) -> Unit,
    onDelete: (Task) -> Unit
) {
    val decoration = if (task.isCompleted)
        TextDecoration.LineThrough
    else null

    Row(modifier = Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = task.isCompleted,
            onCheckedChange = { onToggle(task) }
        )
        Text(
            text = task.title,
            textDecoration = decoration,
            modifier = Modifier.weight(1f), // KEY FIX
            maxLines = 1, // Makes the text not to push the icon
            overflow = TextOverflow.Ellipsis
        )
        IconButton(
            onClick = {
                onDelete(task)
            }
        ) { Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = "Delete"
            )
        }
    }
}