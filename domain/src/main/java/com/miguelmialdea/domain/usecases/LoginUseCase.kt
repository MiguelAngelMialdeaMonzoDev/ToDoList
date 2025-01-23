package com.miguelmialdea.domain.usecases

import com.miguelmialdea.domain.model.UserModel
import com.miguelmialdea.domain.repository.AuthRepository

class LoginUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(email: String, password: String): Result<UserModel> =
        repository.login(email, password)
}