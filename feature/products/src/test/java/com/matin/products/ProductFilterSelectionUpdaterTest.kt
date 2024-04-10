package com.matin.products

import com.matin.happystore.core.model.Filter
import com.matin.happystore.core.redux.ApplicationState
import com.matin.products.stateupdater.ProductFilterSelectionUpdater
import org.junit.Assert.assertEquals
import org.junit.Test

class ProductFilterSelectionUpdaterTest {

    @Test
    fun productFilterShouldBeUpdatedWithTheSelectedOne() {
        val productFilterSelectionUpdater = ProductFilterSelectionUpdater()

        val appState = ApplicationState(
            productFilterInfo = ApplicationState.ProductFilterInfo(
                selectedFilter = Filter(
                    "Men's clothe",
                    "Men's clothe"
                )
            )
        )
        val newFilter = Filter("Jewelry", "Jewelry")
        val newAppState = productFilterSelectionUpdater(newFilter, appState)

        assertEquals(newFilter, newAppState.productFilterInfo.selectedFilter)
    }
}