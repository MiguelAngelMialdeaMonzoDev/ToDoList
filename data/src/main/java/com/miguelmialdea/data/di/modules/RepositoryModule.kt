package com.miguelmialdea.data.di.modules

import com.miguelmialdea.domain.usecases.LoginUseCase
import com.miguelmialdea.domain.usecases.RegisterUseCase
import org.koin.dsl.module

val repositoryModule = module {
    single { LoginUseCase(get()) }
    single { RegisterUseCase(get()) }
}