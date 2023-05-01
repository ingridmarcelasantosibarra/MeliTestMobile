package com.ingridsantos.melitestmobile.domain.usecases

import app.cash.turbine.test
import com.ingridsantos.melitestmobile.domain.entities.Product
import com.ingridsantos.melitestmobile.domain.entities.Result
import com.ingridsantos.melitestmobile.domain.entities.UnknownError
import com.ingridsantos.melitestmobile.domain.entities.getFailure
import com.ingridsantos.melitestmobile.domain.entities.getSuccess
import com.ingridsantos.melitestmobile.domain.repository.SearchProductRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class SearchProductUCTest {

    private val searchProductRepository = mockk<SearchProductRepository>(relaxed = true)
    private lateinit var searchProductUC: SearchProductUC

    @Before
    fun setUp() {
        searchProductUC = SearchProductUC(searchProductRepository = searchProductRepository)
    }

    @Test
    fun whenSearchProductsShouldReturnProduct() = runBlocking {
        val product = mockk<Product>()
        val listProducts = listOf(product)
        coEvery {
            searchProductRepository.getProducts("moto")
        } returns flowOf(Result.Success(listProducts))

        val result = searchProductUC.invoke("moto")

        coVerify(exactly = 1) {
            searchProductRepository.getProducts("moto")
        }
        confirmVerified(product)
        result.test {
            Assert.assertEquals(listProducts, awaitItem().getSuccess())
            awaitComplete()
        }
    }

    @Test
    fun whenSearchProductsShouldReturnException() = runBlocking {
        val flowProduct: Flow<Result<List<Product>>> = flowOf(Result.Failure(UnknownError()))

        coEvery {
            searchProductRepository.getProducts("moto")
        } returns flowProduct

        val result = searchProductUC.invoke("moto")

        result.test {
            Assert.assertEquals(UnknownError(), awaitItem().getFailure())
            awaitComplete()
        }
        coVerify(exactly = 1) {
            searchProductRepository.getProducts("moto")
        }
    }

    @After
    fun tearDown() {
        confirmVerified(searchProductRepository)
    }
}
