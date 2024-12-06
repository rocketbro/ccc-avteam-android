package com.asherpope.ccc_av_team_android.network
import android.util.Log
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
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.KSerializer

class AirtableManager(private val airtableService: AirtableService, private val dataManager: DataManager) {
    private val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    suspend fun fetchWorkflows() {
        withContext(scope.coroutineContext) {
            try {
                val workflows: List<Workflow> = airtableService.fetchTable(TableName.WORKFLOWS, Workflow.serializer())
                Log.d("AirtableManager", "Fetched ${workflows.size} workflows")
                dataManager.saveToDisk(workflows, TableName.WORKFLOWS, ListSerializer(Workflow.serializer()))
            } catch (e: Exception) {
                Log.e("AirtableManager", "Error with workflows: ${e.message}")
            }
        }
    }

    suspend fun fetchWorkflowSteps() {
        withContext(scope.coroutineContext) {
            try {
                val workflowSteps: List<WorkflowStep> = airtableService.fetchTable(TableName.WORKFLOW_STEPS, WorkflowStep.serializer())
                dataManager.saveToDisk(workflowSteps, TableName.WORKFLOW_STEPS, ListSerializer(WorkflowStep.serializer()))
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    suspend fun fetchAVImages() {
        withContext(scope.coroutineContext) {
            try {
                val avImages: List<AVImage> = airtableService.fetchTable(TableName.AV_IMAGES, AVImage.serializer())
                dataManager.saveToDisk(avImages, TableName.AV_IMAGES, ListSerializer(AVImage.serializer()))
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    suspend fun fetchTsOptions() {
        withContext(scope.coroutineContext) {
            try {
                val tsOptions: List<TroubleshootingOption> = airtableService.fetchTable(TableName.TROUBLESHOOTING_OPTIONS, TroubleshootingOption.serializer())
                dataManager.saveToDisk(tsOptions, TableName.TROUBLESHOOTING_OPTIONS, ListSerializer(TroubleshootingOption.serializer()))
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    suspend fun fetchQuickFixes() {
        withContext(scope.coroutineContext) {
            try {
                val quickFixes: List<QuickFix> = airtableService.fetchTable(TableName.QUICK_FIXES, QuickFix.serializer())
                dataManager.saveToDisk(quickFixes, TableName.QUICK_FIXES, ListSerializer(QuickFix.serializer()))
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    suspend fun fetchAVCategories() {
        withContext(scope.coroutineContext) {
            try {
                val avCategories: List<AVCategory> = airtableService.fetchTable(TableName.AV_CATEGORIES, AVCategory.serializer())
                dataManager.saveToDisk(avCategories, TableName.AV_CATEGORIES, ListSerializer(AVCategory.serializer()))
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