package com.technipixl.filrouge

import com.technipixl.filrouge.DBFood.model.BusineseDb
import com.technipixl.filrouge.DBFood.model.BusinesswithCategory
import com.technipixl.filrouge.DBFood.model.CoordinateDb
import com.technipixl.filrouge.Model.Businesse
import com.technipixl.filrouge.Model.Category
import com.technipixl.filrouge.Model.Coordinates
import com.technipixl.filrouge.Model.Location

open class BusinesseMapper  {
    fun transfortoBusinesse (buisenessDb:BusinesswithCategory) : Businesse {
        val category = mutableListOf<Category>()
            buisenessDb.category.forEach {
                category.add(Category(it.alias,it.title))
            }


        return Businesse(
            id = buisenessDb.businesse.id,
            alias = buisenessDb.businesse.alias ?: "",
            display_phone = buisenessDb.businesse.display_phone ?: "",
            image_url = buisenessDb.businesse.image_url?:"",
            location =  Location(
                address1 = buisenessDb.businesse.location!!.address1 ?: "",
                address2 = buisenessDb.businesse.location.address2 ?: "",
                address3 = buisenessDb.businesse.location.address3 ?: "",
                city = buisenessDb.businesse.location.city ?: "",
                country = buisenessDb.businesse.location.country?: "",
                state = buisenessDb.businesse.location.state ?: "",
                zip_code = buisenessDb.businesse.location.zip_code ?: "",
            ),
            name = buisenessDb.businesse.name ?: "",
            phone = buisenessDb.businesse.phone ?: "",
            price = buisenessDb.businesse.price ?: "",
            rating = buisenessDb.businesse.rating!!,
            review_count = buisenessDb.businesse.review_count!!,
            url = buisenessDb.businesse.url ?: "",
            coordinates = Coordinates(
                latitude = buisenessDb.businesse.coordinates?.latitude!!,
                longitude = buisenessDb.businesse.coordinates?.longitude!!
            ),
            distance = 0.0,
            is_closed = false,
            categories = category

        )




    }
}