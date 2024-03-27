package com.matin.happystore.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.matin.happystore.ui.theme.HappyStoreTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HappyStoreTheme {
                // A surface container using the 'background' color from the theme
                val viewModel = hiltViewModel<HappyStoreViewModel>()
                val products = viewModel.products.collectAsState()
                (products.value as? ProductUIState.Success)?.data?.get(0)?.let { Log.d("Yousef", it.image) }
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    when (products.value) {
                        is ProductUIState.Success -> MainScreen((products.value as ProductUIState.Success).data)
                        is ProductUIState.Error -> Text(text = "Error" + (products.value as ProductUIState.Error).exception)
                        is ProductUIState.Loading -> CircularProgressIndicator()
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HappyStoreTheme {
        Greeting("Android")
    }
}