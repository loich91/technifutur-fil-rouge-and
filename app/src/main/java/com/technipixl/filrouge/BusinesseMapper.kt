package com.technipixl.filrouge

import com.technipixl.filrouge.DBFood.model.BusineseDb
import com.technipixl.filrouge.DBFood.model.CoordinateDb
import com.technipixl.filrouge.Model.Businesse
import com.technipixl.filrouge.Model.Category
import com.technipixl.filrouge.Model.Coordinates
import com.technipixl.filrouge.Model.Location

open class BusinesseMapper  {
    fun transfortoBusinesse (buisenessDb:List<BusineseDb>) : List<Businesse>? {
        var businesse:List<Businesse> = emptyList()
        var category : List<Category>
        var coordinate : Coordinates
        var location : Location
        buisenessDb.forEach {
            category = listOf<Category>(
                Category(it.categories[0].alias,it.categories[0].title)
            )
            coordinate = Coordinates(it.coordinates.latitude,it.coordinates.longitude)
            location = Location(it.location.address1,it.location.address2,it.location.address3,it.location.city,it.location.country,it.location.state,it.location.zip_code)
            businesse  = listOf<Businesse>(

                Businesse(it.alias,category,coordinate,it.phone,it.distance,it.id,it.image_url,it.is_closed,location,it.name,it.phone,it.price,it.rating,it.review_count,it.url)
            )

        }
        return businesse




    }
}