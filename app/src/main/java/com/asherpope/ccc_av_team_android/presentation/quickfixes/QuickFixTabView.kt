package com.asherpope.ccc_av_team_android.presentation.quickfixes

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.asherpope.ccc_av_team_android.data.AVCategory
import com.asherpope.ccc_av_team_android.data.TableName
import com.asherpope.ccc_av_team_android.data.quickfixes.QuickFix
import com.asherpope.ccc_av_team_android.data.rememberDataManager

@Composable
fun QuickFixTabView(navController: NavController) {
    val dataManager = rememberDataManager()
    val quickfixes = dataManager.loadFromDisk<List<QuickFix>>(table = TableName.QUICK_FIXES) ?: listOf()
    val categories = dataManager.loadFromDisk<List<AVCategory>>(table = TableName.AV_CATEGORIES) ?: listOf()

    if (quickfixes.isEmpty()) {
        Text("No QuickFixes saved.")
    } else {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(categories) { category ->
                QuickFixSectionView(
                    category = category,
                    quickfixes = filteredQuickFixes(quickfixes, category),
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun QuickFixSectionView(
    category: AVCategory,
    quickfixes: List<QuickFix>,
    navController: NavController
) {
    if (quickfixes.isNotEmpty()) {
        androidx.compose.material3.Text(category.fields.name)
        quickfixes.forEach { quickfix ->
            androidx.compose.material3.TextButton(
                onClick = {
                    navController.navigate("quickfix/${quickfix.id}")
                }
            ) {
                Text(quickfix.fields.title)
            }
        }
    }
}

fun filteredQuickFixes(quickfixes: List<QuickFix>, category: AVCategory): List<QuickFix> {
    return quickfixes.filter { it.fields.avCategory?.first() == category.id }
}
