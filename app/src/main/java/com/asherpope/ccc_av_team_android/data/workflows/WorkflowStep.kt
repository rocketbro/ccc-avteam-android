package com.asherpope.ccc_av_team_android.data.workflows

import com.asherpope.ccc_av_team_android.data.Identifiable
import com.asherpope.ccc_av_team_android.network.AirtableRecord
import java.util.UUID
import java.util.Date
import kotlinx.serialization.Serializable


@Serializable
data class WorkflowStep(
    override val id: String = UUID.randomUUID().toString(),
    val createdTime: String = Date().toString(),
    val fields: Fields = Fields()
): Identifiable, AirtableRecord {
    @Serializable
    data class Fields(
        val tsOptions: List<String>? = null,
        val index: Int = 0,
        val prompt: String = "",
        val sensitiveData: String? = null,
        val avImageId: List<String>? = null,
        val parentWorkflow: List<String> = emptyList()
    )
}
