package com.matin.happystore.feature.cart

import com.matin.happystore.core.common.DataLoadingState
import com.matin.happystore.core.domain.GetInCartProductFullDetailUseCase
import com.matin.happystore.core.domain.RemoveProductFromCartUseCase
import com.matin.happystore.core.domain.UpdateInCartProductQuantityUseCase
import com.matin.happystore.core.model.InCartProduct
import com.matin.happystore.core.model.Product
import com.matin.happystore.core.testing.MainDispatcherRule
import com.matin.happystore.core.testing.TestHappyStoreRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.math.BigDecimal
import kotlin.test.assertEquals

class CartViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: CartViewModel

    private val repository = TestHappyStoreRepository()
    private val getInCartProductFullDetailUseCase = GetInCartProductFullDetailUseCase(repository)
    private val removeProductFromCartUseCase = RemoveProductFromCartUseCase(repository)
    private val updateInCartProductQuantityUseCase = UpdateInCartProductQuantityUseCase(repository)

    @Before
    fun setUp() {
        viewModel =
            CartViewModel(
                getInCartProductFullDetailUseCase,
                removeProductFromCartUseCase,
                updateInCartProductQuantityUseCase,
            )
    }

    @Test
    fun productsAreLoadedWhileViewModelInitiated() =
        runTest {
            val collectJob =
                launch(UnconfinedTestDispatcher()) {
                    viewModel.cartScreenUiState.collect { result ->
                        assertEquals(
                            CartScreenUiState(
                                loadingState = DataLoadingState.Loaded,
                                inCartProducts =
                                    listOf(
                                        InCartProduct(
                                            Product(
                                                123,
                                                "title1",
                                                BigDecimal("23.3"),
                                                "Jewerly",
                                                "description1",
                                                "http://example.png",
                                                Product.Rating(3.4f, 1000),
                                            ),
                                            quantity = 1,
                                        ),
                                    ),
                            ),
                            result,
                        )
                    }
                }
            collectJob.cancel()
        }

    @Test
    fun productQuantityUpdatesAfterChangingQuantity() =
        runTest {
            val debounceWaitTime = 600L
            val inCartProduct =
                InCartProduct(
                    Product(
                        123,
                        "title1",
                        BigDecimal("23.3"),
                        "Jewerly",
                        "description1",
                        "http://example.png",
                        Product.Rating(3.4f, 1000),
                    ),
                    quantity = 3,
                )

            viewModel.onQuantityChanged(inCartProduct)

            advanceTimeBy(debounceWaitTime)

            val collectJob =
                launch(UnconfinedTestDispatcher()) {
                    viewModel.cartScreenUiState.collect { products ->
                        assertEquals(3, products.inCartProducts.first { it.product.id == 123 }.quantity)
                    }
                }

            collectJob.cancel()
        }

    @Test
    fun inCartProductsUpdateAfterRemovingProductsFromCart() =
        runTest {
            val removedProduct =
                InCartProduct(
                    Product(
                        123,
                        "title1",
                        BigDecimal("23.3"),
                        "Jewerly",
                        "description1",
                        "http://example.png",
                        Product.Rating(3.4f, 1000),
                    ),
                    quantity = 1,
                )
            viewModel.removeItem(removedProduct)

            val collectJob =
                launch(UnconfinedTestDispatcher()) {
                    viewModel.cartScreenUiState.collect {
                        assertEquals(true, it.inCartProducts.isEmpty())
                    }
                }

            collectJob.cancel()
        }
}
