package com.matin.happystore.core.ui

import com.matin.happystore.core.model.Product
import com.matin.happystore.core.redux.ApplicationState
import org.junit.Assert.assertTrue
import org.junit.Test
import java.math.BigDecimal

class ProductFavoriteUpdaterTest {

    @Test
    fun favoriteProductsShouldBeUpdatedAfterAddingNewFavorite() {
        val productFavoriteUpdater = ProductFavoriteUpdater()
        val productsTest = listOf(
            Product(
                123,
                "title1",
                BigDecimal("23.3"),
                "Jewerly",
                "description1",
                "http://example.png",
                Product.Rating(3.4f, 1000)
            ),
            Product(
                124,
                "title2",
                BigDecimal("24.4"),
                "Jewerly",
                "description2",
                "http://example.png",
                Product.Rating(4.4f, 2000)
            )
        )

        val appState = ApplicationState(products = productsTest)
        val newAppState = productFavoriteUpdater(124, appState)

        assertTrue(newAppState.favoriteProductId.contains(124))
    }
}