package com.matin.happystore.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun HomeScreen() {
    Scaffold(modifier = Modifier.fillMaxSize(),
    ) {
        Surface(modifier = Modifier.padding(it)) {
            Text(text = "Home screen")
        }
    }
}