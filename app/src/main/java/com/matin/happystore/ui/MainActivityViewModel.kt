package com.matin.happystore.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.matin.happystore.core.domain.GetInCartProductIdsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(private val getInCartProductIds: GetInCartProductIdsUseCase) :
    ViewModel() {

    val mainScreenUiState = MutableStateFlow(MainScreenUiState())

    init {
        getInCartProductItemsCount()
    }

    private fun getInCartProductItemsCount() {
        viewModelScope.launch {
            getInCartProductIds().collect { inCartItemsList ->
                mainScreenUiState.update { state -> state.copy(inCartProductsCount = inCartItemsList.size) }
            }
        }
    }

}

data class MainScreenUiState(
    val inCartProductsCount: Int = 0,
)