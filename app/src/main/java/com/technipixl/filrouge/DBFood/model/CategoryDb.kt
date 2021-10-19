package com.technipixl.filrouge.DBFood.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class CategoryDb (
    @PrimaryKey(autoGenerate = true)
    val idCat :Int,
    val alias: String,
    val title: String
)