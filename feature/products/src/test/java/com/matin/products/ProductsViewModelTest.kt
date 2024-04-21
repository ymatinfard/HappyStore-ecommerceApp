package com.matin.products

class ProductsViewModelTest {
//    @get:Rule
//    val mainDispatcherRule = MainDispatcherRule()
//
//    private lateinit var viewModel: ProductsViewModel
//    private val repository: HappyStoreRepository = TestHappyStoreRepository()
//
//    @Before
//    fun setUp() {
//        val appState = ApplicationState()
//        val store = Store(appState)
//        viewModel =
//            ProductsViewModel(
//                store,
//                GetProductsUseCase(repository),
//                ProductCategoryFilterGeneratorUseCase(),
//                ProductListReducer(store),
//                ProductFavoriteUpdater(),
//                ProductExpandUpdater(),
//                ProductFilterSelectionUpdater(),
//                ProductInCartItemUpdater(),
//                ProductListUiHelper(),
//            )
//    }
//
//    @Test
//    fun productsAreLoadedWhileViewModelInitiated() =
//        runTest {
//            val collectJob =
//                launch(UnconfinedTestDispatcher()) { viewModel.productsScreenUiState.state.map { it.products } }
//
//            viewModel.productsScreenUiState.read { appState ->
//                assertEquals(
//                    listOf(
//                        Product(
//                            123,
//                            "title1",
//                            BigDecimal("23.3"),
//                            "Jewerly",
//                            "description1",
//                            "http://example.png",
//                            Product.Rating(3.4f, 1000),
//                        ),
//                        Product(
//                            124,
//                            "title2",
//                            BigDecimal("24.4"),
//                            "Jewerly",
//                            "description2",
//                            "http://example.png",
//                            Product.Rating(4.4f, 2000),
//                        ),
//                    ),
//                    appState.products,
//                )
//            }
//
//            collectJob.cancel()
//        }
//
//    @Test
//    fun productFavoriteUpdatesAfterUserFavoriteProduct() =
//        runTest {
//            val collectJob = launch(UnconfinedTestDispatcher()) { viewModel.productsScreenUiState.state }
//            viewModel.updateFavoriteIds(124)
//            viewModel.productsScreenUiState.read { appState ->
//                assertTrue(appState.favoriteProductId.contains(124))
//            }
//            collectJob.cancel()
//        }
//
//    @Test
//    fun productDescriptionExpandUpdatesAfterUserExpandsItem() =
//        runTest {
//            val collectJob = launch(UnconfinedTestDispatcher()) { viewModel.productsScreenUiState.state }
//            viewModel.updateProductExpand(123)
//            assertTrue(viewModel.productsScreenUiState.state.value.expandedProductIds.contains(123))
//            collectJob.cancel()
//        }
//
//    @Test
//    fun productsUiStateIsInitiallyLoading() {
//        assertEquals(viewModel.productListUiState.value, ProductsScreenUiState.Loading)
//    }
//
//    @Test
//    fun productsUiStateLoaded() =
//        runTest {
//            val collectJob =
//                launch(UnconfinedTestDispatcher()) { viewModel.productListUiState.collect() }
//            val products =
//                listOf(
//                    Product(
//                        123,
//                        "title1",
//                        BigDecimal("23.3"),
//                        "Jewerly",
//                        "description1",
//                        "http://example.png",
//                        Product.Rating(3.4f, 1000),
//                    ),
//                    Product(
//                        124,
//                        "title2",
//                        BigDecimal("24.4"),
//                        "Jewerly",
//                        "description2",
//                        "http://example.png",
//                        Product.Rating(4.4f, 2000),
//                    ),
//                )
//            val uiProducts = products.map { UiProduct(it, false, false, false, 1) }
//            val filters =
//                listOf(UiFilter(filter = Filter(value = "Jewerly", displayText = "Jewerly (2)"), false))
//            val productsAndFilters = UiProductsAndFilters(uiProducts, filters)
//
//            assertEquals(
//                ProductsScreenUiState.Success(productsAndFilters),
//                viewModel.productListUiState.value,
//            )
//
//            collectJob.cancel()
//        }
//
//    @Test
//    fun inCartProductsUpdateAfterAddingProductsToCart() =
//        runTest {
//            viewModel.addToCart(124)
//            viewModel.productsScreenUiState.read { appState ->
//                assertTrue(appState.inCartProductIds.contains(124))
//            }
//        }
}
