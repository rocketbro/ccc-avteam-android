package com.asherpope.ccc_av_team_android.data

import com.asherpope.ccc_av_team_android.network.AirtableRecord
import kotlinx.serialization.Serializable
import java.util.Date
import java.util.UUID

@Serializable
data class AVCategory(
    override val id: String = UUID.randomUUID().toString(),
    val createdTime: String = Date().toString(),
    val fields: Fields = Fields()
): Identifiable, AirtableRecord {
    @Serializable
    data class Fields(
        val workflows: List<String>? = null,
        val quickFixes: List<String>? = null,
        val description: String? = null,
        val name: String = ""
    )
}
