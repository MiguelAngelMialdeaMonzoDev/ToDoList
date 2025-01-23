package com.miguelmialdea.todolist.screens.login

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed class AuthEvent {
    data class Login(val email: String, val password: String) : AuthEvent()
    data class Register(val name: String, val email: String, val password: String) : AuthEvent()
    data class ToggleAuthMode(val isLogin: Boolean) : AuthEvent()
    data object ClearError : AuthEvent()
}

data class AuthState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val isLogin: Boolean = true,
    val isAuthenticated: Boolean = false
)

class AuthViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow(AuthState())
    val state = _state.asStateFlow()

    fun onEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.Login -> {
                validateLogin(event.email, event.password)?.let { error ->
                    setError(error)
                    return
                }
                login(event.email, event.password)
            }

            is AuthEvent.Register -> {
                validateRegister(event.name, event.email, event.password)?.let { error ->
                    setError(error)
                    return
                }
                register(event.name, event.email, event.password)
            }

            is AuthEvent.ToggleAuthMode -> {
                _state.update { it.copy(isLogin = event.isLogin) }
            }

            is AuthEvent.ClearError -> clearError()
        }
    }

    private fun login(email: String, password: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            try {
                authRepository.login(email, password)
                    .onSuccess { user ->
                        _state.update {
                            it.copy(
                                isLoading = false,
                                isAuthenticated = true
                            )
                        }
                    }
                    .onFailure { exception ->
                        setError(exception.message ?: "Error desconocido")
                    }
            } catch (e: Exception) {
                setError("Error de conexión")
            }
        }
    }

    private fun register(name: String, email: String, password: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            try {
                authRepository.register(name, email, password)
                    .onSuccess { user ->
                        _state.update {
                            it.copy(
                                isLoading = false,
                                isAuthenticated = true
                            )
                        }
                    }
                    .onFailure { exception ->
                        setError(exception.message ?: "Error desconocido")
                    }
            } catch (e: Exception) {
                setError("Error de conexión")
            }
        }
    }

    private fun validateLogin(email: String, password: String): String? {
        if (email.isBlank() || password.isBlank()) {
            return "Todos los campos son requeridos"
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return "Email inválido"
        }
        if (password.length < 6) {
            return "La contraseña debe tener al menos 6 caracteres"
        }
        return null
    }

    private fun validateRegister(name: String, email: String, password: String): String? {
        if (name.isBlank()) {
            return "El nombre es requerido"
        }
        return validateLogin(email, password)
    }

    private fun setError(message: String) {
        _state.update {
            it.copy(
                error = message,
                isLoading = false
            )
        }
    }

    private fun clearError() {
        _state.update { it.copy(error = null) }
    }
}