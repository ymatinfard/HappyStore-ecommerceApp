package com.matin.happystore.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.matin.happystore.core.domain.GeSearchSuggestionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val getSearchSuggestionUseCase: GeSearchSuggestionUseCase) :
    ViewModel() {
    private val _uiState = MutableStateFlow(SearchScreenUiState())
    val uiState = _uiState.asStateFlow()

    private fun onQueryChanged(query: String) {
        viewModelScope.launch {
            if (query.length > SEARCH_QUERY_MIN_LEN) {
                getSearchSuggestionUseCase(query).collectLatest { suggestion ->
                    _uiState.update { it.copy(searchSuggestion = suggestion) }
                }
            }
        }
    }

    private fun onSearchQuery(query: String) {

    }

    fun intentToAction(intent: SearchScreenIntent) {
        when (intent) {
            is SearchScreenIntent.QueryChanged -> onQueryChanged(intent.query)
            else -> {}
        }
    }
}

sealed interface SearchScreenIntent {
    data class QueryChanged(val query: String) : SearchScreenIntent
}

data class SearchScreenUiState(
    val searchSuggestion: List<String> = emptyList()
)

private const val SEARCH_QUERY_MIN_LEN = 3