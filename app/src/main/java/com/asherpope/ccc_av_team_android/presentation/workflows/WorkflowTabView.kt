package com.asherpope.ccc_av_team_android.presentation.workflows

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.asherpope.ccc_av_team_android.data.AVCategory
import com.asherpope.ccc_av_team_android.data.TableName
import com.asherpope.ccc_av_team_android.data.rememberDataManager
import com.asherpope.ccc_av_team_android.data.workflows.Workflow

@Composable
fun WorkflowTabView(navController: NavController) {
    // Your imports remain the same

    val dataManager = rememberDataManager()
    val flows = remember { dataManager.loadFromDisk<List<Workflow>>(TableName.WORKFLOWS) ?: listOf(Workflow()) }
    val categories = remember { dataManager.loadFromDisk<List<AVCategory>>(TableName.AV_CATEGORIES) ?: listOf() }

    if (flows.isEmpty()) {
        Text(text = "No workflows saved.")
    } else {
        LazyColumn {
            items(categories) { category ->
                WorkflowSectionView(
                    category = category,
                    flows = filteredWorkflows(flows, category),
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun WorkflowSectionView(
    category: AVCategory,
    flows: List<Workflow>,
    navController: NavController
) {
    if (flows.isNotEmpty()) {
        Text(
            text = category.fields.name,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
        )
        flows.forEach { workflow ->
            ListItem(
                headlineContent = { Text(workflow.fields.title) },
                modifier = Modifier.clickable {
                    navController.navigate("workflow/${workflow.id}")
                }
            )
        }
    }
}

fun filteredWorkflows(flows: List<Workflow>, category: AVCategory): List<Workflow> {
    return flows.filter { it.fields.avCategory.firstOrNull() == category.id }
}

// End of file. No additional code.