package com.technipixl.filrouge.DBFood.Dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.technipixl.filrouge.DBFood.model.BusineseDb
import com.technipixl.filrouge.DBFood.model.BusinesswithCategory

@Dao
interface FoodDao {
    @Query("Select * From BusineseDb")
    fun getFavoriteFood(): LiveData<List<BusinesswithCategory>>
    @Query("Select * From BusineseDb")
    suspend fun getFavoriteFoodBusi(): List<BusineseDb>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDataFood(busineseDb: BusineseDb): Long

    @Query("SELECT * FROM BusineseDb WHERE id LIKE :id LIMIT 1")
    fun getById(id: String): LiveData<BusineseDb>

    @Query("DELETE FROM businesedb WHERE id = :id")
    fun deleteById(id: String)
}