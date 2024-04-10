package com.matin.happystore.core.domain

import com.matin.happystore.core.model.Filter
import com.matin.happystore.core.model.Product

import javax.inject.Inject

class ProductCategoryFilterGeneratorUseCase @Inject constructor() {
    operator fun invoke(products: List<Product>): Set<Filter> {
        return products.groupBy { it.category }.map { entry ->
            Filter(entry.key, "${entry.key} (${entry.value.size})")
        }.toSet()
    }
}