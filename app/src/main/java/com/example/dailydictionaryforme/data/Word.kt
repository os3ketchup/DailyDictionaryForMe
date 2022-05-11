package com.example.dailydictionaryforme.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(entity = Category::class, parentColumns = ["category_id"], childColumns = ["category_id"], onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE)]
)
data class Word(

    var category_id:Int?=null,
    @PrimaryKey(autoGenerate = true)

    var word_id:Int?=null,
    var name_word:String?=null,
    var description:String?=null,
    var image:String?=null,
    var favorite:Int? = 0
)
