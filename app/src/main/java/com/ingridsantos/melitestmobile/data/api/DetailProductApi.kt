package com.ingridsantos.melitestmobile.data.api

import com.ingridsantos.melitestmobile.data.model.BodyProductDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface DetailProductApi {

    @GET("items")
    suspend fun getProductDetail(
        @Query("ids") id: String
    ): List<BodyProductDTO>
}
