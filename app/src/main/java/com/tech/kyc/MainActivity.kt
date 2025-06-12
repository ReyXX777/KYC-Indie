package com.tech.kyc

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.tech.kyc.network.FoodItem
import com.tech.kyc.ui.screens.ChatViewModel
import com.tech.kyc.ui.screens.DetailsScreen
import com.tech.kyc.ui.screens.FoodItemCard
import com.tech.kyc.ui.screens.MainScreen
import com.tech.kyc.ui.screens.MainViewModel
import com.tech.kyc.ui.theme.KYCTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KYCTheme {
                val navController = rememberNavController()
                val viewModel: MainViewModel = viewModel()
                val chatViewModel = viewModel<ChatViewModel>()


                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "main_screen",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        // ✅ Fix: Pass `navController` correctly
                        composable(route = "main_screen") {
                            MainScreen(
                                viewModel = viewModel,
                                chatViewModel = chatViewModel,
                                navController = navController
                            )
                        }

                        // ✅ Fix: Correctly deserialize food data in details screen
                        composable("details_screen/{foodJson}") { backStackEntry ->
                            val foodJson = backStackEntry.arguments?.getString("foodJson") ?: ""
                            DetailsScreen(foodJson = foodJson, navController = navController)
                        }

                        composable("food_list_screen") {
                            val viewModel: MainViewModel = viewModel()

                            LazyColumn {
                                items(viewModel.foodList.size) { index ->
                                    FoodItemCard(
                                        food = viewModel.foodList[index],
                                        navController = navController // ✅ Pass navController directly
                                    )
                                }
                            }
                        }


                    }
                }
            }
        }
    }
}

