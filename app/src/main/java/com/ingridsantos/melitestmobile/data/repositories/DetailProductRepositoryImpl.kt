package com.ingridsantos.melitestmobile.data.repositories

import com.ingridsantos.melitestmobile.data.api.DetailProductApi
import com.ingridsantos.melitestmobile.domain.entities.Product
import com.ingridsantos.melitestmobile.domain.entities.Result
import com.ingridsantos.melitestmobile.domain.repository.DetailProductRepository
import com.ingridsantos.melitestmobile.domain.repository.DomainExceptionRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DetailProductRepositoryImpl @Inject constructor(
    private val detailProductApi: DetailProductApi,
    private val domainExceptionRepository: DomainExceptionRepository
) : DetailProductRepository {

    override fun getProductDetail(id: String) = flow<Result<Product>> {
        val products = detailProductApi.getProducts(id)
        emit(
            Result.Success(
                products.first().body.toProduct()
            )
        )
    }.catch {
        emit(Result.Failure(domainExceptionRepository.manageError(it)))
    }
}
