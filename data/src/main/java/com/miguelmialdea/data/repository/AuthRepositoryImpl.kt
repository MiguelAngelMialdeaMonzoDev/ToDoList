package com.miguelmialdea.data.repository

import android.content.SharedPreferences
import com.miguelmialdea.data.dto.toDomain
import com.miguelmialdea.data.remote.AuthApi
import com.miguelmialdea.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val api: AuthApi,
    private val preferences: SharedPreferences
) : AuthRepository {
    override suspend fun login(email: String, password: String) = try {
        val response = api.login(email, password)
        saveToken(response.token)
        Result.success(response.toDomain())
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun register(name: String, email: String, password: String) = try {
        val response = api.register(name, email, password)
        saveToken(response.token)
        Result.success(response.toDomain())
    } catch (e: Exception) {
        Result.failure(e)
    }

    private fun saveToken(token: String) {
        preferences.edit().putString("auth_token", token).apply()
    }
}