package com.technipixl.filrouge.DBFood.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
class LocationDb (
    @PrimaryKey(autoGenerate = true)
    val idLoc :Int,
    val address1: String,
    val address2: String,
    val address3: String,
    val city: String,
    val country: String,
    val state: String,
    val zip_code: String
        )