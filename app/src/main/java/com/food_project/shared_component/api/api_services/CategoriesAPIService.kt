package com.food_project.shared_component.api.api_services

import com.food_project.feature.home.domain.data.model.CategoriesResponse
import retrofit2.Response
import retrofit2.http.GET

interface CategoriesAPIService {
    @GET("categories.php")
    suspend fun getCategories(): Response<CategoriesResponse>
}