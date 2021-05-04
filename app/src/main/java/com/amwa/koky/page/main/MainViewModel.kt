package com.amwa.koky.page.main

import androidx.lifecycle.*
import com.amwa.koky.data.Resource
import com.amwa.koky.domain.interaction.RecipeUseCase
import com.amwa.koky.domain.model.Category
import com.amwa.koky.domain.model.Recipe
import com.amwa.koky.domain.model.SearchHistory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val recipeUseCase: RecipeUseCase
) : ViewModel() {

    enum class Source {
        CATEGORY, QUERY
    }

    private val _pageTitle = MutableLiveData<String>()
    val pageTitle: LiveData<String> = _pageTitle

    private val _categoryList = MediatorLiveData<Resource<List<Category>>>()

    val categoryList: LiveData<Resource<List<Category>>> = _categoryList

    private var categoryListSource: LiveData<Resource<List<Category>>>? = null

    private val _recipeList = MediatorLiveData<Resource<List<Recipe>>>()

    val recipeList: LiveData<Resource<List<Recipe>>> = _recipeList

    val favoriteList = recipeUseCase.getFavoriteRecipes().asLiveData()

    val historyList = recipeUseCase.getAllHistories().asLiveData()

    private val selectedCategory = MutableLiveData("new")

    private val searchQuery = MutableLiveData<String>()

    private val recipeListByCategory = selectedCategory.switchMap {
        if (it == "new") {
            recipeUseCase.getNewRecipes().asLiveData()
        } else {
            recipeUseCase.getRecipesByCategory(it).asLiveData()
        }
    }

    private val recipeListByQuery = searchQuery.switchMap {
        recipeUseCase.searchRecipe(it).asLiveData()
    }

    private var lastSource = Source.CATEGORY

    init {
        categoryListSource = recipeUseCase.getCategories().asLiveData()
        _categoryList.addSource(categoryListSource!!) {
            _categoryList.value = it
        }

        _recipeList.addSource(recipeListByCategory) {
            _recipeList.value = it
            lastSource = Source.CATEGORY
        }

        _recipeList.addSource(recipeListByQuery) {
            _recipeList.value = it
            lastSource = Source.QUERY
        }
    }

    fun setCategory(key: String) {
        selectedCategory.value = key
    }

    fun searchRecipe(query: String) {
        searchQuery.value = query
    }

    fun addHistory(searchHistory: SearchHistory) = viewModelScope.launch {
        recipeUseCase.saveHistory(searchHistory)
    }

    fun deleteHistory(searchHistory: SearchHistory) = viewModelScope.launch {
        recipeUseCase.deleteHistory(searchHistory)
    }

    fun deleteAllHistories() = viewModelScope.launch {
        recipeUseCase.deleteAllHistories()
    }

    fun refreshRecipeList() {
        if (lastSource == Source.CATEGORY) {
            selectedCategory.value = selectedCategory.value
        } else {
            searchQuery.value = searchQuery.value
        }
    }

    fun refreshCategoryList() {
        categoryListSource = recipeUseCase.getCategories().asLiveData()
        _categoryList.addSource(categoryListSource!!) {
            _categoryList.value = it
        }
    }

    fun removeCategoryListSource() {
        categoryListSource?.let { _categoryList.removeSource(it) }
    }

    fun setPageTitle(title: String) {
        _pageTitle.value = title
    }
}