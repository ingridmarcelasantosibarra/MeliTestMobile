package com.ingridsantos.melitestmobile.data.model

import com.ingridsantos.melitestmobile.domain.entities.Attributes
import com.ingridsantos.melitestmobile.domain.entities.Product
import com.squareup.moshi.Json

data class ProductDTO(
    @field:Json(name = "id")
    val id: String,
    @field:Json(name = "title")
    val title: String,
    @field:Json(name = "thumbnail")
    val thumbnail: String,
    @field:Json(name = "price")
    val price: String,
    @field:Json(name = "condition")
    val condition: String,
    @field:Json(name = "warranty")
    val warranty: String ? = "",
    @field:Json(name = "attributes")
    val attributes: List<AttributesDTO>,
    @field:Json(name = "pictures")
    val pictures: List<PicturesDTO>? = listOf()
) {
    fun toProduct(): Product {
        return Product(
            id = this.id,
            title = this.title,
            thumbnail = this.thumbnail,
            condition = this.condition,
            price = this.price,
            warranty = this.warranty ?: "",
            attributes = this.attributes.map {
                Attributes(
                    valueName = it.valueName ?: "",
                    name = it.name ?: ""
                )
            },
            pictures = this.pictures?.map {
                it.secureUrl ?: ""
            } ?: listOf()
        )
    }
}
