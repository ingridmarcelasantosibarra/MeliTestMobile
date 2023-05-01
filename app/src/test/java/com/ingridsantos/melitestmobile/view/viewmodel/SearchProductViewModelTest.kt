package com.ingridsantos.melitestmobile.view.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ingridsantos.melitestmobile.R
import com.ingridsantos.melitestmobile.core.MainCoroutineRule
import com.ingridsantos.melitestmobile.domain.entities.Product
import com.ingridsantos.melitestmobile.domain.entities.Result
import com.ingridsantos.melitestmobile.domain.entities.TimeOutException
import com.ingridsantos.melitestmobile.domain.exceptions.ExceptionHandler
import com.ingridsantos.melitestmobile.domain.usecases.SearchProductUC
import com.ingridsantos.melitestmobile.view.productsearch.SearchProductViewModel
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

class SearchProductViewModelTest {

    private val searchProductUC = mockk<SearchProductUC>()
    private val exceptionHandler = mockk<ExceptionHandler>(relaxed = true)

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var searchProductViewModel: SearchProductViewModel

    @Before
    fun setup() {
        searchProductViewModel = SearchProductViewModel(
            searchProductUC,
            exceptionHandler
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun returnsSearchProductSuccessProducts() = mainCoroutineRule.runBlockingTest {
        val product = mockk<Product>()
        val listProducts = listOf(product)
        var state = searchProductViewModel.productsState.value

        coEvery { searchProductUC.invoke("moto") } returns flowOf(Result.Success(listProducts))
        searchProductViewModel.getProducts("moto")

        state = searchProductViewModel.productsState.value

        Assert.assertEquals(listProducts, state.resultProduct)
        coVerify { searchProductUC.invoke("moto") }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun returnsSearchProductSuccessListEmptyProducts() = mainCoroutineRule.runBlockingTest {
        val listProducts = listOf<Product>()
        var state = searchProductViewModel.productsState.value

        coEvery { searchProductUC.invoke("moto") } returns flowOf(Result.Success(listProducts))
        searchProductViewModel.getProducts("moto")

        state = searchProductViewModel.productsState.value

        Assert.assertEquals(true, state.emptyList)
        coVerify { searchProductUC.invoke("moto") }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun returnsSearchProductSuccessError() = mainCoroutineRule.runBlockingTest {
        var state = searchProductViewModel.productsState.value

        coEvery { searchProductUC.invoke("moto") } returns flowOf(
            Result.Failure(TimeOutException)
        )
        every { exceptionHandler.manageException(TimeOutException) } returns R.string.error_internal_error_exception
        searchProductViewModel.getProducts("moto")

        state = searchProductViewModel.productsState.value

        Assert.assertEquals(R.string.error_internal_error_exception, state.isError)
        coVerify { searchProductUC.invoke("moto") }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun onClearClick() = mainCoroutineRule.runBlockingTest {
        val state = searchProductViewModel.productsState.value

        searchProductViewModel.getProducts("moto")

        Assert.assertEquals(listOf<Product>(), state.resultProduct)
    }
}
