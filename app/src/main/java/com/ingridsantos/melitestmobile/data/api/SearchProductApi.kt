package com.ingridsantos.melitestmobile.data.api

import com.ingridsantos.melitestmobile.data.model.ResultProductDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchProductApi {

    @GET("sites/MLA/search")
    suspend fun getProducts(
        @Query("q") query: String
    ): ResultProductDTO
}
