package com.matin.products

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.matin.happystore.core.common.Constants
import com.matin.happystore.core.domain.GetSingleProductUseCase
import com.matin.happystore.core.model.Product
import com.matin.happystore.core.model.ui.UiProduct
import com.matin.happystore.core.testing.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DetailScreenViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: DetailScreenViewModel
    private lateinit var getSingleProductUseCase: GetSingleProductUseCase
    private lateinit var savedStateHandle: SavedStateHandle
    private val testScope = TestScope(UnconfinedTestDispatcher())

    @Before
    fun setUp() {
        getSingleProductUseCase = mockk(relaxed = true)
        savedStateHandle = SavedStateHandle()
    }

    @Test
    fun `when viewModel is initialized with product id, then load product data`() =
        testScope.runTest {
            // Given
            val productId = 42
            val mockProduct = createMockProduct(productId)
            savedStateHandle[Constants.PRODUCT_ID] = productId
            coEvery { getSingleProductUseCase(productId) } returns flowOf(mockProduct)

            // When
            viewModel = DetailScreenViewModel(getSingleProductUseCase, savedStateHandle)

            // Then
            viewModel.uiState.test {
                assertEquals(mockProduct, awaitItem().product)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `when loading product details, then show loading state`() = testScope.runTest {
        // When
        viewModel = DetailScreenViewModel(getSingleProductUseCase, savedStateHandle)

        // Then
        viewModel.uiState.test {
            val initialState = awaitItem()
            assertEquals(true, initialState.isLoading)
            assertNull(initialState.error)

            cancelAndIgnoreRemainingEvents()
        }
    }

    private fun createMockProduct(id: Int): UiProduct {
        return UiProduct(
            product = Product(id = id),
            isFavorite = false,
            isExpended = false,
            isInCart = true
        )
    }
}