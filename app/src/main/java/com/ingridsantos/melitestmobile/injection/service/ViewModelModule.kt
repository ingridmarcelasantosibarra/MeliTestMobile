package com.ingridsantos.melitestmobile.injection.service

import com.ingridsantos.melitestmobile.domain.exceptions.ExceptionHandler
import com.ingridsantos.melitestmobile.domain.usecases.DetailProductUC
import com.ingridsantos.melitestmobile.domain.usecases.SearchProductUC
import com.ingridsantos.melitestmobile.view.viewmodel.DetailProductViewModel
import com.ingridsantos.melitestmobile.view.viewmodel.SearchProductViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @Provides
    fun searchProductsViewModel(
        searchProductUC: SearchProductUC,
        exceptionHandler: ExceptionHandler
    ) = SearchProductViewModel(searchProductUC, exceptionHandler)

    @Provides
    fun detailProductViewModel(
        detailProductUC: DetailProductUC,
        exceptionHandler: ExceptionHandler
    ) = DetailProductViewModel(detailProductUC, exceptionHandler)
}
