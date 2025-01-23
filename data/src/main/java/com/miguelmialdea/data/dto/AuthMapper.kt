package com.miguelmialdea.data.dto

import com.miguelmialdea.domain.model.UserModel

fun AuthDto.toDomain() = UserModel(
    id = userId,
    name = name,
    email = email
)