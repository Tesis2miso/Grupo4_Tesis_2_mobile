package com.example.dermoapp.daos

import com.example.dermoapp.models.Consult
import com.example.dermoapp.models.User
import com.fasterxml.jackson.annotation.JsonProperty


data class ConsultDAO(
    var id: Int,
    var automatic: Boolean,
    var color: String,
    @JsonProperty("created_at")
    val created_at: String,
    var distribution: String,
    @JsonProperty("injuries_count")
    var injuries_count: Int,
    @JsonProperty("injury_type")
    var injury_type: String,
    @JsonProperty("photo_url")
    var photo_url: String,
    var shape: String,
    @JsonProperty("user_id")
    var user_id: Int,
    @JsonProperty("city")
    var city: String,
    @JsonProperty("status")
    var status: Int,
    @JsonProperty("diagnosis")
    var diagnosis: String?,
    @JsonProperty("specialist_id")
    var specialist_id: Int?,
    @JsonProperty("specialist")
    var specialist: SpecialistDAO?,
) : DAO {
    fun toConsult(): Consult {
        return Consult(
            automatic, color, created_at, distribution,
            injuries_count, injury_type, photo_url, shape,
            user_id, city, status, diagnosis, specialist?.toSpecialist(),
            specialist_id, id
        )
    }
}