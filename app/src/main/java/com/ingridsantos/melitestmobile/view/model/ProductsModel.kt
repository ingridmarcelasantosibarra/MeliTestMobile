package com.ingridsantos.melitestmobile.view.model

import com.ingridsantos.melitestmobile.domain.entities.Product

data class ProductsModel(
    val resultProduct: List<Product> = listOf(),
    val isError: Int = 0,
    val isLoading: Boolean = false,
    val emptyList: Boolean = false
)
