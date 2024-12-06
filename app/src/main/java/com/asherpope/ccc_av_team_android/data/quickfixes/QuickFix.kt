package com.asherpope.ccc_av_team_android.data.quickfixes

import com.asherpope.ccc_av_team_android.data.Identifiable
import com.asherpope.ccc_av_team_android.network.AirtableRecord
import java.util.UUID
import java.util.Date
import kotlinx.serialization.Serializable


@Serializable
data class QuickFix(
    override val id: String = UUID.randomUUID().toString(),
    val createdTime: String = Date().toString(),
    val fields: Fields = Fields()
): Identifiable, AirtableRecord {
    @Serializable
    data class Fields(
        val index: Int = 0,
        val description: String = "",
        val solution: String = "",
        val avCategory: List<String>? = null,
        val title: String = "",
        val related: List<String>? = null
    )
}