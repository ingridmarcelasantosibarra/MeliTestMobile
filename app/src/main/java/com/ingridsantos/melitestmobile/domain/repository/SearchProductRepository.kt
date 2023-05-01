package com.ingridsantos.melitestmobile.domain.repository

import com.ingridsantos.melitestmobile.domain.entities.Product
import com.ingridsantos.melitestmobile.domain.entities.Result
import kotlinx.coroutines.flow.Flow

interface SearchProductRepository {

    fun getProducts(query: String): Flow<Result<List<Product>>>
}
