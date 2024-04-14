package com.matin.happystore.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.matin.data.util.NetworkMonitor
import com.matin.happystore.core.designsystem.theme.HappyStoreTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var networkMonitor: NetworkMonitor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val appState = rememberHappyStoreAppState(networkMonitor = networkMonitor)
            HappyStoreTheme {
                HappyStoreApp(appState)
            }
        }
    }
}