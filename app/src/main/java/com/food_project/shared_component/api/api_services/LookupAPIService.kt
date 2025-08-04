package com.food_project.shared_component.api.api_services

import com.food_project.feature.home.domain.data.model.MealResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LookupAPIService {
    @GET("lookup.php")
    suspend fun getMealById(@Query("i") id: String): Response<MealResponse>
}