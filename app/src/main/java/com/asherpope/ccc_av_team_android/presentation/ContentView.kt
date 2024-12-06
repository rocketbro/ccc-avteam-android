package com.asherpope.ccc_av_team_android.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.asherpope.ccc_av_team_android.R
import com.asherpope.ccc_av_team_android.presentation.quickfixes.QuickFixTabView
import com.asherpope.ccc_av_team_android.presentation.settings.SettingsTabView
import com.asherpope.ccc_av_team_android.presentation.workflows.WorkflowTabView

@Composable
fun ContentView() {
    val navController = rememberNavController()
    var selectedItem by remember { mutableIntStateOf(0) }
    val items = listOf("Workflows", "QuickFixes", "Settings")

    Scaffold(
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = { Icon(getIconForTab(index), contentDescription = item) },
                        label = { Text(item) },
                        selected = selectedItem == index,
                        onClick = {
                            selectedItem = index
                            navController.navigate(item)
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "Workflows",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("Workflows") { WorkflowTabView(navController) }
            composable("QuickFixes") { QuickFixTabView(navController) }
            composable("Settings") { SettingsTabView() }
        }
    }
}

@Composable
fun getIconForTab(index: Int) = when (index) {
//    0 -> painterResource(id = R.drawable.ic_flowchart_fill)
    0 -> Icons.Default.Checklist
//    1 -> painterResource(id = R.drawable.ic_wrench_fill)
    1 -> Icons.Default.Speed
//    2 -> painterResource(id = R.drawable.ic_settings_fill)
    2 -> Icons.Default.Settings
    else -> throw IllegalArgumentException("Invalid tab index")
}
