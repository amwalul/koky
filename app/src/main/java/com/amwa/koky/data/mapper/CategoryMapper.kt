package com.amwa.koky.data.mapper

import com.amwa.koky.data.remote.api.model.category.CategoryResultResponse
import com.amwa.koky.domain.model.Category

object CategoryResponseMapper : DomainMapper<CategoryResultResponse, Category> {
    override fun mapToDomain(type: CategoryResultResponse) = with(type) {
        Category(key, category, url)
    }
}