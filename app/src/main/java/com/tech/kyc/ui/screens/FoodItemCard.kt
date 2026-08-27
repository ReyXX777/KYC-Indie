package com.tech.kyc.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import com.tech.kyc.network.FoodItem

@Composable
fun FoodItemCard(
    food: FoodItem,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                navController.navigate("food_list_screen")
            },
        elevation = CardDefaults.elevatedCardElevation()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            val painter: Painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(food.photo?.thumb ?: android.R.drawable.ic_menu_report_image)
                    .crossfade(true)
                    .placeholder(android.R.drawable.ic_menu_report_image)
                    .error(android.R.drawable.ic_menu_report_image)
                    .build()
            )

            Image(
                painter = painter,
                contentDescription = food.foodName,
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = food.foodName,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 4.dp)
            )

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
