package com.matin.products


import com.matin.data.HappyStoreRepository
import com.matin.happystore.core.domain.GetProductsUseCase
import com.matin.happystore.core.domain.ProductCategoryFilterGeneratorUseCase
import com.matin.happystore.core.model.Filter
import com.matin.happystore.core.model.Product
import com.matin.happystore.core.model.ui.ProductsAndFilters
import com.matin.happystore.core.model.ui.UiFilter
import com.matin.happystore.core.model.ui.UiProduct
import com.matin.happystore.core.redux.ApplicationState
import com.matin.happystore.core.redux.Store
import com.matin.happystore.core.testing.MainDispatcherRule
import com.matin.happystore.core.testing.TestHappyStoreRepository
import com.matin.happystore.core.ui.ProductFavoriteUpdater
import com.matin.happystore.core.ui.ProductInCartItemUpdater
import com.matin.happystore.core.ui.ProductListReducer
import com.matin.products.stateupdater.ProductExpandUpdater
import com.matin.products.stateupdater.ProductFilterSelectionUpdater
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.math.BigDecimal
import kotlin.test.assertEquals
import kotlin.test.assertTrue


class ProductsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: ProductsViewModel
    private val repository: HappyStoreRepository = TestHappyStoreRepository()

    @Before
    fun setUp() {

        val appState = ApplicationState()
        val store = Store(appState)
        viewModel = ProductsViewModel(
            store,
            GetProductsUseCase(repository),
            ProductCategoryFilterGeneratorUseCase(),
            ProductListReducer(store),
            ProductFavoriteUpdater(),
            ProductExpandUpdater(),
            ProductFilterSelectionUpdater(),
            ProductInCartItemUpdater(),
            ProductListUIStateGenerator()
        )
    }

    @Test
    fun productsAreLoadedWhileViewModelInitiated() = runTest {
        val collectJob =
            launch(UnconfinedTestDispatcher()) { viewModel.store.state.map { it.products } }

        viewModel.store.read { appState ->
            assertEquals(
                listOf(
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
                ), appState.products
            )
        }

        collectJob.cancel()
    }

    @Test
    fun productFavoriteUpdatesAfterUserFavoriteProduct() = runTest {
        val collectJob = launch(UnconfinedTestDispatcher()) { viewModel.store.state }
        viewModel.updateFavoriteIds(124)
        viewModel.store.read { appState ->
            assertTrue(appState.favoriteProductId.contains(124))
        }
        collectJob.cancel()
    }

    @Test
    fun productDescriptionExpandUpdatesAfterUserExpandsItem() = runTest {
        val collectJob = launch(UnconfinedTestDispatcher()) { viewModel.store.state }
        viewModel.updateProductExpand(123)
        assertTrue(viewModel.store.state.value.expandedProductIds.contains(123))
        collectJob.cancel()
    }

    @Test
    fun productsUiStateIsInitiallyLoading() {
        assertEquals(viewModel.productListUiState.value, ProductsScreenUiState.Loading)
    }

    @Test
    fun productsUiStateLoaded() = runTest {
        val collectJob =
            launch(UnconfinedTestDispatcher()) { viewModel.productListUiState.collect() }
        val products = listOf(
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
        val uiProducts = products.map { UiProduct(it, false, false, false, 1) }
        val filters =
            listOf(UiFilter(filter = Filter(value = "Jewerly", displayText = "Jewerly (2)"), false))
        val productsAndFilters = ProductsAndFilters(uiProducts, filters)

        assertEquals(
            ProductsScreenUiState.Success(productsAndFilters),
            viewModel.productListUiState.value
        )

        collectJob.cancel()
    }

    @Test
    fun inCartProductsUpdateAfterAddingProductsToCart() = runTest {
        viewModel.updateInCartItemIds(124)
        viewModel.store.read { appState ->
            assertTrue(appState.inCartProductIds.contains(124))
        }
    }
}