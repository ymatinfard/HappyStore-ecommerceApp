package com.matin.happystore.core.domain

import com.matin.data.HappyStoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GeSearchSuggestionUseCase
@Inject
constructor(private val repository: HappyStoreRepository) {
    operator fun invoke(query: String): Flow<List<String>> = repository.getSearchSuggestion(query)
}
