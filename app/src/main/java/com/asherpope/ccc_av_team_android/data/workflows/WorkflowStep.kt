package com.asherpope.ccc_av_team_android.data.workflows

import java.util.UUID
import java.util.Date

data class WorkflowStep(
    val id: String = UUID.randomUUID().toString(),
    val createdTime: String = Date().toString(),
    val fields: Fields = Fields()
) {
    data class Fields(
        val tsOptions: List<String>? = null,
        val index: Int = 0,
        val prompt: String = "",
        val sensitiveData: String? = null,
        val avImageId: List<String>? = null,
        val parentWorkflow: List<String> = emptyList()
    )
}
