package com.example.dailydictionaryforme.data

import androidx.room.Embedded
import androidx.room.Relation

data class CategoryData(
    @Embedded
    val category: Category,

    @Relation(
        parentColumn = "category_id",
        entityColumn = "word_id"
    )

    val wordList:List<Word>


)
