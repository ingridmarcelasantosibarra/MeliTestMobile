package com.ingridsantos.melitestmobile.data.model

import com.squareup.moshi.Json

data class BodyProductDTO(
    @field:Json(name = "body")
    val body: ProductDTO
)
