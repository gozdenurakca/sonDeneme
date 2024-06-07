package com.example.son

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class Recipe(
    val id: String,
    val title: String,
    val time: String,
    val ingredients: List<Ingredient> = emptyList(),
    val steps: List<String> = emptyList(),
    val calories: String
)

data class Ingredient(
    val name: String,
    val amount: String
)

class RecipeViewModel : ViewModel() {
    private val _recipes = MutableStateFlow<List<Recipe>>(emptyList())
    val recipes: StateFlow<List<Recipe>> get() = _recipes


    init {
        loadRecipes()
    }

    private fun loadRecipes() {
        viewModelScope.launch {
        }
    }
    /*
    fun getRecipeById(recipeId: Int): StateFlow<Recipe?> {
        return MutableStateFlow(_recipes.value.find { it.id == recipeId })
    }

     */

    fun updateRecipeTitle(recipeId: Int, newTitle: String) {

    }

    fun updateRecipeTime(recipeId: Int, newTime: String) {

    }

    fun updateRecipe(recipe: Recipe) {
        viewModelScope.launch {

        }
    }

    fun deleteRecipe(recipe: Recipe) {
        viewModelScope.launch {
            // Delete the recipe from the repository
            _recipes.value = _recipes.value.filter { it.id != recipe.id }
        }
    }

    fun addRecipe(recipe: Recipe) {
        viewModelScope.launch {
            _recipes.value += recipe
        }
    }

    fun getRecipeById(recipeId: Int) {

    }

    fun deleteRecipeByTitle(title: String) {

    }
}