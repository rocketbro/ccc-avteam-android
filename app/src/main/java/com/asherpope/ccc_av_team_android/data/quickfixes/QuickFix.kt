package com.asherpope.ccc_av_team_android.data.quickfixes

import java.util.UUID
import java.util.Date

data class QuickFix(
    val id: String = UUID.randomUUID().toString(),
    val createdTime: String = Date().toString(),
    val fields: Fields = Fields()
) {
    data class Fields(
        val index: Int = 0,
        val description: String = "",
        val solution: String = "",
        val avCategory: List<String>? = null,
        val title: String = "",
        val related: List<String>? = null
    )
}