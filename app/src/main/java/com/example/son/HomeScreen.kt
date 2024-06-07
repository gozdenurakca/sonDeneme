package com.example.son

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController

@Composable
fun HomeScreen(navController: NavHostController, recipeViewModel: RecipeViewModel) {
    val recipes by recipeViewModel.recipes.collectAsState()
    var items by remember { mutableStateOf<List<Item>>(emptyList()) }
    var searchValue = remember { mutableStateOf("") }



    LaunchedEffect(Unit) {
        fetchData { data ->
            val parsedItems = parseJsonResponse(data)
            items = parsedItems

        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.arka3),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)) {

            Text(
                text = "Hello GNA",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "What are you cooking today?",
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Gray)
                    .padding(8.dp)
            ) {
                TextField(
                    value = searchValue.value,
                    onValueChange = {newValue -> searchValue.value = newValue},
                    modifier = Modifier.weight(1f),
                    textStyle = TextStyle(color = Color.Black, fontSize = 16.sp)
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
            Spacer(modifier = Modifier.height(16.dp))

            val filteredItems = if (searchValue.value.isNotBlank()) {
                items.filter { item ->
                    item.title.startsWith(searchValue.value, ignoreCase = true)
                }
            } else {
                items
            }


            if(items.isNotEmpty()){
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(items) { item ->
                        RecipeCard( item , navController)
                    }
                }
            }

            FloatingActionButton(
                onClick = { navController.navigate("addRecipe") },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 16.dp),
                containerColor = Color.Gray
            ) {
                Text("Add New Recipe", color = Color.White)
            }
        }
    }
}


@Composable
fun RecipeCard(item: Item, navController: NavController){
    Column {
        Box {
            Column {
                Row {
                    Button(onClick = {
                        navController.navigate("RecipeDetailScreen/${item.title}"+
                                "/${item.ingredient}"+
                                "/${item.recipe}"+
                                "/${item.preparationTime}"+
                                "/${item.calories}")}) {
                        Row{
                            BasicText(text = item.title)
                        }
                    }
                }
            }
        }
    }
}