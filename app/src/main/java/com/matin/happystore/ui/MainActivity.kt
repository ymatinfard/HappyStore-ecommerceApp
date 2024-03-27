package com.matin.happystore.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.matin.happystore.ui.theme.HappyStoreTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HappyStoreTheme {
                val viewModel = hiltViewModel<HappyStoreViewModel>()
                val products = combine(
                    viewModel.store.state.map { it.products },
                    viewModel.store.state.map { it.favoriteProductId }) { products, favorites ->
                    products.map { UiProduct(it, favorites.contains(it.id)) }

                }.collectAsState(initial = emptyList())
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(products = products.value) { favId ->
                        viewModel.updateFavoriteIds(favId)
                    }
                }
            }
        }
    }
}