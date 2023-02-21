package com.example.dermoapp.models

import com.google.gson.annotations.SerializedName
import java.util.*

class Consult(
    var automatic: Boolean,
    var color: String,
    @SerializedName("created_at")
    var createdAt: String,
    var distribution: String,
    @SerializedName("injuries_count")
    var injuriesCount: Int,
    @SerializedName("injury_type")
    var injuryType: String,
    @SerializedName("photo_url")
    var photoUrl: String,
    var shape: String,
    @SerializedName("user_id")
    var userId: Int,
    @SerializedName("city")
    var city: String,
    @SerializedName("status")
    var status: Int,
    @SerializedName("specialist_id")
    var specialistId: Int? = null,
    var id: Int? = null
): Model {
}