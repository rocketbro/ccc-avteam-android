package com.asherpope.ccc_av_team_android.data.workflows

import com.asherpope.ccc_av_team_android.data.Identifiable
import com.asherpope.ccc_av_team_android.network.AirtableRecord
import kotlinx.serialization.Serializable


@Serializable
data class TroubleshootingOption(
    override val id: String,
    val createdTime: String,
    val fields: Fields
): Identifiable, AirtableRecord {
    @Serializable
    data class Fields(
        val parentStep: List<String>? = null,
        val index: Int,
        val prompt: String,
        val solution: String,
        val imageFileName: String? = null
    )
}
