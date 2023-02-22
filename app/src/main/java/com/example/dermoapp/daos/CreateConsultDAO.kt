package com.example.dermoapp.daos

import com.fasterxml.jackson.annotation.JsonProperty

data class CreateConsultDAO(
    var automatic: Boolean,
    var color: String,
    var distribution: String,
    @JsonProperty("injuries_count")
    var injuries_count: Int?,
    @JsonProperty("injury_type")
    var injury_type: String,
    @JsonProperty("photo_url")
    var photo_url: String?,
    var shape: String
) : DAO {}