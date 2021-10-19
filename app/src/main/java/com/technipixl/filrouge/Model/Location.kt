package com.technipixl.filrouge.Model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Location(
    val address1: String,
    val address2: String,
    val address3: String,
    val city: String,
    val country: String,
    val state: String,
    val zip_code: String
) : Parcelable