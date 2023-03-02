package com.example.dermoapp.daos

import com.fasterxml.jackson.annotation.JsonProperty

data class UpdateStatusDAO(
    @JsonProperty("status") var status: Int,
): DAO {}