package com.matin.happystore.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.matin.happystore.core.domain.GeSearchSuggestionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(private val getSearchSuggestionUseCase: GeSearchSuggestionUseCase) :
    ViewModel() {
    private val _uiState = MutableStateFlow(SearchScreenUiState())
    val uiState = _uiState.asStateFlow()

    private val queryStateFlow = MutableStateFlow("")

    init {
        observeQuery()
    }

    private fun observeQuery() {
        viewModelScope.launch {
            queryStateFlow.debounce(DEBOUNCE_TIME).collectLatest { q ->
                if (q.isEmpty() || q.length < SEARCH_QUERY_MIN_LEN) {
                    _uiState.update { it.copy(searchSuggestion = emptyList()) }
                    return@collectLatest
                }
                getSearchSuggestionUseCase(q).collectLatest { suggestion ->
                    _uiState.update { it.copy(searchSuggestion = suggestion) }
                }
            }
        }
    }

    private fun onQueryChanged(query: String) {
        queryStateFlow.value = query
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
private const val DEBOUNCE_TIME = 700L