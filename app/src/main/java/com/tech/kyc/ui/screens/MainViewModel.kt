package com.tech.kyc.ui.screens

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tech.kyc.network.FoodItem
import com.tech.kyc.network.FoodRequest
import com.tech.kyc.network.RetrofitClient
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class MainViewModel : ViewModel() {

    var foodList by mutableStateOf<List<FoodItem>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var isEmptyState by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    private var searchJob: Job? = null

    fun fetchFoodData(query: String) {
        // ✅ Cancel the previous search to avoid race conditions
        searchJob?.cancel()

        searchJob = viewModelScope.launch {
            isLoading = true
            errorMessage = null
            isEmptyState = false
            foodList = emptyList() // ✅ Reset list to avoid showing stale data

            try {
                // ✅ Debouncing to avoid rapid API calls
                delay(300)

                val response = RetrofitClient.apiService.getFoodData(FoodRequest(query))

                if (response.foods.isEmpty()) {
                    isEmptyState = true
                    errorMessage = "No results found for \"$query\""
                } else {
                    foodList = response.foods.map { food ->
                        food.copy(photo = food.photo) // ✅ Ensure photo is mapped correctly
                    } // ✅ FIXED: Added missing closing bracket here
                }
            } catch (e: SocketTimeoutException) {
                errorMessage = "Request timed out. Please try again."
            } catch (e: UnknownHostException) {
                errorMessage = "No internet connection. Check your network and try again."
            } catch (e: IOException) {
                errorMessage = "Network error: ${e.message}"
            } catch (e: HttpException) {
                errorMessage = "Server error: ${e.message}"
            } catch (e: Exception) {
                errorMessage = "Failed to load data: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }
}
