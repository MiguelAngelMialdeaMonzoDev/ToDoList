package com.miguelmialdea.domain.di.modules

import com.miguelmialdea.domain.usecases.LoginUseCase
import com.miguelmialdea.domain.usecases.RegisterUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { LoginUseCase(get()) }
    single { RegisterUseCase(get()) }
}