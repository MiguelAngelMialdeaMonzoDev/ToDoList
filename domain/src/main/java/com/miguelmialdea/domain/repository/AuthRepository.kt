package com.miguelmialdea.domain.repository

import com.miguelmialdea.domain.model.UserModel

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<UserModel>
    suspend fun register(name: String, email: String, password: String): Result<UserModel>
}