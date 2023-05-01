package com.ingridsantos.melitestmobile.view.model

import com.ingridsantos.melitestmobile.domain.entities.Product

data class ProductDetailModel(
    val product: Product = Product(),
    val isError: Int = 0,
    val isLoading: Boolean = false
)
