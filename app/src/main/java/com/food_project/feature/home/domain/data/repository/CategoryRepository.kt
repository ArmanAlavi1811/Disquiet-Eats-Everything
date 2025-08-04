package com.food_project.feature.home.domain.data.repository

import com.food_project.feature.home.domain.data.model.CategoriesResponse
import com.food_project.shared_component.api.api_services.CategoriesAPIService

class CategoryRepository(private val categoriesApi: CategoriesAPIService) {

    suspend fun getCategories(): Result<CategoriesResponse> {
        return try {
            val response = categoriesApi.getCategories()
            if (response.isSuccessful) {
                response.body()?.let { Result.success(it) } ?: Result.failure(Throwable("Response body is null"))
            } else {
                Result.failure(Throwable("API Error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}