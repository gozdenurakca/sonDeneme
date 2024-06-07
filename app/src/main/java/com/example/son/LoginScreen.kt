package com.example.son

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

var base = "http://192.168.1.114:5555"

@Composable
fun LoginScreen(navController: NavHostController) {
    val usernameState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.arka2),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Hello, Welcome Back!",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            BasicTextField(
                value = usernameState.value,
                onValueChange = { usernameState.value = it },
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .background(Color.Gray)
                    .padding(16.dp),
                textStyle = TextStyle(color = Color.Black, fontSize = 18.sp)
            ) {
                if (usernameState.value.isEmpty()) {
                    Text(text = "Enter Username", color = Color.Black)
                }
            }

            BasicTextField(
                value = passwordState.value,
                onValueChange = { passwordState.value = it },
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .background(Color.Gray)
                    .padding(16.dp),
                textStyle = TextStyle(color = Color.Black, fontSize = 18.sp)
            ) {
                if (passwordState.value.isEmpty()) {
                    Text(text = "Enter Password", color = Color.Black)
                }
            }

            Button(
                onClick = { navController.navigate("home") },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red
                )
            ) {
                Text(text = "Log In")
            }
        }
    }
}

data class Item(
    val title: String,
    val ingredient: String,
    val recipe: String,
    val preparationTime: String,
    val calories: String,
)

@OptIn(DelicateCoroutinesApi::class)
fun fetchData(callback: (String) -> Unit) {
    val baseUrl = "$base/read_data"
    val client = OkHttpClient()
    val request = Request.Builder()
        .url(baseUrl)
        .build()

    GlobalScope.launch(Dispatchers.IO) {
        try {
            val response = client.newCall(request).execute()
            val responseData = response.body?.string() ?: ""
            withContext(Dispatchers.Main) {
                callback(responseData)
            }
        } catch (e: IOException) {
            e.printStackTrace()
            withContext(Dispatchers.Main) {
                callback("")
            }
        }
    }
}

fun parseJsonResponse(jsonString: String): List<Item> {
    val items = mutableListOf<Item>()

    try {
        val jsonObject = JSONObject(jsonString)
        val dataArray = jsonObject.getJSONArray("data")
        for (i in 0 until dataArray.length()) {
            val itemObject = dataArray.getJSONObject(i)
            val title = itemObject.getString("Title")
            val ingredient = itemObject.getString("Ingredient")
            val recipe = itemObject.getString("Recipe")
            val preparationTime = itemObject.getString("PreparationTime")
            val calories = itemObject.getString("Calories")

            val item = Item(title, ingredient, recipe, preparationTime, calories)
            items.add(item)
        }
    } catch (e: JSONException) {
        e.printStackTrace()
    }
    return items
}