package com.matin.happystore.ui.stateupdater

import com.matin.happystore.domain.model.Filter
import com.matin.happystore.ui.redux.ApplicationState
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