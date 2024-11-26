package com.asherpope.ccc_av_team_android.data

import android.content.Context
import android.content.SharedPreferences
import com.asherpope.ccc_av_team_android.data.images.ImageAspectRatio
import com.asherpope.ccc_av_team_android.data.images.ImageSize

class AppDefaults(context: Context) {
    companion object {
        private const val PREFS_NAME = "AppDefaultsPrefs"
        const val FIRST_LAUNCH_KEY = "hasLaunchedBefore"
        const val PULL_DATA_KEY = "pullData"
        const val IMAGE_ASPECT_RATIO_KEY = "imageAspectRatio"
        const val GLOBAL_IMAGE_SIZE_KEY = "globalImageSize"
        const val USER_AUTHENTICATED_KEY = "userAuthenticated"
        const val DATA_FETCHED_KEY = "dataFetched"
    }

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun initializeDefaults() {
        val hasLaunchedBefore = sharedPreferences.getBoolean(FIRST_LAUNCH_KEY, false)

        if (!hasLaunchedBefore) {
            // First launch, set the default values
            with(sharedPreferences.edit()) {
                putBoolean(PULL_DATA_KEY, true)
                putBoolean(DATA_FETCHED_KEY, false)
                putString(IMAGE_ASPECT_RATIO_KEY, ImageAspectRatio.STANDARD.name)
                putString(GLOBAL_IMAGE_SIZE_KEY, ImageSize.MEDIUM.name)
                putBoolean(USER_AUTHENTICATED_KEY, false)
                // Mark that the app has been launched before
                putBoolean(FIRST_LAUNCH_KEY, true)
                apply()
            }
        }
    }
}
// Usage example
// val appDefaults = AppDefaults(context)
// appDefaults.initializeDefaults()