package com.technipixl.filrouge

import androidx.room.Embedded
import com.technipixl.filrouge.DBFood.model.BusineseDb
import com.technipixl.filrouge.DBFood.model.BusinesswithCategory
import com.technipixl.filrouge.DBFood.model.CoordinateDb
import com.technipixl.filrouge.DBFood.model.LocationDb
import com.technipixl.filrouge.Model.Businesse
import com.technipixl.filrouge.Model.Category
import com.technipixl.filrouge.Model.Coordinates
import com.technipixl.filrouge.Model.Location

open class BusinesseMapper  {
    fun transfortoBusinesse (buisenessDb:BusinesswithCategory) : Businesse {
        val categorie = mutableListOf<Category>()
            buisenessDb.category.forEach {
                categorie.add(Category(it.alias,it.title))
            }

        val cat =  categorie.toList()
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
                longitude = buisenessDb.businesse.coordinates.longitude!!
            ),
            distance = buisenessDb.businesse.distance!!,
            is_closed = false,
            categories = cat

        )




    }
    fun transformToBusineseDb(businesse: Businesse):BusineseDb{

        return BusineseDb(
            idDb= 0,
         id=businesse.id,
         name=businesse.name,
         alias=businesse.alias,
         display_phone=businesse.display_phone,
         distance=businesse.distance,
         image_url=businesse.image_url,
         is_closed=businesse.is_closed,
         phone=businesse.phone,
         price=businesse.price,
         rating=businesse.rating,
         review_count=businesse.review_count,
         url=businesse.url,
         location= LocationDb(
             address1 = businesse.location.address1 ?: "",
             address2 = businesse.location.address2 ?: "",
             address3 = businesse.location.address3 ?: "",
             city = businesse.location.city ?: "",
             country = businesse.location.country?: "",
             state = businesse.location.state ?: "",
             zip_code = businesse.location.zip_code ?: "",
         ),
         coordinates= CoordinateDb(
             latitude = businesse.coordinates.latitude,
             longitude = businesse.coordinates.longitude
         )
        )


    }
}

