package com.tech.kyc.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.gson.Gson
import com.tech.kyc.network.FoodItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(foodJson: String, navController: NavController) {
    val food = Gson().fromJson(foodJson, FoodItem::class.java) // ✅ Deserialize JSON

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Food Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // ✅ Food Name
            Text(
                text = food.foodName,
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            // ✅ Nutritional Information
            Text(
                text = "Calories: ${food.calories} kcal",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "Protein: ${food.protein} g",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "Fat: ${food.totalFat} g",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "Carbs: ${food.totalCarbohydrate} g",
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Serving Size: ${food.servingQty} ${food.servingUnit}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
