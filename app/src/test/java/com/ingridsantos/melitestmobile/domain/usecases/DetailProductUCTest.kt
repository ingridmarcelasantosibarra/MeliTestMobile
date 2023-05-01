package com.ingridsantos.melitestmobile.domain.usecases

import app.cash.turbine.test
import com.ingridsantos.melitestmobile.domain.entities.Product
import com.ingridsantos.melitestmobile.domain.entities.Result
import com.ingridsantos.melitestmobile.domain.entities.UnknownError
import com.ingridsantos.melitestmobile.domain.entities.getFailure
import com.ingridsantos.melitestmobile.domain.entities.getSuccess
import com.ingridsantos.melitestmobile.domain.repository.DetailProductRepository
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

class DetailProductUCTest {

    private val detailProductRepository = mockk<DetailProductRepository>(relaxed = true)
    private lateinit var detailProductUC: DetailProductUC

    @Before
    fun setUp() {
        detailProductUC = DetailProductUC(detailProductRepository= detailProductRepository)
    }

    @Test
    fun whenDetailProductsShouldReturnProduct() = runBlocking {
        val product = mockk<Product>()
        coEvery {
            detailProductRepository.getProductDetail("id")
        } returns flowOf(Result.Success(product))

        val result = detailProductUC.invoke("id")

        coVerify(exactly = 1) {
            detailProductRepository.getProductDetail("id")
        }
        confirmVerified(product)
        result.test {
            Assert.assertEquals(product, awaitItem().getSuccess())
            awaitComplete()
        }
    }

    @Test
    fun whenDetailProductsShouldReturnException() = runBlocking {
        val flowProduct: Flow<Result<Product>> = flowOf(Result.Failure(UnknownError()))

        coEvery {
            detailProductRepository.getProductDetail("id")
        } returns flowProduct

        val result = detailProductUC.invoke("id")

        result.test {
            Assert.assertEquals(UnknownError(), awaitItem().getFailure())
            awaitComplete()
        }
        coVerify(exactly = 1) {
            detailProductRepository.getProductDetail("id")
        }
    }

    @After
    fun tearDown() {
        confirmVerified(detailProductRepository)
    }
}
