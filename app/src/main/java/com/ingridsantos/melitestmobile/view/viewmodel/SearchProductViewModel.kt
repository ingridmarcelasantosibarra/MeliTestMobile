package com.ingridsantos.melitestmobile.view.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ingridsantos.melitestmobile.domain.entities.fold
import com.ingridsantos.melitestmobile.domain.exceptions.ExceptionHandler
import com.ingridsantos.melitestmobile.domain.usecases.SearchProductUC
import com.ingridsantos.melitestmobile.view.model.ProductsModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchProductViewModel @Inject constructor(
    private val searchProductUC: SearchProductUC,
    private val exceptionHandler: ExceptionHandler
) : ViewModel() {

    private val _productsState = MutableStateFlow(ProductsModel())
    val productsState = _productsState.asStateFlow()

    fun getProducts(query: String) {
        viewModelScope.launch {
            searchProductUC.invoke(query).map { result ->
                result.fold(
                    onSuccess = {
                        if (it.isNotEmpty()) {
                            _productsState.value = ProductsModel(resultProduct = it, isLoading = false)
                        } else {
                            _productsState.value = ProductsModel(emptyList = true, isLoading = false)
                        }
                    },
                    onFailure = {
                        _productsState.value = ProductsModel(isError = exceptionHandler.manageException(it))
                    }
                )
            }.onStart {
                _productsState.value = ProductsModel(isLoading = true)
            }.flowOn(Dispatchers.Main).launchIn(viewModelScope)
        }
    }

    fun onClearClick() {
        _productsState.value = ProductsModel(resultProduct = listOf())
    }
}
