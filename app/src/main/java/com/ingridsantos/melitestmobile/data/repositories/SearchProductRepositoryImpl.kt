package com.ingridsantos.melitestmobile.data.repositories

import com.ingridsantos.melitestmobile.data.api.SearchProductApi
import com.ingridsantos.melitestmobile.domain.entities.Product
import com.ingridsantos.melitestmobile.domain.entities.Result
import com.ingridsantos.melitestmobile.domain.repository.DomainExceptionRepository
import com.ingridsantos.melitestmobile.domain.repository.SearchProductRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchProductRepositoryImpl @Inject constructor(
    private val searchProductApi: SearchProductApi,
    private val domainExceptionRepository: DomainExceptionRepository
) : SearchProductRepository {

    override fun getProducts(query: String) = flow<Result<List<Product>>> {
        val products = searchProductApi.getProducts(query)
        emit(
            Result.Success(
                products.results.map {
                    it.toProduct()
                }
            )
        )
    }.catch {
        emit(Result.Failure(domainExceptionRepository.manageError(it)))
    }
}
