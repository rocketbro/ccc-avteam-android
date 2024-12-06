package com.asherpope.ccc_av_team_android.data.images

import com.asherpope.ccc_av_team_android.data.Identifiable
import com.asherpope.ccc_av_team_android.network.AirtableRecord
import kotlinx.serialization.Serializable


@Serializable
data class AVImage(
    override val id: String,
    val createdTime: String,
    val fields: Fields
): Identifiable, AirtableRecord {
    // MARK: - Fields data class
    @Serializable
    data class Fields(
        val name: String?,
        val image: List<Image>?
    ) {
        // MARK: - Image data class
        @Serializable
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
            @Serializable
            data class Thumbnails(
                val small: ThumbnailSize?,
                val large: ThumbnailSize?,
                val full: ThumbnailSize?
            ) {
                // MARK: - ThumbnailSize data class
                @Serializable
                data class ThumbnailSize(
                    val url: String?,
                    val width: Int?,
                    val height: Int?
                )
            }
        }
    }
}
