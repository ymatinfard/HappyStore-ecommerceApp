package com.matin.happystore.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.matin.happystore.ui.theme.HappyStoreTheme
import com.matin.happystore.widgets.MainProgressBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HappyStoreTheme {
                val viewModel = hiltViewModel<HappyStoreViewModel>()
                val products = viewModel.products.collectAsState()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    when (products.value) {
                        is ProductUIState.Success -> MainScreen((products.value as ProductUIState.Success).data)
                        is ProductUIState.Error -> Text(text = "Error" + (products.value as ProductUIState.Error).exception)
                        is ProductUIState.Loading -> MainProgressBar()
                    }
                }
            }
        }
    }
}