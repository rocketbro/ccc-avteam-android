package com.asherpope.ccc_av_team_android.data

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.asherpope.ccc_av_team_android.data.images.AVImage
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.net.URL

class DataManager(private val context: Context) {

    val imageCache = mutableMapOf<String, ByteArray>()
    val json = Json { ignoreUnknownKeys = true }

    // Helper function to get the documents directory
    fun getDocumentsDirectory(): File {
        return context.filesDir
    }

    // Generic save function for serializable objects
    inline fun <reified T> saveToDisk(obj: T, table: TableName) {
        val filename = "${table.name}.json"
        val file = File(getDocumentsDirectory(), filename)
        try {
            val jsonString = json.encodeToString(obj)
            file.writeText(jsonString)
        } catch (e: Exception) {
            Log.e("DataManager", "Failed to save $filename: ${e.message}")
        }
    }

    // Generic load function for serializable objects
    inline fun <reified T> loadFromDisk(table: TableName): T? {
        val filename = "${table.name}.json"
        val file = File(getDocumentsDirectory(), filename)
        return try {
            val jsonString = file.readText()
            json.decodeFromString<T>(jsonString)
        } catch (e: Exception) {
            Log.e("DataManager", "Failed to load $filename: ${e.message}")
            null
        }
    }

    // Find an object by ID in a loaded array of serializable and identifiable objects
    inline fun <reified T> findObjectById(table: TableName, id: String): T? where T : Identifiable {
        val objects: List<T>? = loadFromDisk(table)
        return objects?.firstOrNull { it.id == id }
    }

    // MARK: Image Processing

    fun processImages(avImages: List<AVImage>): Map<String, URL> {
        return avImages.mapNotNull { avImage ->
            val fullThumbnailURLString = avImage.fields.image?.firstOrNull()?.thumbnails?.full?.url
            if (fullThumbnailURLString != null) {
                avImage.id to URL(fullThumbnailURLString)
            } else null
        }.toMap()
    }
}

// Create a composable function to get the DataManager instance
@Composable
fun rememberDataManager(): DataManager {
    val context = LocalContext.current
    return remember { DataManager(context) }
}