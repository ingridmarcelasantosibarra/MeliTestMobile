package com.ingridsantos.melitestmobile.data.api

import com.ingridsantos.melitestmobile.data.model.BodyProductDTO
import com.ingridsantos.melitestmobile.data.model.ResultProductDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface DetailProductApi {

    @GET("items")
    suspend fun getProducts(
        @Query("ids") id: String
    ): List<BodyProductDTO>
}
