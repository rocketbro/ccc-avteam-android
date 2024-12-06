package com.asherpope.ccc_av_team_android.presentation.quickfixes

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.asherpope.ccc_av_team_android.data.AVCategory
import com.asherpope.ccc_av_team_android.data.TableName
import com.asherpope.ccc_av_team_android.data.quickfixes.QuickFix
import com.asherpope.ccc_av_team_android.data.rememberDataManager
import com.asherpope.ccc_av_team_android.data.workflows.Workflow
import com.asherpope.ccc_av_team_android.presentation.components.TextChip


@Composable
fun QuickFixScreen(quickFix: QuickFix) {
    val dataManager = rememberDataManager()
    val related = mutableListOf<Workflow>()
    val categories = mutableListOf<AVCategory>()

    // Fetch related workflows
    quickFix.fields.related?.forEach { workflowId ->
        dataManager.findObjectById<Workflow>(TableName.WORKFLOWS, workflowId)?.let {
            related.add(it)
        }
    }

    // Fetch categories
    quickFix.fields.avCategory?.forEach { categoryId ->
        dataManager.findObjectById<AVCategory>(TableName.AV_CATEGORIES, categoryId)?.let {
            categories.add(it)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(22.dp)
    ) {
        Text(
            text = quickFix.fields.title,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            categories.forEach { category ->
                TextChip(text = category.fields.name, tint = Color.Gray)
            }
        }

        Text(
            text = "ISSUE",
            style = MaterialTheme.typography.titleMedium,
            color = Color.Red
        )

        Text(text = quickFix.fields.description)

        Text(
            text = "SOLUTION",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Text(text = quickFix.fields.solution)
    }
}

// Preview function
@Preview
@Composable
fun QuickFixScreenPreview() {
    val sampleQuickFix = QuickFix(
        id = "01",
        createdTime = "today",
        fields = QuickFix.Fields(
            index = 0,
            description = "There is a loud humming sound in the room, and you know it is coming from somewhere on the soundboard.",
            solution = "Stop the entire service and reboot the soundboard. If that doesn't do it, figure out something on YouTube.",
            avCategory = listOf(),
            title = "Strange humming sound in the room.",
            related = listOf()
        )
    )

    QuickFixScreen(quickFix = sampleQuickFix)
}
