package com.technipixl.filrouge.DBFood.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity

class BusineseDb(
    @PrimaryKey(autoGenerate = true)
    val idDb:Int,
    val id: String,
    val name: String?,
    val alias: String?,
    val display_phone: String?,
    val distance: Double?,
    val image_url: String?,
    val is_closed: Boolean?,
    val phone: String?,
    val price: String?,
    val rating: Double?,
    val review_count: Int?,
    val url: String?,
    val choiceNumber: Int?,

    @Embedded val location: LocationDb?,
    @Embedded val coordinates: CoordinateDb?
        )

