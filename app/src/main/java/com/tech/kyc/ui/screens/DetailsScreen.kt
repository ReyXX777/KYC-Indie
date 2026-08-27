package com.tech.kyc.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
    val food = remember(foodJson) {
        runCatching { Gson().fromJson(foodJson, FoodItem::class.java) }.getOrNull()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Food Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
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
            if (food != null) {
                Text(
                    text = food.foodName,
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

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
            } else {
                Text(
                    text = "Food details could not be loaded.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}
