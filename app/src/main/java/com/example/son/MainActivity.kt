package com.example.son

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels

import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.son.ui.theme.SonTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SonTheme {
                val navController = rememberNavController()
                val recipeViewModel: RecipeViewModel by viewModels()
                NavHost(navController = navController, startDestination = "login") {
                    composable("login") { LoginScreen(navController) }
                    composable("home") {
                        HomeScreen(
                            navController = navController,
                            recipeViewModel = recipeViewModel
                        )
                    }

                    composable("addRecipe") {
                        AddRecipeScreen(navController, recipeViewModel)
                    }

                    composable(
                        route = "RecipeDetailScreen/{title}/{ingredients}/{recipe}/{preparationTime}/{calories}",
                        arguments = listOf(
                            navArgument("Title") { type = NavType.StringType },
                            navArgument("Ingredient") { type = NavType.StringType },
                            navArgument("Recipe") { type = NavType.StringType },
                            navArgument("PreparationTime") { type = NavType.StringType },
                            navArgument("Calories") { type = NavType.StringType }
                        )
                    ) { backStackEntry ->
                        RecipeDetailScreen(
                            navController = navController,
                            title = backStackEntry.arguments?.getString("Title") ?: "",
                            ingredient = backStackEntry.arguments?.getString("Ingredient") ?: "",
                            recipe = backStackEntry.arguments?.getString("Recipe") ?: "",
                            preparationTime = backStackEntry.arguments?.getString("PreparationTime") ?: "",
                            calories = backStackEntry.arguments?.getString("Calories") ?: "",
                            onDelete = {
                                // Tarifi silme işlemi
                                val title = backStackEntry.arguments?.getString("title") ?: ""
                                recipeViewModel.deleteRecipeByTitle(title)
                                navController.popBackStack()
                            },
                            onUpdate = {
                                // Güncelleme ekranına geçiş
                                val title = backStackEntry.arguments?.getString("title") ?: ""
                                navController.navigate("updateRecipe/$title")
                            }
                        )
                    }
                    composable(
                        route = "updateRecipe/{title}",
                        arguments = listOf(
                            navArgument("title") { type = NavType.StringType }
                        )
                    ) { backStackEntry ->
                        val title = backStackEntry.arguments?.getString("title") ?: ""
                        UpdateRecipeScreen(navController, recipeViewModel, title)
                    }
                }
            }
        }
    }
}