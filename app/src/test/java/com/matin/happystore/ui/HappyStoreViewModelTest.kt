package com.matin.happystore.ui


import com.matin.happystore.MainDispatcherRule
import com.matin.happystore.TestHappyStoreRepository
import com.matin.happystore.domain.HappyStoreRepository
import com.matin.happystore.ui.viewmodel.HappyStoreViewModel
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class HappyStoreViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: HappyStoreViewModel
    private val repository: HappyStoreRepository = TestHappyStoreRepository()

    @Before
    fun setUp() {
    }

    @Test
    fun productsAreShown() = runTest {
    }
}