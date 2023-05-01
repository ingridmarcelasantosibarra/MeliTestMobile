package com.ingridsantos.melitestmobile.domain.repository

import com.ingridsantos.melitestmobile.domain.entities.Product
import com.ingridsantos.melitestmobile.domain.entities.Result
import kotlinx.coroutines.flow.Flow

interface DetailProductRepository {

    fun getProductDetail(id:String): Flow<Result<Product>>
}
