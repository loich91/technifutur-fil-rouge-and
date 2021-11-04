package com.technipixl.filrouge.DBFood.Dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.technipixl.filrouge.DBFood.model.BusineseDb
import com.technipixl.filrouge.DBFood.model.BusinesswithCategory
import com.technipixl.filrouge.DBFood.model.CategoryDb

@Dao
interface FoodDao {
    @Query("Select * From BusineseDb WHERE choiceNumber = :choiceNumber")
    fun getFavoriteFood(choiceNumber:Int): LiveData<List<BusinesswithCategory>>
    @Query("Select * From BusineseDb")
    suspend fun getFavoriteFoodBusi(): List<BusineseDb>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDataFood(busineseDb: BusineseDb): Long
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDataCategory(categoryDb: CategoryDb):Long

    @Query("SELECT * FROM BusineseDb WHERE id LIKE :id LIMIT 1")
    fun getById(id: String): LiveData<BusineseDb>

    @Query("DELETE FROM businesedb WHERE id = :id")
    fun deleteById(id: String)
}