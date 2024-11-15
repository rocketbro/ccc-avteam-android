package com.asherpope.ccc_av_team_android.data.images

data class AVImage(
    val id: String,
    val createdTime: String,
    val fields: Fields
) {
    // MARK: - Fields data class
    data class Fields(
        val name: String?,
        val image: List<Image>?
    ) {
        // MARK: - Image data class
        data class Image(
            val id: String,
            val width: Int?,
            val height: Int?,
            val url: String?,
            val filename: String?,
            val size: Int?,
            val type: String?,
            val thumbnails: Thumbnails?
        ) {
            // MARK: - Thumbnails data class
            data class Thumbnails(
                val small: ThumbnailSize?,
                val large: ThumbnailSize?,
                val full: ThumbnailSize?
            ) {
                // MARK: - ThumbnailSize data class
                data class ThumbnailSize(
                    val url: String?,
                    val width: Int?,
                    val height: Int?
                )
            }
        }
    }
}
