package com.asherpope.ccc_av_team_android.network

import com.asherpope.ccc_av_team_android.data.TableName
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.*
import kotlinx.serialization.json.*

// Define required operations for interacting with Airtable
interface AirtableService {
    suspend fun <T> fetchTable(table: TableName): List<T>
}

// Generic wrapper for Airtable response object
@Serializable
data class AirtableResponse<T>(val records: List<T>)

class Airtable(private val baseId: String, private val token: String) : AirtableService {
    private val client = HttpClient()
    private val json = Json { ignoreUnknownKeys = true }

    override suspend fun <T> fetchTable(table: TableName): List<T> {
        val url = "https://api.airtable.com/v0/$baseId/${table.value}"
        val response: String = client.get(url) {
            headers {
                append(HttpHeaders.Authorization, "Bearer $token")
            }
        }.body()

        val decodedResponse = json.decodeFromString<AirtableResponse<T>>(response)
        return decodedResponse.records
    }
}

// Create an instance of Airtable
val airtable = Airtable(
    baseId = airtableBaseId,
    token = airtableToken
)

