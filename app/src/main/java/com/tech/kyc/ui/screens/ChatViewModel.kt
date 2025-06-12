package com.tech.kyc.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {

    private val model = GenerativeModel(
        modelName = "gemini-1.5-flash-002",
        apiKey = "AIzaSyASrSfvDLvDldP_1_wuCwD6yQnYiA3tPAg"
    )

    private val _chatResponse = MutableStateFlow<String?>(null)
    val chatResponse: StateFlow<String?> = _chatResponse

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun fetchChatResponse(query: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _errorMessage.value = null

                val response = model.generateContent(query)
                _chatResponse.value = response.text

            } catch (e: Exception) {
                _errorMessage.value = "Failed to fetch response: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
