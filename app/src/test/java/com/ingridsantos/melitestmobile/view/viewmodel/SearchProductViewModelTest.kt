package com.ingridsantos.melitestmobile.view.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ingridsantos.melitestmobile.core.MainCoroutineRule
import com.ingridsantos.melitestmobile.domain.entities.Product
import com.ingridsantos.melitestmobile.domain.entities.Result
import com.ingridsantos.melitestmobile.domain.exceptions.ExceptionHandler
import com.ingridsantos.melitestmobile.domain.usecases.SearchProductUC
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
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

    /*@OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun returnsSearchProductSuccessProducts() = mainCoroutineRule.runBlockingTest {
        val product = mockk<Product>()
        val listProducts = listOf(product)
        val state = searchProductViewModel.productsState.value

        coEvery { searchProductUC.invoke("moto") } returns flowOf(Result.Success(listProducts))
        searchProductViewModel.getProducts("moto")

        Assert.assertEquals(listProducts, state.resultProduct)
    }*/
}
