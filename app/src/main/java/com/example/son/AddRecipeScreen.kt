package com.example.son


import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch



@Composable
fun AddRecipeScreen(navController: NavHostController, recipeViewModel: RecipeViewModel) {
    val titleState = remember { mutableStateOf("") }
    val timeState = remember { mutableStateOf("") }
    val ingredientsState = remember { mutableStateOf("") }
    val stepsState = remember { mutableStateOf("") }
    val caloriesState = remember { mutableStateOf(" ") }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()


    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.arka2),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Add New Recipe",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            BasicTextField(
                value = titleState.value,
                onValueChange = { titleState.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray)
                    .padding(8.dp),
                singleLine = true,
                decorationBox = { innerTextField ->
                    Box(modifier = Modifier.padding(8.dp)) {
                        if (titleState.value.isEmpty()) {
                            Text(text = "Enter Food Name", color = Color.Black)
                        }
                        innerTextField()
                    }
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            BasicTextField(
                value = ingredientsState.value,
                onValueChange = { ingredientsState.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray)
                    .padding(8.dp),
                decorationBox = { innerTextField ->
                    Box(modifier = Modifier.padding(8.dp)) {
                        if (ingredientsState.value.isEmpty()) {
                            Text(text = "Enter Ingredients (e.g., Flour:200g, Sugar:100g)", color = Color.Black)
                        }
                        innerTextField()
                    }
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            BasicTextField(
                value = stepsState.value,
                onValueChange = { stepsState.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray)
                    .padding(8.dp),
                decorationBox = { innerTextField ->
                    Box(modifier = Modifier.padding(8.dp)) {
                        if (stepsState.value.isEmpty()) {
                            Text(text = "Enter Recipe Steps (e.g., Mix ingredients, Bake for 20 minutes)", color = Color.Black)
                        }
                        innerTextField()
                    }
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            BasicTextField(
                value = timeState.value,
                onValueChange = { timeState.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray)
                    .padding(8.dp),
                singleLine = true,
                decorationBox = { innerTextField ->
                    Box(modifier = Modifier.padding(8.dp)) {
                        if (timeState.value.isEmpty()) {
                            Text(text = "Enter Preparation Time", color = Color.Black)
                        }
                        innerTextField()
                    }
                }
            )
            Spacer(modifier = Modifier.height(8.dp))

            BasicTextField(
                value = caloriesState.value,
                onValueChange = { caloriesState.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray)
                    .padding(8.dp),
                singleLine = true,
                decorationBox = { innerTextField ->
                    Box(modifier = Modifier.padding(8.dp)) {
                        if (caloriesState.value.isEmpty()) {
                            Text(text = "Enter Calories", color = Color.Black)
                        }
                        innerTextField()
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row {
                Button(onClick = {
                    val newRecipe = Recipe(
                        id = (recipeViewModel.recipes.value.size + 1).toString(),
                        title = titleState.value,
                        time = timeState.value,
                        ingredients = parseIngredients(ingredientsState.value),
                        steps = parseSteps(stepsState.value),
                        calories = caloriesState.value
                    )


                    coroutineScope.launch {
                        recipeViewModel.addRecipe(newRecipe)
                        Toast.makeText(context, "Your recipe has been saved.", Toast.LENGTH_SHORT).show()
                        navController.popBackStack()
                    }
                }) {
                    Text(text = "Save Recipe")
                }

                Spacer(modifier = Modifier.width(16.dp))

                Button(onClick = {
                    navController.popBackStack()
                }) {
                    Text(text = "Back")
                }
            }
        }
    }
}
fun parseIngredients(input: String): List<Ingredient> {
    // Example parsing logic, assuming each ingredient is in "name:amount" format
    return input.split(",").map {
        val parts = it.split(":")
        Ingredient(name = parts[0].trim(), amount = parts.getOrElse(1) { "amount" }.trim())
    }
}

fun parseSteps(input: String): List<String> {
    // Parse the steps input string into a list of steps
    return input.split(",").map { it.trim() }
}