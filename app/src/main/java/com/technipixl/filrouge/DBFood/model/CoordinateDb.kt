package com.technipixl.filrouge.DBFood.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class CoordinateDb (
    @PrimaryKey(autoGenerate = true)
    val idCoord :Int,
    val latitude: Double,
    val longitude: Double

        )