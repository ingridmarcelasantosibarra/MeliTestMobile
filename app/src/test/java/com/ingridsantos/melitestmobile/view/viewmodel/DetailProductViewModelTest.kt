package com.ingridsantos.melitestmobile.view.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ingridsantos.melitestmobile.R
import com.ingridsantos.melitestmobile.core.MainCoroutineRule
import com.ingridsantos.melitestmobile.domain.entities.Product
import com.ingridsantos.melitestmobile.domain.entities.Result
import com.ingridsantos.melitestmobile.domain.entities.TimeOutException
import com.ingridsantos.melitestmobile.domain.exceptions.ExceptionHandler
import com.ingridsantos.melitestmobile.domain.usecases.DetailProductUC
import com.ingridsantos.melitestmobile.view.productdetail.DetailProductViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailProductViewModelTest {

    private val detailProductUC = mockk<DetailProductUC>()
    private val exceptionHandler = mockk<ExceptionHandler>(relaxed = true)

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var detailProductViewModel: DetailProductViewModel

    @Before
    fun setup() {
        detailProductViewModel = DetailProductViewModel(
            detailProductUC,
            exceptionHandler
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun returnsProductSuccessDetail() = mainCoroutineRule.runBlockingTest {
        val product = mockk<Product>()
        var state = detailProductViewModel.productState.value

        coEvery { detailProductUC.invoke("id") } returns flowOf(Result.Success(product))
        detailProductViewModel.getProductDetail("id")

        state = detailProductViewModel.productState.value

        Assert.assertEquals(product, state.product)
        coVerify { detailProductUC.invoke("id") }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun returnsProductSuccessError() = mainCoroutineRule.runBlockingTest {
        var state = detailProductViewModel.productState.value

        coEvery { detailProductUC.invoke("id") } returns flowOf(
            Result.Failure(TimeOutException)
        )
        every { exceptionHandler.manageException(TimeOutException) } returns R.string.error_internal_error_exception
        detailProductViewModel.getProductDetail("id")

        state = detailProductViewModel.productState.value

        Assert.assertEquals(R.string.error_internal_error_exception, state.isError)
        coVerify { detailProductUC.invoke("id") }
    }
}
