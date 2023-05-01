package com.ingridsantos.melitestmobile.data.model

import com.squareup.moshi.Json

data class ResultProductDTO(
    @field:Json(name = "results")
    val results: List<ProductDTO>
)
