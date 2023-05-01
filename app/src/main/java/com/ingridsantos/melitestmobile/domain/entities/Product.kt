package com.ingridsantos.melitestmobile.domain.entities

data class Product(
    val id: String = "",
    val title: String = "",
    val thumbnail: String = "",
    val condition: String = "",
    val price: String = "",
    val warranty: String = "",
    val attributes: List<Attributes> = listOf(),
    val pictures: List<String> = listOf()
)
