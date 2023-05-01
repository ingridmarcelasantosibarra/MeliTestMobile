package com.ingridsantos.melitestmobile.data.model

import com.squareup.moshi.Json

data class AttributesDTO(
    @field:Json(name = "name")
    val name: String? = "",
    @field:Json(name = "value_name")
    val valueName: String? = ""
)
