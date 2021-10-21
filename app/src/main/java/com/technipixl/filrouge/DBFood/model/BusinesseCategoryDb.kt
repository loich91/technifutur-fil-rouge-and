package com.technipixl.filrouge.DBFood.model

import androidx.room.*
import com.technipixl.filrouge.Model.Businesse


@Entity(primaryKeys = ["idDb","idCat"])
class BusinesseCategoryDb (

    val idDb:Int,
    val idCat:Int
        )

data class BusinesswithCategory(
    @Embedded
    val businesse: BusineseDb,
    @Relation(
        parentColumn = "idDb",
        entityColumn = "idCat",
        associateBy = Junction(BusinesseCategoryDb::class)
    )
    val category: List<CategoryDb>
)

data class CategorywithBusiness(
    @Embedded
    val category: CategoryDb,
    @Relation(
        parentColumn = "idCat",
        entityColumn = "idDb",
        associateBy = Junction(BusinesseCategoryDb::class)
    )
    val businesse: List<BusineseDb>
)