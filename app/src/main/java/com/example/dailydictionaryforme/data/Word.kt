package com.example.dailydictionaryforme.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity()
data class Word(
    val category_id:Int?=null,
    @PrimaryKey(autoGenerate = true)
    val word_id:Int?=null,
    val name_word:String?=null,
    val description:String?=null,
    val image:Int?=null
)
