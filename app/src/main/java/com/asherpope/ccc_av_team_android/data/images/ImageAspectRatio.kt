package com.asherpope.ccc_av_team_android.data.images

enum class ImageSize(val rawValue: String) {
    SMALL("Small"),
    MEDIUM("Medium"),
    LARGE("Large");

    companion object {
        fun fromRawValue(value: String): ImageSize? = entries.find { it.rawValue == value }
    }
}

enum class ImageAspectRatio(val rawValue: String, val displayName: String) {
    WIDE("wide", "Wide (16x9)"),
    STANDARD("standard", "Standard (4x3)");

    companion object {
        fun fromRawValue(value: String): ImageAspectRatio? = entries.find { it.rawValue == value }
    }
}