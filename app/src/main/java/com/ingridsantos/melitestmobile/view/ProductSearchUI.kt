package com.ingridsantos.melitestmobile.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ingridsantos.melitestmobile.domain.entities.Product
import com.ingridsantos.melitestmobile.view.viewmodel.SearchProductViewModel

@Composable
fun ProductSearchUI(
    onClick: (Product) -> Unit,
    viewModel: SearchProductViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController()
) {
    val state by viewModel.productsState.collectAsState()
    var searchText by rememberSaveable { mutableStateOf("") }
    SearchBarUI(
        searchText = searchText,
        placeholderText = "Search users",
        onSearchTextChanged = {
            searchText = it
            viewModel.getProducts(it)
        },
        onClearClick = {
            searchText = ""
            viewModel.onClearClick()
        },
        onNavigateBack = {
            navController.popBackStack()
        }
    ) {
        if (state.isError != 0) {
            val message = stringResource(id = state.isError)
            NoSearchResults(message = "¡Ah occurred an error! $message", color = Color.Red)
        }

        if (state.emptyList.not()) {
            Products(products = state.resultProduct) {
                onClick.invoke(it)
            }
        } else {
            NoSearchResults(message = "No matches found", color = Color.Black)
        }
    }
}

@Composable
fun Products(products: List<Product>, onClick: (Product) -> Unit) {
    LazyColumn {
        items(products) { product ->
            ProductRow(product = product) {
                onClick(product)
            }
            Divider()
        }
    }
}

@Composable
fun ProductRow(product: Product, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        Icon(
            imageVector = Icons.Filled.Search,
            modifier = Modifier,
            contentDescription = "Resultado producto"
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(product.title, fontSize = 14.sp, fontWeight = FontWeight.Medium, fontFamily = FontFamily.Serif)
    }
}
