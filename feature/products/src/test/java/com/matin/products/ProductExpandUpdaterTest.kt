package com.matin.products

import com.matin.happystore.core.model.Product
import com.matin.happystore.core.redux.ApplicationState
import com.matin.products.stateupdater.ProductExpandUpdater
import org.junit.Assert.assertTrue
import org.junit.Test
import java.math.BigDecimal

class ProductExpandUpdaterTest {

    @Test
    fun productExpandStateShouldBeUpdatedAfterNewOneExpanded() {
        val productExpandUpdater = ProductExpandUpdater()
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
        val appState = ApplicationState(products = productsTest, expandedProductIds = emptySet())

        val newAppState = productExpandUpdater(124, appState)

        assertTrue(newAppState.expandedProductIds.contains(124))
    }
}