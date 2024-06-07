package com.example.son

import androidx.navigation.NavController
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

class UpdateRecipeScreen(
    private val navController: NavController,
    private val recipeViewModel: RecipeViewModel,
    private val title: String
) {
    private val client = OkHttpClient()

    @OptIn(DelicateCoroutinesApi::class)
    fun modifyItem(
        ingredient: String?,
        recipe: String?,
        preparationTime: String?,
        calories: String?,
    ) {
        val baseUrl = "${base}/modify_data"
        val json = buildString {
            append("{")
            append("\"Title\":\"$title\"")
            ingredient?.let { append(", \"Ingredient\":\"$it\"") }
            recipe?.let { append(", \"Recipe\":\"$it\"") }
            preparationTime?.let { append(", \"PreparationTime\":\"$it\"") }
            calories?.let { append(", \"Calories\":\"$it\"") }
            append("}")
        }
        val requestBody = json.toRequestBody("application/json".toMediaType())
        val request = Request.Builder()
            .url(baseUrl)
            .post(requestBody)
            .build()

        GlobalScope.launch(Dispatchers.IO) {
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    // Handle error
                }
            }
        }
    }
}