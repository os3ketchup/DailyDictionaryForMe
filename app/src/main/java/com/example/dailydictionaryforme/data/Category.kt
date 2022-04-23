package com.example.dailydictionaryforme.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Category(
    @PrimaryKey(autoGenerate = true) val category_id:Int?=null,

    val name_category:String?=null

)
