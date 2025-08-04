package com.food_project.shared_component.api.api_services

import com.food_project.feature.home.domain.data.model.MealResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchAPIService {
    @GET("search.php")
    suspend fun listMealsByFirstLetter(@Query("f") letter: String): Response<MealResponse>

    @GET("search.php")
    suspend fun searchMeal(@Query("s") name: String): Response<MealResponse>
}