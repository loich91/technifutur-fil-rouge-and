package com.technipixl.filrouge.DBFood.Dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.technipixl.filrouge.DBFood.model.BusineseDb

@Dao
interface FoodDao {
    @Query("Select * From BusineseDb")
    fun getFavoriteFood():LiveData<List<BusineseDb>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDataFood(busineseDb: BusineseDb):Long
}