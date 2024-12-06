package com.asherpope.ccc_av_team_android

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.asherpope.ccc_av_team_android.data.AppPreferencesManager
import com.asherpope.ccc_av_team_android.data.DataManager
import com.asherpope.ccc_av_team_android.network.AirtableManager
import com.asherpope.ccc_av_team_android.network.airtable
import com.asherpope.ccc_av_team_android.presentation.ContentView
import com.asherpope.ccc_av_team_android.presentation.components.SplashScreen
import kotlinx.coroutines.*

class MainActivity : ComponentActivity() {

    private val coroutineScope = CoroutineScope(Dispatchers.Default)
    private var showHome by mutableStateOf(false)
    private lateinit var appPreferencesManager: AppPreferencesManager
    private lateinit var airtableManager: AirtableManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appPreferencesManager = AppPreferencesManager(this)

        // Initialize Airtable and AirtableManager
        val dataManager = DataManager(this)
        airtableManager = AirtableManager(airtable, dataManager)

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val pullData by appPreferencesManager.pullData.collectAsState(initial = true)
                    var dataFetched by remember { mutableStateOf(false) }

                    LaunchedEffect(dataFetched) {
                        if (!dataFetched) {
                            showHome = false
                        }
                    }

                    if (showHome) {
                        ContentView()
                    } else {
                        SplashScreen(
                            pullData = pullData,
                            dataFetched = dataFetched
                        ) {
                            if (pullData) {
                                lifecycleScope.launch {
                                    loadData()
                                }
                            } else {
                                dataFetched = true
                                showHome = true
                            }
                        }
                    }
                }
            }
        }
    }

    // Asynchronous data loading function
    private fun loadData() {
        coroutineScope.launch {
            val jobs = listOf(
                launch { fetchAndSaveWorkflows() },
                launch { fetchAndSaveWorkflowSteps() },
                launch { fetchAndSaveAVImages() },
                launch { fetchAndSaveTsOptions() },
                launch { fetchAndSaveQuickFixes() },
                launch { fetchAndSaveAVCategories() }
            )

            jobs.joinAll()

            withContext(Dispatchers.Main) {
                Log.i(TAG, "Fetched all Airtable data")
                appPreferencesManager.updateDataFetched(true)
                showHome = true
            }
        }
    }

    // Fetch and save functions
    private suspend fun fetchAndSaveWorkflows() {
        try {
            airtableManager.fetchWorkflows()
            Log.i(TAG, "Downloaded Workflows")
        } catch (e: Exception) {
            Log.e(TAG, "Error: ${e.localizedMessage}")
        }
    }

    private suspend fun fetchAndSaveWorkflowSteps() {
        try {
            airtableManager.fetchWorkflowSteps()
            Log.i(TAG, "Downloaded WorkflowSteps")
        } catch (e: Exception) {
            Log.e(TAG, "Error: ${e.localizedMessage}")
        }
    }

    private suspend fun fetchAndSaveAVImages() {
        try {
            airtableManager.fetchAVImages()
            Log.i(TAG, "Downloaded AVImages")
        } catch (e: Exception) {
            Log.e(TAG, "Error: ${e.localizedMessage}")
        }
    }

    private suspend fun fetchAndSaveTsOptions() {
        try {
            airtableManager.fetchTsOptions()
            Log.i(TAG, "Downloaded TroubleshootingOptions")
        } catch (e: Exception) {
            Log.e(TAG, "Error: ${e.localizedMessage}")
        }
    }

    private suspend fun fetchAndSaveQuickFixes() {
        try {
            airtableManager.fetchQuickFixes()
            Log.i(TAG, "Downloaded QuickFixes")
        } catch (e: Exception) {
            Log.e(TAG, "Error: ${e.localizedMessage}")
        }
    }

    private suspend fun fetchAndSaveAVCategories() {
        try {
            airtableManager.fetchAVCategories()
            Log.i(TAG, "Downloaded AVCategories")
        } catch (e: Exception) {
            Log.e(TAG, "Error: ${e.localizedMessage}")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}
