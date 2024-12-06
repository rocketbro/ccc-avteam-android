package com.asherpope.ccc_av_team_android.presentation.workflows

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.asherpope.ccc_av_team_android.data.AppPreferencesManager
import com.asherpope.ccc_av_team_android.data.TableName
import com.asherpope.ccc_av_team_android.data.images.AVImage
import com.asherpope.ccc_av_team_android.data.images.ImageAspectRatio
import com.asherpope.ccc_av_team_android.data.images.ImageAspectRatio.*
import com.asherpope.ccc_av_team_android.data.images.ImageSize
import com.asherpope.ccc_av_team_android.data.rememberDataManager
import com.asherpope.ccc_av_team_android.data.workflows.Workflow
import com.asherpope.ccc_av_team_android.data.workflows.WorkflowStep
import com.asherpope.ccc_av_team_android.presentation.components.RoundedButton
import kotlinx.coroutines.launch
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.filled.Photo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkflowView(workflow: Workflow, onNavigateToTSOption: () -> Unit) {
    val context = LocalContext.current
    val dataManager = rememberDataManager()
    val appPreferencesManager = remember { AppPreferencesManager(context) }
    val coroutineScope = rememberCoroutineScope()

    // Use collectAsState for reactive updates
    val userAuthenticated by appPreferencesManager.userAuthenticated.collectAsState(initial = false)
    val aspectRatio by appPreferencesManager.imageAspectRatio.collectAsState(initial = STANDARD.name)
    val globalImageSize by appPreferencesManager.globalImageSize.collectAsState(initial = ImageSize.MEDIUM.name)

    val steps = remember(workflow) {
        workflow.fields.steps.mapNotNull { stepId ->
            dataManager.findObjectById<WorkflowStep>(TableName.WORKFLOW_STEPS, stepId)
        }
    }

    val imageDict = remember {
        val fetchedImages = dataManager.loadFromDisk<List<AVImage>>(TableName.AV_IMAGES)
        fetchedImages?.let { dataManager.processImages(it) } ?: emptyMap()
    }

    var currentIndex by remember { mutableIntStateOf(0) }
    var showBottomSheet by remember { mutableStateOf(false) }

    val tsOptions = remember(currentIndex) {
        steps.getOrNull(currentIndex)?.fields?.tsOptions ?: emptyList()
    }

    val configuration = LocalConfiguration.current
    val maxImageWidth = if (configuration.screenWidthDp >= 600) 475.dp else 375.dp

    val avImageId = remember(currentIndex) {
        steps.getOrNull(currentIndex)?.fields?.avImageId?.firstOrNull()
    }

    val ratio = when (ImageAspectRatio.valueOf(aspectRatio)) {
        STANDARD -> 4f / 3f
        WIDE -> 16f / 9f
    }

    BottomSheetScaffold(
        sheetContent = {
            if (showBottomSheet) {
                TSOptionView(tsOptions = tsOptions)
            }
        },
        sheetPeekHeight = 0.dp
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Image section
            if (avImageId != null) {
                val avImageUrl = imageDict[avImageId]
                AsyncImage(
                    model = avImageUrl,
                    contentDescription = "Workflow Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(ratio)
                        .padding(horizontal = 16.dp)
                )
            } else {
                Text(
                    text = workflow.fields.title,
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }

            // WorkflowStep Prompt
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                Text(
                    text = steps.getOrNull(currentIndex)?.fields?.prompt ?: "",
                    fontFamily = FontFamily.Monospace
                )
                if (userAuthenticated) {
                    steps.getOrNull(currentIndex)?.fields?.sensitiveData?.let { sensitiveData ->
                        Text(
                            text = sensitiveData,
                            color = Color.Red,
                            fontFamily = FontFamily.Monospace,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                }
            }

            // Navigation buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                RoundedButton(
                    text = "Prev",
                    tint = if (currentIndex > 0) MaterialTheme.colorScheme.primary else Color.Gray,
                    action = {
                        if (currentIndex > 0) {
                            currentIndex--
                        }
                    }
                )
                if (tsOptions.isNotEmpty()) {
                    IconButton(onClick = { showBottomSheet = true }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.Help, contentDescription = "Troubleshooting")
                    }
                }
                RoundedButton(
                    text = "Next",
                    tint = if (currentIndex < steps.size - 1) MaterialTheme.colorScheme.primary else Color.Gray,
                    action = {
                        if (currentIndex < steps.size - 1) {
                            currentIndex++
                        }
                    }
                )
            }
        }
    }

    // Add a toolbar with image aspect ratio picker
    if (avImageId != null) {
        var expanded by remember { mutableStateOf(false) }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            IconButton(
                onClick = { expanded = true },
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                Icon(imageVector = Icons.Default.Photo, contentDescription = "Change Image Size")
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                entries.forEach { imageAspectRatio ->
                    DropdownMenuItem(
                        text = { Text(imageAspectRatio.name) },
                        onClick = {
                            coroutineScope.launch {
                                appPreferencesManager.updateImageAspectRatio(imageAspectRatio.name)
                            }
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}
