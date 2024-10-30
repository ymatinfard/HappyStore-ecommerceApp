package com.matin.happystore.core.domain

import com.matin.data.HappyStoreRepository
import com.matin.happystore.core.model.Product
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSingleProductUseCase
@Inject
constructor(private val repository: HappyStoreRepository) {
    operator fun invoke(id: Int): Flow<Product> = repository.getSingleProduct(id)

}
