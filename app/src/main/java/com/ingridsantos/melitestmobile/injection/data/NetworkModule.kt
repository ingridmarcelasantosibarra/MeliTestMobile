package com.ingridsantos.melitestmobile.injection.data

import com.ingridsantos.melitestmobile.data.api.DetailProductApi
import com.ingridsantos.melitestmobile.data.api.SearchProductApi
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Named(AUTH_RETROFIT_CLIENT_NAME)
    @Provides
    @Singleton
    fun getInstanceRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.mercadolibre.com/")
            .client(OkHttpClient())
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun searchProductApi(@Named(AUTH_RETROFIT_CLIENT_NAME) retrofit: Retrofit): SearchProductApi {
        return retrofit.create(SearchProductApi::class.java)
    }

    @Provides
    @Singleton
    fun detailProductApi(@Named(AUTH_RETROFIT_CLIENT_NAME) retrofit: Retrofit): DetailProductApi {
        return retrofit.create(DetailProductApi::class.java)
    }

    private const val AUTH_RETROFIT_CLIENT_NAME = "auth_retrofit"
}
