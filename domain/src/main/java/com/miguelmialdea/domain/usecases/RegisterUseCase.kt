package com.miguelmialdea.domain.usecases

import com.miguelmialdea.domain.model.UserModel
import com.miguelmialdea.domain.repository.AuthRepository

class RegisterUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(name: String, email: String, password: String): Result<UserModel> =
        repository.register(name, email, password)
}