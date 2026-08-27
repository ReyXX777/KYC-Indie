package com.tech.kyc.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
        searchJob?.cancel()

        if (query.isBlank()) {
            foodList = emptyList()
            isEmptyState = true
            return
        }

        searchJob = viewModelScope.launch {
            isLoading = true
            errorMessage = null
            isEmptyState = false
            foodList = emptyList()

            try {
                delay(300)

                val response = RetrofitClient.apiService.getFoodData(FoodRequest(query))

                if (response.foods.isEmpty()) {
                    isEmptyState = true
                    errorMessage = "No results found for \"$query\""
                } else {
                    foodList = response.foods
                }
            } catch (e: SocketTimeoutException) {
                errorMessage = "Request timed out. Please try again."
            } catch (e: UnknownHostException) {
                errorMessage = "No internet connection. Check your network and try again."
            } catch (e: IOException) {
                errorMessage = "Network error: ${e.localizedMessage ?: e.message}"
            } catch (e: HttpException) {
                errorMessage = "Server error: ${e.localizedMessage ?: e.message}"
            } catch (e: Exception) {
                errorMessage = "Failed to load data: ${e.localizedMessage ?: e.message}"
            } finally {
                isLoading = false
            }
        }
    }
}
