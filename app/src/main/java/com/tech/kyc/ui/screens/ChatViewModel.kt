package com.tech.kyc.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {

    private val model = GenerativeModel(
        modelName = "gemini-1.5-flash-002",
        apiKey = "" // Set your Gemini API key here or load from BuildConfig
    )

    private val _chatResponse = MutableStateFlow<String?>(null)
    val chatResponse: StateFlow<String?> = _chatResponse.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    fun fetchChatResponse(query: String) {
        if (query.isBlank()) return

        viewModelScope.launch {
            try {
                _isLoading.value = true
                _errorMessage.value = null

                val response = model.generateContent(query)
                _chatResponse.value = response.text
            } catch (e: Exception) {
                _errorMessage.value = "Failed to fetch response: ${e.localizedMessage ?: e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
