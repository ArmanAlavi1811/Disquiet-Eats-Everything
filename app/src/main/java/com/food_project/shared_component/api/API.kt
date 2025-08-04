package com.food_project.shared_component.api
import com.food_project.shared_component.api.api_services.CategoriesAPIService
import com.food_project.shared_component.api.api_services.FilterAPIService
import com.food_project.shared_component.api.api_services.LookupAPIService
import com.food_project.shared_component.api.api_services.RandomAPIService
import com.food_project.shared_component.api.api_services.SearchAPIService
import com.google.gson.Gson
import com.google.gson.GsonBuilder

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class API {

    companion object {
        // Unchanged properties
        private val myAppClient: OkHttpClient
            get() = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .writeTimeout(Constants.connectionTime, TimeUnit.SECONDS)
                .readTimeout(Constants.connectionTime, TimeUnit.SECONDS)
                .connectTimeout(Constants.connectionTime, TimeUnit.SECONDS)
                .build()

        private val gson: Gson get() = GsonBuilder().setLenient().create()

        private val interceptor: HttpLoggingInterceptor
            get() = HttpLoggingInterceptor()
                .apply { level = HttpLoggingInterceptor.Level.BODY }

        private object Constants {
            const val baseURL = "https://www.themealdb.com/api/json/v1/1/"
            const val connectionTime: Long = 60
        }

        // A single Retrofit instance that all services will share
        private val retrofit: Retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(Constants.baseURL)
                .client(myAppClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }

        // ✨ Create a lazy service for each route interface
        val searchApiService: SearchAPIService by lazy {
            retrofit.create(SearchAPIService::class.java)
        }

        val filterApiService: FilterAPIService by lazy {
            retrofit.create(FilterAPIService::class.java)
        }

        val lookupApiService: LookupAPIService by lazy {
            retrofit.create(LookupAPIService::class.java)
        }

        val randomApiService: RandomAPIService by lazy {
            retrofit.create(RandomAPIService::class.java)
        }

        val categoriesApiService: CategoriesAPIService by lazy {
            retrofit.create(CategoriesAPIService::class.java)
        }
    }
}