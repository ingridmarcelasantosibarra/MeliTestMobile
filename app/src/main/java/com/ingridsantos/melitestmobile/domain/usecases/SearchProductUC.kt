package com.ingridsantos.melitestmobile.domain.usecases

import com.ingridsantos.melitestmobile.domain.repository.SearchProductRepository
import javax.inject.Inject

class SearchProductUC @Inject constructor(
    private val searchProductRepository: SearchProductRepository
) {

    operator fun invoke(query: String) = searchProductRepository.getProducts(query)
}
