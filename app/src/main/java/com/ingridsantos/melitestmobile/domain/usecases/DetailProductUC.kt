package com.ingridsantos.melitestmobile.domain.usecases

import com.ingridsantos.melitestmobile.domain.repository.DetailProductRepository
import javax.inject.Inject

class DetailProductUC @Inject constructor(
    private val detailProductRepository: DetailProductRepository
) {

    operator fun invoke(id: String) = detailProductRepository.getProductDetail(id)
}
