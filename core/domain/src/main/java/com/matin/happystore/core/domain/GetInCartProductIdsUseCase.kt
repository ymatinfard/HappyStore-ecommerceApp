package com.matin.happystore.core.domain

import com.matin.data.HappyStoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetInCartProductIdsUseCase
    @Inject
    constructor(private val repository: HappyStoreRepository) {
        suspend operator fun invoke(): Flow<List<Int>> {
            return repository.getInCartProductIds()
        }
    }
