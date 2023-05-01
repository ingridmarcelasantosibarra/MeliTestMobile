package com.ingridsantos.melitestmobile.view

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.ingridsantos.melitestmobile.view.productsearch.ProductSearchUI

enum class NavPath(
    val route: String
) {
    ProductSearch(route = "productSearch"),
    ProductDetail(route = "productDetail")
}

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = NavPath.ProductSearch.route) {
        composable(NavPath.ProductSearch.route) {
            ProductSearchUI(navController = navController, onClick = { product ->
                navController.navigate(route = "${NavPath.ProductDetail.route}/${product.id}")
            })
        }

        composable(
            "${NavPath.ProductDetail.route}/{productId}",
            arguments = listOf(
                navArgument("productId") {
                    type = NavType.StringType
                }
            )
        ) {
            it.arguments?.getString("productId")?.let { id ->
                ProductDetailUI(product = id)
            }
        }
    }
}
