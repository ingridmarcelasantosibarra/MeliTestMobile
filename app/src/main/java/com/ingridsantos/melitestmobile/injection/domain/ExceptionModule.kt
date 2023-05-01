package com.ingridsantos.melitestmobile.injection.domain

import com.ingridsantos.melitestmobile.domain.exceptions.ExceptionHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ExceptionModule {

    @Provides
    @Singleton
    fun exceptionHandlerProvider() = ExceptionHandler()
}
