package com.asherpope.ccc_av_team_android.data.workflows

data class TroubleshootingOption(
    val id: String,
    val createdTime: String,
    val fields: Fields
) {
    data class Fields(
        val parentStep: List<String>? = null,
        val index: Int,
        val prompt: String,
        val solution: String,
        val imageFileName: String? = null
    )
}
