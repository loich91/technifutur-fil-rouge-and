package com.technipixl.filrouge.Model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Coordinates(
    val latitude: Double,
    val longitude: Double
) : Parcelable