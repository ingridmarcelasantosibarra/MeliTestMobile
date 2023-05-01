package com.ingridsantos.melitestmobile.view.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ingridsantos.melitestmobile.domain.entities.fold
import com.ingridsantos.melitestmobile.domain.exceptions.ExceptionHandler
import com.ingridsantos.melitestmobile.domain.usecases.DetailProductUC
import com.ingridsantos.melitestmobile.view.model.ProductDetailModel
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
class DetailProductViewModel @Inject constructor(
    private val detailProductUC: DetailProductUC,
    private val exceptionHandler: ExceptionHandler
) : ViewModel() {

    private val _productDetailState = MutableStateFlow(ProductDetailModel())
    val productState = _productDetailState.asStateFlow()

    fun getProductDetail(id: String) {
        viewModelScope.launch {
            detailProductUC.invoke(id).map { result ->
                result.fold(
                    onSuccess = {
                        _productDetailState.value = ProductDetailModel(product = it, isLoading = false)
                    },
                    onFailure = {
                        _productDetailState.value = ProductDetailModel(isError = exceptionHandler.manageException(it))
                    }
                )
            }.onStart {
                _productDetailState.value = ProductDetailModel(isLoading = true)
            }.flowOn(Dispatchers.Main).launchIn(viewModelScope)
        }
    }
}
