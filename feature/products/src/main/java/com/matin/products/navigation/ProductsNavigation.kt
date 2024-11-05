package com.matin.products.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions

const val PRODUCTS_ROUTE = "products_route"
const val PRODUCT_DETAIL_ROUTE = "product_detail/{product_id}"

fun NavController.navigateToProducts(navOptions: NavOptions) = navigate(PRODUCTS_ROUTE, navOptions)
fun NavController.navigateToProductDetail(productId: Int) = navigate("product_detail/$productId")