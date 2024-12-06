package com.asherpope.ccc_av_team_android.network

import android.util.Log
import com.asherpope.ccc_av_team_android.data.AVCategory
import com.asherpope.ccc_av_team_android.data.TableName
import com.asherpope.ccc_av_team_android.data.images.AVImage
import com.asherpope.ccc_av_team_android.data.quickfixes.QuickFix
import com.asherpope.ccc_av_team_android.data.workflows.TroubleshootingOption
import com.asherpope.ccc_av_team_android.data.workflows.Workflow
import com.asherpope.ccc_av_team_android.data.workflows.WorkflowStep
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.bodyAsText
import io.ktor.http.*
import kotlinx.serialization.*
import kotlinx.serialization.json.*

// Your imports remain the same

// Define required operations for interacting with Airtable
interface AirtableService {
    suspend fun <T> fetchTable(table: TableName, serializer: KSerializer<T>): List<T>
}

// Define a generic Airtable record interface
interface AirtableRecord

// Generic wrapper for Airtable response object
@Serializable
data class AirtableResponse<T>(val records: List<T>)

class Airtable(private val baseId: String, private val token: String) : AirtableService {
    private val client = HttpClient()

    @OptIn(ExperimentalSerializationApi::class)
    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        explicitNulls = false
    }

    override suspend fun <T> fetchTable(table: TableName, serializer: KSerializer<T>): List<T> {
        val url = "https://api.airtable.com/v0/$baseId/${table.value}"
        val response: String = client.get(url) {
            headers {
                append(HttpHeaders.Authorization, "Bearer $token")
            }
        }.bodyAsText()

        Log.d("Airtable", "Type: $table")
        Log.d("Airtable", "Raw Response: $response")

        return try {
            val decodedResponse = json.decodeFromString(AirtableResponse.serializer(serializer), response)
            Log.d("Airtable", "Decoded Response: $decodedResponse")
            decodedResponse.records
        } catch (e: SerializationException) {
            Log.e("Airtable", "Error deserializing response: ${e.message}")
            Log.e("Airtable", "Stack trace: ${e.stackTraceToString()}")
            emptyList()
        }
    }
}

// Create an instance of Airtable
val airtable = Airtable(
    baseId = airtableBaseId,
    token = airtableToken
)

// End of file. No additional code.

