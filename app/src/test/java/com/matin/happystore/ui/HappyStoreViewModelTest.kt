package com.matin.happystore.ui


import com.matin.happystore.MainDispatcherRule
import com.matin.happystore.TestHappyStoreRepository
import com.matin.happystore.domain.HappyStoreRepository
import com.matin.happystore.domain.model.Filter
import com.matin.happystore.domain.model.Product
import com.matin.happystore.domain.usecase.GetProductsUseCase
import com.matin.happystore.domain.usecase.ProductCategoryFilterGeneratorUseCase
import com.matin.happystore.ui.model.ProductsAndFilters
import com.matin.happystore.ui.model.UiFilter
import com.matin.happystore.ui.model.UiProduct
import com.matin.happystore.ui.redux.ApplicationState
import com.matin.happystore.ui.redux.Store
import com.matin.happystore.ui.stateupdater.ProductExpandUpdater
import com.matin.happystore.ui.stateupdater.ProductFavoriteUpdater
import com.matin.happystore.ui.stateupdater.ProductFilterSelectionUpdater
import com.matin.happystore.ui.stateupdater.ProductInCartItemUpdater
import com.matin.happystore.ui.viewmodel.HappyStoreViewModel
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


class HappyStoreViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: HappyStoreViewModel
    private val repository: HappyStoreRepository = TestHappyStoreRepository()

    @Before
    fun setUp() {

        val appState = ApplicationState()
        val store = Store(appState)
        viewModel = HappyStoreViewModel(
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
    fun inCartProductUiStateIsInitiallyIsEmpty() {
        assertEquals(viewModel.inCartProductsUiState.value, CartScreenUiState.Empty)
    }

    @Test
    fun inCartProductUiStateLoaded() = runTest {
        val collectJob =
            launch(UnconfinedTestDispatcher()) { viewModel.inCartProductsUiState.collect() }
        val inCartProducts = listOf(
            UiProduct(
                Product(
                    123,
                    "title1",
                    BigDecimal("23.3"),
                    "Jewerly",
                    "description1",
                    "http://example.png",
                    Product.Rating(3.4f, 1000)
                ), isFavorite = false, isExpended = false, isInCart = true, 1
            )
        )

        viewModel.updateInCartItemIds(123)

        assertEquals(CartScreenUiState.Data(inCartProducts), viewModel.inCartProductsUiState.value)

        collectJob.cancel()
    }

    @Test
    fun productQuantityUpdatesAfterChangingQuantity() = runTest {
        viewModel.updateProductInCartQuantity(123, 3)

        viewModel.store.read { appState ->
            assertEquals(3, appState.inCartProductQuantity[123])
        }
    }

    @Test
    fun inCartProductsUpdateAfterAddingProductsToCart() = runTest {
        viewModel.updateInCartItemIds(124)
        viewModel.store.read { appState ->
            assertTrue(appState.inCartProductIds.contains(124))
        }
    }

    @Test
    fun productQuantityResetAfterRemovingFromCart() = runTest {
        viewModel.updateInCartItemIds(124)
        viewModel.updateProductInCartQuantity(124, 5)
        viewModel.updateInCartItemIds(124)

        viewModel.store.read { appState ->
            assertTrue(appState.inCartProductQuantity[124] == 1)
        }
    }
}