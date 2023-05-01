package com.ingridsantos.melitestmobile.data.model

import com.squareup.moshi.Json

data class PicturesDTO(
    @field:Json(name = "secure_url")
    val secureUrl: String? = ""
)
