package com.food_project.shared_component.api.api_services

import com.food_project.feature.home.domain.data.model.MealResponse
import retrofit2.Response
import retrofit2.http.GET

interface RandomAPIService {
    @GET("random.php")
    suspend fun getRandomMeal(): Response<MealResponse>
}