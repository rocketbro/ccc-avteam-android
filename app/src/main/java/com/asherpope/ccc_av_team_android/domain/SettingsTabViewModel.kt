package com.asherpope.ccc_av_team_android.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asherpope.ccc_av_team_android.data.AppPreferencesManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class SettingsViewModel(private val preferencesManager: AppPreferencesManager) : ViewModel() {
    val pullData: Flow<Boolean> = preferencesManager.pullData
    val dataFetched: Flow<Boolean> = preferencesManager.dataFetched
    val imageAspectRatio: Flow<String> = preferencesManager.imageAspectRatio
    val globalImageSize: Flow<String> = preferencesManager.globalImageSize
    val userAuthenticated: Flow<Boolean> = preferencesManager.userAuthenticated

    fun updatePullData(value: Boolean) {
        viewModelScope.launch {
            preferencesManager.updatePullData(value)
        }
    }

    fun updateDataFetched(value: Boolean) {
        viewModelScope.launch {
            preferencesManager.updateDataFetched(value)
        }
    }

    fun updateImageAspectRatio(value: String) {
        viewModelScope.launch {
            preferencesManager.updateImageAspectRatio(value)
        }
    }

    fun updateGlobalImageSize(value: String) {
        viewModelScope.launch {
            preferencesManager.updateGlobalImageSize(value)
        }
    }

    fun updateUserAuthenticated(value: Boolean) {
        viewModelScope.launch {
            preferencesManager.updateUserAuthenticated(value)
        }
    }
}
