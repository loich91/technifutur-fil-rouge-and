package com.technipixl.filrouge.DBFood.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation
import com.technipixl.filrouge.Model.Businesse

@Entity(primaryKeys = ["idDb","idCat"])
class BusinesseCategoryDb (

    val busineseid:Int,
    val categoryId:Int
        )

data class BusinesswithCategory(
    @Embedded
    val businesse: BusineseDb,
    @Relation(
        parentColumn = "busineseid",
        entityColumn = "categoryId",
        associateBy = Junction(BusinesseCategoryDb::class)
    )
    val category: List<CategoryDb>
)

data class CategorywithBusiness(
    @Embedded
    val category: CategoryDb,
    @Relation(
        parentColumn = "categoryId",
        entityColumn = "busineseid",
        associateBy = Junction(BusinesseCategoryDb::class)
    )
    val businesse: List<BusineseDb>
)