package com.miguelmialdea.data.remote

import com.miguelmialdea.data.dto.AuthDto

interface AuthApi {
    suspend fun login(email: String, password: String): AuthDto
    suspend fun register(name: String, email: String, password: String): AuthDto
}