package com.tech.kyc.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.google.gson.Gson
import com.tech.kyc.network.FoodItem

@Composable
fun FoodItemCard(food: FoodItem, navController: NavController,modifier: Modifier = Modifier) {
    val foodJson = Gson().toJson(food)
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { navController.navigate("food_list_screen") // ✅ Navigate with JSON
            },
        elevation = CardDefaults.elevatedCardElevation()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            // ✅ Load actual image or fallback to placeholder
            val painter: Painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(food.photo?.thumb ?: android.R.drawable.ic_menu_report_image) // ✅ Load actual image if available
                    .crossfade(true) // ✅ Smooth transition effect
                    .placeholder(android.R.drawable.ic_menu_report_image) // ✅ Placeholder while loading
                    .error(android.R.drawable.ic_menu_report_image) // ✅ Fallback if loading fails
                    .build()
            )

            Image(
                painter = painter,
                contentDescription = "Food Image",
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(8.dp))

            // ✅ Food name
            Text(
                text = food.foodName,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 4.dp)
            )

            // ✅ Nutritional Information
            Text(
                text = "Calories: ${food.calories} kcal",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Protein: ${food.protein} g",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Fat: ${food.totalFat} g",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Carbs: ${food.totalCarbohydrate} g",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
