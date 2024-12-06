package com.asherpope.ccc_av_team_android.presentation.settings

import android.content.pm.PackageManager
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.asherpope.ccc_av_team_android.data.images.ImageAspectRatio
import com.asherpope.ccc_av_team_android.data.images.ImageSize
import com.asherpope.ccc_av_team_android.domain.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsTabView(
    viewModel: SettingsViewModel = viewModel()
) {
    val pullData by viewModel.pullData.collectAsState(initial = true)
    val dataFetched by viewModel.dataFetched.collectAsState(initial = true)
    val aspectRatio by viewModel.imageAspectRatio.collectAsState(initial = ImageAspectRatio.STANDARD.name)
    val globalImageSize by viewModel.globalImageSize.collectAsState(initial = ImageSize.MEDIUM.name)
    val userAuthenticated by viewModel.userAuthenticated.collectAsState(initial = false)

    var showingAuthSheet by remember { mutableStateOf(false) }
    var showingConfirmationAlert by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val packageInfo = remember {
        try {
            context.packageManager.getPackageInfo(context.packageName, 0)
        } catch (e: PackageManager.NameNotFoundException) {
            null
        }
    }
    val versionName = packageInfo?.versionName ?: "Unknown"
    val versionCode = packageInfo?.longVersionCode?.toString() ?: "Unknown"

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues)
        ) {
            item {
                ListItem(
                    headlineContent = { Text("Content Data") },
                    supportingContent = {
                        Switch(
                            checked = pullData,
                            onCheckedChange = { viewModel.updatePullData(it) },
                            colors = SwitchDefaults.colors(checkedThumbColor = MaterialTheme.colorScheme.primary)
                        )
                    },
                    trailingContent = { Text("Pull Server Data on Launch") }
                )
            }

            if (userAuthenticated && pullData) {
                item {
                    Button(
                        onClick = { viewModel.updateDataFetched(!dataFetched) },
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text("Refresh App Data")
                    }
                }
            }

            item {
                ListItem(
                    headlineContent = { Text("Workflow Images") },
                    supportingContent = {
                        DropdownMenu(
                            expanded = false, // TODO: Implement dropdown logic
                            onDismissRequest = { /* TODO */ }
                        ) {
                            ImageAspectRatio.values().forEach { ratio ->
                                DropdownMenuItem(
                                    text = { Text(ratio.name) },
                                    onClick = { viewModel.updateImageAspectRatio(ratio.name) }
                                )
                            }
                        }
                    },
                    trailingContent = { Text("Image Aspect Ratio") }
                )
            }

            item {
                ListItem(
                    headlineContent = { Text("User Settings") },
                    supportingContent = {
                        if (userAuthenticated) {
                            Button(
                                onClick = { showingConfirmationAlert = true },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                            ) {
                                Text("Sign Out")
                            }
                        } else {
                            Button(onClick = { showingAuthSheet = true }) {
                                Text("Sign In")
                            }
                        }
                    }
                )
            }

            item {
                if (userAuthenticated) {
                    Text(
                        "Signed in as AV Team Member",
                        fontFamily = FontFamily.Monospace,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "CCC AV Team | Version $versionName.$versionCode\n" +
                                "Developed by Intrinsic Labs LLC\n" +
                                "helloworld@intrinsiclabs.co",
                        fontFamily = FontFamily.Monospace,
                        color = Color.Gray,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }

    if (showingAuthSheet) {
        AuthenticationSheet(
            onDismiss = { showingAuthSheet = false },
            onAuthenticated = { authenticated ->
                viewModel.updateUserAuthenticated(authenticated)
                showingAuthSheet = false
            }
        )
    }

    if (showingConfirmationAlert) {
        AlertDialog(
            onDismissRequest = { showingConfirmationAlert = false },
            title = { Text("Are you sure?") },
            text = { Text("You will need to log in again to view sensitive data in Workflows.") },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.updateUserAuthenticated(false)
                        showingConfirmationAlert = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text("Sign Out")
                }
            },
            dismissButton = {
                Button(onClick = { showingConfirmationAlert = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}
