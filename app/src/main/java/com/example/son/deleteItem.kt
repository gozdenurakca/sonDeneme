package com.example.son

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

fun deleteItem(
    keyy: Int,
    title: String,
    ingredient: String,
    recipe: String,
    preparationTime: String,
    calories: String,
) {
    val baseUrl = "$base/delete_data"
    val json = buildString {
        append("{")
        append("\"keyy\":\"$keyy\"")
        append(", \"Title\":\"$title\"")
        append(", \"Ingredient\":\"$ingredient\"")
        append(", \"Recipe\":\"$recipe\"")
        append(", \"PreparationTime\":\"$preparationTime\"")
        append(", \"Calories\":\"$calories\"")
        append("}")
    }
    val requestBody = json.toRequestBody("application/json".toMediaType())
    val request = Request.Builder()
        .url(baseUrl)
        .post(requestBody)
        .build()

    val client = OkHttpClient()

    CoroutineScope(Dispatchers.IO).launch {
        try {
            val response = client.newCall(request).execute()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    println("Data deleted successfully")
                } else {
                    println("Failed to delete data :${response.message}")
                }
            }
        } catch (e: IOException) {
            withContext(Dispatchers.Main) {
                println("Error : ${e.message}")
            }
        }
    }
}