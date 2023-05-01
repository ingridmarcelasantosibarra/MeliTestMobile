package com.ingridsantos.melitestmobile.data.repositories

import app.cash.turbine.test
import com.ingridsantos.melitestmobile.data.api.DetailProductApi
import com.ingridsantos.melitestmobile.data.model.BodyProductDTO
import com.ingridsantos.melitestmobile.data.model.ProductDTO
import com.ingridsantos.melitestmobile.data.repositories.exceptions.ProductsExceptionRepositoryImpl
import com.ingridsantos.melitestmobile.domain.entities.Product
import com.ingridsantos.melitestmobile.domain.entities.UnknownError
import com.ingridsantos.melitestmobile.domain.entities.getFailure
import com.ingridsantos.melitestmobile.domain.entities.getSuccess
import com.ingridsantos.melitestmobile.domain.repository.DetailProductRepository
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

class DetailProductRepositoryImplTest {

    private val detailProductApi = mockk<DetailProductApi>(relaxed = true)

    private lateinit var detailProductRepository: DetailProductRepository

    @Before
    fun setup() {
        detailProductRepository = DetailProductRepositoryImpl(
            detailProductApi = detailProductApi,
            domainExceptionRepository = ProductsExceptionRepositoryImpl()
        )
    }

    @Test
    fun whenDetailProductsReturnsBodyProductDTO() = runBlocking {
        val bodyProductDTO: BodyProductDTO = mockk()
        val listBodyProductDTO = listOf(bodyProductDTO)
        val productDTO: ProductDTO = mockk()
        val product: Product = mockk()

        coEvery { detailProductApi.getProductDetail("id") } returns listBodyProductDTO
        coEvery { bodyProductDTO.body } returns productDTO
        every { productDTO.toProduct() } returns product

        val result = detailProductRepository.getProductDetail("id")

        result.test {
            Assert.assertEquals(product, awaitItem().getSuccess())
            awaitComplete()
        }

        coVerify { detailProductApi.getProductDetail("id") }
        coVerify { bodyProductDTO.body }
        verify { productDTO.toProduct() }
        confirmVerified(bodyProductDTO, productDTO)
    }

    @Test
    fun whenDetailProductsReturnException() = runBlocking {
        coEvery { detailProductApi.getProductDetail("id") } throws UnknownError()

        val result = detailProductRepository.getProductDetail("id")

        result.test {
            Assert.assertEquals(UnknownError(), awaitItem().getFailure())
            awaitComplete()
        }

        coVerify { detailProductApi.getProductDetail("id") }
    }

    @After
    fun tearDown() {
        confirmVerified(detailProductApi)
    }
}
