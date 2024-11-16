package com.matin.products

import com.matin.data.HappyStoreRepository
import com.matin.happystore.core.domain.AddProductToCartUseCase
import com.matin.happystore.core.domain.GetInCartProductIdsUseCase
import com.matin.happystore.core.domain.GetProductsUseCase
import com.matin.happystore.core.domain.RemoveProductFromCartUseCase
import com.matin.happystore.core.model.Product
import com.matin.happystore.core.model.ui.UiProduct
import com.matin.happystore.core.model.ui.UiProductsAndFilters
import com.matin.happystore.core.testing.MainDispatcherRule
import com.matin.happystore.core.testing.TestHappyStoreRepository
import com.matin.products.helper.ProductListUiHelper
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import java.math.BigDecimal
import kotlin.test.Test

class ProductsViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val testScope = TestScope(UnconfinedTestDispatcher())

    private lateinit var viewModel: ProductsViewModel
    private val repository: HappyStoreRepository = TestHappyStoreRepository()
    private val getProductsUseCase = GetProductsUseCase(repository)
    private val getInCartProductIdsUseCase = GetInCartProductIdsUseCase(repository)
    private val addProductToCartUseCase = AddProductToCartUseCase(repository)
    private val removeProductFromCartUseCase = RemoveProductFromCartUseCase(repository)
    private val productListUiHelper = ProductListUiHelper()

    @Before
    fun setUp() {
        viewModel =
            ProductsViewModel(
                getProductsUseCase,
                getInCartProductIdsUseCase,
                addProductToCartUseCase,
                removeProductFromCartUseCase,
                productListUiHelper,
            )
    }

    @Test
    fun collectProducts_gets_all_products() =
        testScope.runTest {
            val products =
                listOf(
                    Product(
                        123,
                        "title1",
                        BigDecimal("23.3"),
                        "Jewerly",
                        "description1",
                        "http://example.png",
                        Product.Rating(3.4f, 1000),
                    ),
                    Product(
                        124,
                        "title2",
                        BigDecimal("24.4"),
                        "Jewerly",
                        "description2",
                        "http://example.png",
                        Product.Rating(4.4f, 2000),
                    ),
                )

            val uiProducts =
                products.map {
                    UiProduct(
                        it,
                        isFavorite = false,
                        isExpended = false,
                        // product with id = 123 is in cart in TestDouble
                        isInCart = it.id == 123,
                    )
                }

            val uiProductsAndFilters = UiProductsAndFilters(products = uiProducts)

            val collectJob =
                launch {
                    viewModel.productsScreenUiState.collect { state ->
                        assertEquals(
                            uiProductsAndFilters.products,
                            state.uiProductsAndFilters.products,
                        )
                    }
                }

            collectJob.cancel()
        }

    @Test
    fun addToCart_adds_product_to_cart() =
        testScope.runTest {
            viewModel.intentToAction(intent = ProductsIntent.AddToCard(124))
            val collectJob = launch(UnconfinedTestDispatcher()) {
                viewModel.productsScreenUiState.collect()
            }

            assertEquals(
                2,
                viewModel.productsScreenUiState.value.uiProductsAndFilters.products.filter { it.isInCart }.size
            )

            collectJob.cancel()
        }

    @Test
    fun removeFromCart_removes_product_from_cart() = runTest {
        // There is only one item in Testdouble cart with id: 123
        viewModel.intentToAction(intent = ProductsIntent.RemoveFromCard(123))
        val collectJob =
            launch(UnconfinedTestDispatcher()) { viewModel.productsScreenUiState.collect() }
        assertEquals(
            0,
            viewModel.productsScreenUiState.value.uiProductsAndFilters.products.filter { it.isInCart }.size
        )
        collectJob.cancel()
    }

    @Test
    fun updateFavorite_if_product_is_favorite_then_remove_it_from_favorite_else_add_it_to_favorite() = runTest {
            viewModel.intentToAction(intent = ProductsIntent.UpdateProductFavorite(123))
            val collectJob = launch(UnconfinedTestDispatcher()) {
                viewModel.productsScreenUiState.collect {
                    assertEquals(1, it.uiProductsAndFilters.products.filter { it.isFavorite }.size)
                }
            }
            collectJob.cancel()
        }
}
