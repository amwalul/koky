package com.amwa.koky.data.mapper

import com.amwa.koky.data.local.db.model.RecipeEntity
import com.amwa.koky.data.remote.api.model.recipe.RecipeDetailResultResponse
import com.amwa.koky.data.remote.api.model.recipe.RecipeListResultResponse
import com.amwa.koky.domain.model.Recipe

object RecipeListResponseMapper : DomainMapper<RecipeListResultResponse, Recipe> {
    override fun mapToDomain(type: RecipeListResultResponse) = with(type) {
        Recipe(key, title, thumb, portion, times, dificulty, null, null, null)
    }
}

object RecipeDetailResponseMapper : DomainMapper<RecipeDetailResultResponse, Recipe> {
    override fun mapToDomain(type: RecipeDetailResultResponse) = with(type) {
        Recipe(null, title, thumb, servings, times, dificulty, desc, ingredient, step)
    }
}

object RecipeEntityMapper : DomainMapper<RecipeEntity, Recipe> {
    override fun mapToDomain(type: RecipeEntity) = with(type) {
        Recipe(key, title, thumb, servings, times, dificulty, desc, ingredient, step)
    }
}

object RecipeDomainMapper : EntityMapper<Recipe, RecipeEntity> {
    override fun mapToEntity(type: Recipe) = with(type) {
        RecipeEntity(
            key ?: "",
            title,
            thumb,
            servings,
            times,
            dificulty,
            desc ?: "",
            ingredient ?: emptyList(),
            step ?: emptyList()
        )
    }
}