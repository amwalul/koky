package com.amwa.koky.page.detail

import androidx.lifecycle.*
import com.amwa.koky.domain.interaction.RecipeUseCase
import com.amwa.koky.domain.model.Recipe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val recipeUseCase: RecipeUseCase
) : ViewModel() {

    private val _favoriteRecipe = MutableLiveData<Recipe?>()
    val favoriteRecipe: LiveData<Recipe?> = _favoriteRecipe

    fun getFavoriteRecipeDetail(key: String) = viewModelScope.launch {
        _favoriteRecipe.postValue(recipeUseCase.getFavoriteRecipeDetail(key))
    }

    fun getRecipeDetail(key: String) = recipeUseCase.getRecipeDetail(key).asLiveData()

    fun saveRecipe(recipe: Recipe) = viewModelScope.launch {
        recipeUseCase.saveRecipe(recipe)
    }

    fun deleteRecipe(recipe: Recipe) = viewModelScope.launch {
        recipeUseCase.deleteRecipe(recipe)
    }
}