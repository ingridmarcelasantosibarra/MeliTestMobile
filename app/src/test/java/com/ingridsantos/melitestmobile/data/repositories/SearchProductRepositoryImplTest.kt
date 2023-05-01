package com.ingridsantos.melitestmobile.data.repositories

import app.cash.turbine.test
import com.ingridsantos.melitestmobile.data.api.SearchProductApi
import com.ingridsantos.melitestmobile.data.model.ProductDTO
import com.ingridsantos.melitestmobile.data.model.ResultProductDTO
import com.ingridsantos.melitestmobile.data.repositories.exceptions.ProductsExceptionRepositoryImpl
import com.ingridsantos.melitestmobile.domain.entities.Product
import com.ingridsantos.melitestmobile.domain.entities.UnknownError
import com.ingridsantos.melitestmobile.domain.entities.getFailure
import com.ingridsantos.melitestmobile.domain.entities.getSuccess
import com.ingridsantos.melitestmobile.domain.repository.SearchProductRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class SearchProductRepositoryImplTest {

    private val searchProductApi = mockk<SearchProductApi>(relaxed = true)

    private lateinit var searchProductRepository: SearchProductRepository

    @Before
    fun setup() {
        searchProductRepository = SearchProductRepositoryImpl(
            searchProductApi = searchProductApi,
            domainExceptionRepository = ProductsExceptionRepositoryImpl()
        )
    }

    @Test
    fun whenSearchProductsReturnsProductDTO() = runBlocking {
        val resultProductDTO: ResultProductDTO = mockk()
        val productDTO: ProductDTO = mockk()
        val product: Product = mockk()
        val listProduct = listOf(product)
        val productsDTO = listOf(productDTO)

        coEvery { searchProductApi.getProducts("moto") } returns resultProductDTO
        coEvery { resultProductDTO.results } returns productsDTO
        every { productDTO.toProduct() } returns product

        val result = searchProductRepository.getProducts("moto")

        result.test {
            Assert.assertEquals(listProduct, awaitItem().getSuccess())
            awaitComplete()
        }

        coVerify { searchProductApi.getProducts("moto") }
        coVerify { resultProductDTO.results }
        verify { productDTO.toProduct() }
        confirmVerified(resultProductDTO, productDTO)
    }

    @Test
    fun whenSearchProductsReturnException() = runBlocking {
        coEvery { searchProductApi.getProducts("moto") } throws UnknownError()

        val result = searchProductRepository.getProducts("moto")

        result.test {
            Assert.assertEquals(UnknownError(), awaitItem().getFailure())
            awaitComplete()
        }

        coVerify { searchProductApi.getProducts("moto") }
    }

    @After
    fun tearDown() {
        confirmVerified(searchProductApi)
    }
}
