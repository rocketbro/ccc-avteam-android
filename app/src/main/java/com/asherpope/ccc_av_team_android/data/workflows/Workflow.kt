package com.asherpope.ccc_av_team_android.data.workflows

import com.asherpope.ccc_av_team_android.data.Identifiable
import com.asherpope.ccc_av_team_android.network.AirtableRecord
import java.util.UUID
import java.util.Date
import kotlinx.serialization.Serializable


// Define the main data class to decode the JSON
@Serializable
data class Workflow (
    override val id: String = UUID.randomUUID().toString(),
    val createdTime: String = Date().toString(),
    val fields: Fields = Fields()
): Identifiable, AirtableRecord {
    // Define the nested Fields data class
    @Serializable
    data class Fields(
        val index: Int = 0,
        val title: String = "",
        val steps: List<String> = emptyList(),
        val description: String = "",
        val avCategory: List<String> = emptyList()
    )
}
