package com.asherpope.ccc_av_team_android.network
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.coroutines.*
import com.asherpope.ccc_av_team_android.data.AVCategory
import com.asherpope.ccc_av_team_android.data.DataManager
import com.asherpope.ccc_av_team_android.data.TableName
import com.asherpope.ccc_av_team_android.data.images.AVImage
import com.asherpope.ccc_av_team_android.data.quickfixes.QuickFix
import com.asherpope.ccc_av_team_android.data.rememberDataManager
import com.asherpope.ccc_av_team_android.data.workflows.TroubleshootingOption
import com.asherpope.ccc_av_team_android.data.workflows.Workflow
import com.asherpope.ccc_av_team_android.data.workflows.WorkflowStep

class AirtableManager(private val airtableService: AirtableService, private val dataManager: DataManager) {
    private val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    suspend fun fetchWorkflows() {
        withContext(scope.coroutineContext) {
            try {
                val workflows: List<Workflow> = airtableService.fetchTable(TableName.WORKFLOWS)
                dataManager.saveToDisk(workflows, TableName.WORKFLOWS)
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    suspend fun fetchWorkflowSteps() {
        withContext(scope.coroutineContext) {
            try {
                val workflowSteps: List<WorkflowStep> = airtableService.fetchTable(TableName.WORKFLOW_STEPS)
                dataManager.saveToDisk(workflowSteps, TableName.WORKFLOW_STEPS)
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    suspend fun fetchAVImages() {
        withContext(scope.coroutineContext) {
            try {
                val avImages: List<AVImage> = airtableService.fetchTable(TableName.AV_IMAGES)
                dataManager.saveToDisk(avImages, TableName.AV_IMAGES)
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    suspend fun fetchTsOptions() {
        withContext(scope.coroutineContext) {
            try {
                val tsOptions: List<TroubleshootingOption> = airtableService.fetchTable(TableName.TROUBLESHOOTING_OPTIONS)
                dataManager.saveToDisk(tsOptions, TableName.TROUBLESHOOTING_OPTIONS)
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    suspend fun fetchQuickFixes() {
        withContext(scope.coroutineContext) {
            try {
                val quickFixes: List<QuickFix> = airtableService.fetchTable(TableName.QUICK_FIXES)
                dataManager.saveToDisk(quickFixes, TableName.QUICK_FIXES)
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    suspend fun fetchAVCategories() {
        withContext(scope.coroutineContext) {
            try {
                val avCategories: List<AVCategory> = airtableService.fetchTable(TableName.AV_CATEGORIES)
                dataManager.saveToDisk(avCategories, TableName.AV_CATEGORIES)
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}

@Composable
fun rememberAirtableManager(airtableService: AirtableService): AirtableManager {
    val dataManager = rememberDataManager()
    return remember { AirtableManager(airtableService, dataManager) }
}

/*
Usage in a composable function:

@Composable
fun SomeComposable() {
    val airtableManager = rememberAirtableManager(airtable)
    // Use airtableManager here
}

*/