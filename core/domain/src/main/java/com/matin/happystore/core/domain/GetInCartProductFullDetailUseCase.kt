package com.matin.happystore.core.domain

import com.matin.data.HappyStoreRepository
import com.matin.happystore.core.model.InCartProduct
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetInCartProductFullDetailUseCase
    @Inject
    constructor(private val repository: HappyStoreRepository) {
        suspend operator fun invoke(): Flow<List<InCartProduct>> = repository.getInCartProductsFullDetail()
    }
