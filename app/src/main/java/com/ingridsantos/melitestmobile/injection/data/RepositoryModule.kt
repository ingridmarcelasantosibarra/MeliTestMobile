package com.ingridsantos.melitestmobile.injection.data

import com.ingridsantos.melitestmobile.data.api.DetailProductApi
import com.ingridsantos.melitestmobile.data.api.SearchProductApi
import com.ingridsantos.melitestmobile.data.repositories.DetailProductRepositoryImpl
import com.ingridsantos.melitestmobile.data.repositories.SearchProductRepositoryImpl
import com.ingridsantos.melitestmobile.data.repositories.exceptions.ProductsExceptionRepositoryImpl
import com.ingridsantos.melitestmobile.domain.repository.DetailProductRepository
import com.ingridsantos.melitestmobile.domain.repository.DomainExceptionRepository
import com.ingridsantos.melitestmobile.domain.repository.SearchProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    @ViewModelScoped
    fun searchProductRepository(
        searchProductApi: SearchProductApi,
        @Named(EXCEPTION_SERVICE_REPOSITORY) domainExceptionRepository: DomainExceptionRepository
    ): SearchProductRepository = SearchProductRepositoryImpl(searchProductApi, domainExceptionRepository)

    @Provides
    @ViewModelScoped
    fun detailProductRepository(
        detailProductApi: DetailProductApi,
        @Named(EXCEPTION_SERVICE_REPOSITORY) domainExceptionRepository: DomainExceptionRepository
    ): DetailProductRepository = DetailProductRepositoryImpl(detailProductApi, domainExceptionRepository)

    @Named(EXCEPTION_SERVICE_REPOSITORY)
    @Provides
    @ViewModelScoped
    fun exceptionRecipeRepository(): DomainExceptionRepository =
        ProductsExceptionRepositoryImpl()

    private const val EXCEPTION_SERVICE_REPOSITORY = "exceptionServiceRepository"
}
