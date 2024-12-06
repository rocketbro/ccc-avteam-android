package com.asherpope.ccc_av_team_android.data

import kotlinx.serialization.Serializable

@Serializable
enum class TableName(val value: String) {
    WORKFLOWS("WORKFLOWS"),
    WORKFLOW_STEPS("WORKFLOW_STEPS"),
    AV_IMAGES("AV_IMAGES"),
    TROUBLESHOOTING_OPTIONS("TROUBLESHOOTING_OPTIONS"),
    QUICK_FIXES("QUICK_FIXES"),
    AV_CATEGORIES("AV_CATEGORIES");
}