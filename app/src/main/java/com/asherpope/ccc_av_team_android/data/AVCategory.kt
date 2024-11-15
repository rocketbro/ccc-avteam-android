package com.asherpope.ccc_av_team_android.data

data class AVCategory(
    val id: String,
    val createdTime: String,
    val fields: Fields
) {
    data class Fields(
        val workflows: List<String>?,
        val quickFixes: List<String>?,
        val description: String?,
        val name: String
    )
}
