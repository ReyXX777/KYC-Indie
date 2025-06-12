package com.tech.kyc.network

import com.google.gson.annotations.SerializedName
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

// ✅ Request Body
data class FoodRequest(
    val query: String
)

// ✅ API Response
data class ApiResponse(
    val foods: List<FoodItem>
)

// ✅ Food Item Model
data class FoodItem(
    @SerializedName("food_name") val foodName: String,
    @SerializedName("serving_qty") val servingQty: Float,
    @SerializedName("serving_unit") val servingUnit: String,
    @SerializedName("serving_weight_grams") val servingWeightGrams: Float,
    @SerializedName("nf_calories") val calories: Float,
    @SerializedName("nf_total_fat") val totalFat: Float,
    @SerializedName("nf_saturated_fat") val saturatedFat: Float,
    @SerializedName("nf_cholesterol") val cholesterol: Float,
    @SerializedName("nf_sodium") val sodium: Float,
    @SerializedName("nf_total_carbohydrate") val totalCarbohydrate: Float,
    @SerializedName("nf_dietary_fiber") val dietaryFiber: Float,
    @SerializedName("nf_sugars") val sugars: Float,
    @SerializedName("nf_protein") val protein: Float,
    @SerializedName("nf_potassium") val potassium: Float,
    @SerializedName("nf_p") val phosphorus: Float?,  // ✅ Added missing phosphorus field
    @SerializedName("photo") val photo: Photo?, // ✅ Nullable photo object
    @SerializedName("alt_measures") val altMeasures: List<AltMeasure>?, // ✅ Nullable altMeasures
    @SerializedName("metadata") val metadata: Metadata? // ✅ Nullable metadata
)

// ✅ Photo Model (Optional)
data class Photo(
    @SerializedName("thumb") val thumb: String?,
    @SerializedName("highres") val highres: String?,
    @SerializedName("is_user_uploaded") val isUserUploaded: Boolean?
)

// ✅ Alt Measure Model
data class AltMeasure(
    @SerializedName("serving_weight") val servingWeight: Float,
    @SerializedName("measure") val measure: String?,
    @SerializedName("seq") val seq: Int?,
    @SerializedName("qty") val qty: Int
)

// ✅ Metadata Model
data class Metadata(
    @SerializedName("is_raw_food") val isRawFood: Boolean
)

// ✅ API Service
interface ApiService {
    @Headers(
        "Content-Type: application/json",
        "x-app-id: b19d97ab",
        "x-app-key: 498ef25e1e7143db6f1c959819a3f316"
    )
    @POST("natural/nutrients") // ✅ Correct endpoint
    suspend fun getFoodData(@Body request: FoodRequest): ApiResponse
}
