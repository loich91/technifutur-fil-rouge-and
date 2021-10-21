package com.technipixl.filrouge.DBFood.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.technipixl.filrouge.DBFood.Dao.FoodDao
import com.technipixl.filrouge.DBFood.model.BusineseDb
import com.technipixl.filrouge.DBFood.model.BusinesseCategoryDb
import com.technipixl.filrouge.DBFood.model.CategoryDb
import com.technipixl.filrouge.DBFood.model.CoordinateDb


@Database(entities = [BusineseDb::class, CategoryDb::class, BusinesseCategoryDb::class],version = 1)
abstract class DatabaseFood:RoomDatabase() {
    abstract fun foodDao():FoodDao
    companion object {
        @Volatile
        private var sharedInstance: DatabaseFood? = null
        fun getDb(context: Context): DatabaseFood {
            if (sharedInstance != null) return sharedInstance!!
            synchronized(this) {
                sharedInstance = Room
                    .databaseBuilder(context, DatabaseFood::class.java, "food.db")
                    .fallbackToDestructiveMigration()
                    .build()
                return sharedInstance!!
            }
        }
    }
}