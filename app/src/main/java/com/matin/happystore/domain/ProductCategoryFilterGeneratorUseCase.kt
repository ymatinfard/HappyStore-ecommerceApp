package com.matin.happystore.domain

import com.matin.happystore.domain.model.Filter
import com.matin.happystore.domain.model.Product
import javax.inject.Inject

class ProductCategoryFilterGeneratorUseCase @Inject constructor() {
    operator fun invoke(products: List<Product>): Set<Filter> {
        return products.groupBy { it.category }.map { entry ->
            Filter(entry.key, "${entry.key} (${entry.value.size})")
        }.toSet()
    }
}