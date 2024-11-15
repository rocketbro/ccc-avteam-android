package com.asherpope.ccc_av_team_android.data.workflows

import java.util.UUID
import java.util.Date

// Define the main data class to decode the JSON
data class Workflow(
    val id: String = UUID.randomUUID().toString(),
    val createdTime: String = Date().toString(),
    val fields: Fields = Fields()
) {
    // Define the nested Fields data class
    data class Fields(
        val index: Int = 0,
        val title: String = "",
        val steps: List<String> = emptyList(),
        val description: String = "",
        val avCategory: List<String> = emptyList()
    )
}
