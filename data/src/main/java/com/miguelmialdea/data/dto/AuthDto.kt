package com.miguelmialdea.data.dto

data class AuthDto(
    val userId: String,
    val name: String,
    val email: String,
    val token: String
)