package com.matin.happystore.ui


import com.matin.happystore.MainDispatcherRule
import com.matin.happystore.TestHappyStoreRepository
import com.matin.happystore.domain.HappyStoreRepository
import com.matin.happystore.domain.model.Product
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.math.BigDecimal
import kotlin.test.assertEquals


class HappyStoreViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: HappyStoreViewModel
    private val repository: HappyStoreRepository = TestHappyStoreRepository()

    @Before
    fun setUp() {
        viewModel = HappyStoreViewModel(repository)
    }

    @Test
    fun productsAreShown() = runTest {
        val collectJob = launch(UnconfinedTestDispatcher()) { viewModel.products.collect() }
        assertEquals(
            ProductsUIState.Success(
                listOf(
                    Product(
                        123,
                        "title",
                        BigDecimal.ZERO,
                        "categoryTest",
                        "descriptionTest",
                        "http://image.test.jpg"
                    )
                )
            ), viewModel.products.value
        )
        collectJob.cancel()
    }
}