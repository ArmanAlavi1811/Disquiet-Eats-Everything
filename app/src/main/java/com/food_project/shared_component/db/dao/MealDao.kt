package com.food_project.shared_component.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.food_project.shared_component.db.Constants
import com.food_project.feature.home.domain.data.model.FavoriteMealEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MealDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favoriteMeal: FavoriteMealEntity)

    @Delete
    suspend fun deleteFavorite(favoriteMeal: FavoriteMealEntity)

    @Query("SELECT * FROM ${Constants.FAVORITE_MEALS_TABLE_NAME} WHERE ${Constants.MEAL_ID_COLUMN} = :mealId")
    fun getFavoriteById(mealId: String): Flow<FavoriteMealEntity?>

    @Query("SELECT * FROM ${Constants.FAVORITE_MEALS_TABLE_NAME}")
    fun getAllFavorites(): Flow<List<FavoriteMealEntity>>
}