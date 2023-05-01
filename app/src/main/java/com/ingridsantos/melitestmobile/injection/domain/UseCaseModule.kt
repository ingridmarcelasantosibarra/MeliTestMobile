package com.ingridsantos.melitestmobile.injection.domain

import com.ingridsantos.melitestmobile.domain.repository.DetailProductRepository
import com.ingridsantos.melitestmobile.domain.repository.SearchProductRepository
import com.ingridsantos.melitestmobile.domain.usecases.DetailProductUC
import com.ingridsantos.melitestmobile.domain.usecases.SearchProductUC
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    @ViewModelScoped
    fun searchProductUC(
        searchProductRepository: SearchProductRepository
    ) = SearchProductUC(searchProductRepository)

    @Provides
    @ViewModelScoped
    fun detailProductUC(
        detailProductRepository: DetailProductRepository
    ) = DetailProductUC(detailProductRepository)
}
