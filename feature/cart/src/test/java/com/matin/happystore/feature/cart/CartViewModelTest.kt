package com.matin.happystore.feature.cart

// import com.matin.happystore.core.model.Product
// import com.matin.happystore.core.model.ui.UiProduct
// import com.matin.happystore.core.redux.ApplicationState
// import com.matin.happystore.core.redux.Store
// import com.matin.happystore.core.testing.MainDispatcherRule
// import com.matin.happystore.core.testing.TestHappyStoreRepository
// import com.matin.happystore.core.ui.ProductFavoriteUpdater
// import com.matin.happystore.core.ui.ProductInCartItemUpdater
// import com.matin.happystore.core.ui.ProductListReducer
// import kotlinx.coroutines.flow.collect
// import kotlinx.coroutines.flow.first
// import kotlinx.coroutines.flow.map
// import kotlinx.coroutines.launch
// import kotlinx.coroutines.test.UnconfinedTestDispatcher
// import kotlinx.coroutines.test.runTest
// import org.junit.Before
// import org.junit.Rule
// import org.junit.Test
// import java.math.BigDecimal
// import kotlin.test.assertEquals
// import kotlin.test.assertTrue

class CartViewModelTest {
//    @get:Rule
//    val mainDispatcherRule = MainDispatcherRule()
//
//    private lateinit var viewModel: CartViewModel
//
//    private val repository = TestHappyStoreRepository()
//
//    @Before
//    fun setUp() = runTest {
//        val appState = ApplicationState()
//        val store = Store(appState)
//        viewModel = CartViewModel(
//            ProductListReducer(store),
//            ProductInCartItemUpdater(),
//            ProductFavoriteUpdater(),
//            store
//        )
//
//        launch(UnconfinedTestDispatcher()) {
//            val initialProductList = repository.getProducts().first()
//            store.update { applicationState ->
//                applicationState.copy(products = initialProductList)
//            }
//        }
//    }
//
//    @Test
//    fun productsAreLoadedWhileViewModelInitiated() = runTest {
//        val collectJob =
//            launch(UnconfinedTestDispatcher()) { viewModel.store.state.map { it.products } }
//
//        viewModel.store.read { appState ->
//            assertEquals(
//                listOf(
//                    Product(
//                        123,
//                        "title1",
//                        BigDecimal("23.3"),
//                        "Jewerly",
//                        "description1",
//                        "http://example.png",
//                        Product.Rating(3.4f, 1000)
//                    ),
//                    Product(
//                        124,
//                        "title2",
//                        BigDecimal("24.4"),
//                        "Jewerly",
//                        "description2",
//                        "http://example.png",
//                        Product.Rating(4.4f, 2000)
//                    )
//                ), appState.products
//            )
//        }
//
//        collectJob.cancel()
//    }
//
//    @Test
//    fun productFavoriteUpdatesAfterUserFavoriteProduct() = runTest {
//        val collectJob = launch(UnconfinedTestDispatcher()) { viewModel.store.state }
//        viewModel.updateFavoriteIds(124)
//        viewModel.store.read { appState ->
//            assertTrue(appState.favoriteProductId.contains(124))
//        }
//        collectJob.cancel()
//    }
//
//    @Test
//    fun inCartProductUiStateIsInitiallyIsEmpty() {
//        assertEquals(viewModel.inCartProductsUiState.value, CartScreenUiState.Empty)
//    }
//
//    @Test
//    fun inCartProductUiStateLoaded() = runTest {
//        val collectJob =
//            launch(UnconfinedTestDispatcher()) { viewModel.inCartProductsUiState.collect() }
//        val inCartProducts = listOf(
//            UiProduct(
//                Product(
//                    123,
//                    "title1",
//                    BigDecimal("23.3"),
//                    "Jewerly",
//                    "description1",
//                    "http://example.png",
//                    Product.Rating(3.4f, 1000)
//                ), isFavorite = false, isExpended = false, isInCart = true, 1
//            )
//        )
//
//        viewModel.removeItem(123)
//
//        assertEquals(CartScreenUiState.Data(inCartProducts), viewModel.inCartProductsUiState.value)
//
//        collectJob.cancel()
//    }
//
//    @Test
//    fun productQuantityUpdatesAfterChangingQuantity() = runTest {
//        viewModel.onQuantityChanged(123, 3)
//
//        viewModel.store.read { appState ->
//            assertEquals(3, appState.inCartProductQuantity[123])
//        }
//    }
//
//    @Test
//    fun inCartProductsUpdateAfterAddingProductsToCart() = runTest {
//        viewModel.removeItem(124)
//        viewModel.store.read { appState ->
//            assertTrue(appState.inCartProductIds.contains(124))
//        }
//    }
//
//    @Test
//    fun productQuantityResetAfterRemovingFromCart() = runTest {
//        viewModel.removeItem(124)
//        viewModel.onQuantityChanged(124, 5)
//        viewModel.removeItem(124)
//
//        viewModel.store.read { appState ->
//            assertTrue(appState.inCartProductQuantity[124] == 1)
//        }
//    }
}
