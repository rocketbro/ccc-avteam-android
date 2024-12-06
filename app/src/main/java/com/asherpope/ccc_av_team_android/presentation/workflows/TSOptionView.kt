package com.asherpope.ccc_av_team_android.presentation.workflows

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.asherpope.ccc_av_team_android.data.TableName
import com.asherpope.ccc_av_team_android.data.rememberDataManager
import com.asherpope.ccc_av_team_android.data.workflows.TroubleshootingOption

@Composable
fun TSOptionView(tsOptions: List<String>) {

    val dataManager = rememberDataManager()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            Text(
                text = "Troubleshooting",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 16.dp, bottom = 30.dp)
            )
        }

        items(tsOptions) { tsOption ->
            val option = dataManager.findObjectById<TroubleshootingOption>(TableName.TROUBLESHOOTING_OPTIONS, tsOption)
            option?.let {
                Text(
                    text = it.fields.prompt ?: "error",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.error,
                    fontFamily = FontFamily.Monospace
                )
                Text(
                    text = "\n${it.fields.solution ?: "error"}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontFamily = FontFamily.Monospace
                )
                Divider(
                    modifier = Modifier.padding(vertical = 16.dp)
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
