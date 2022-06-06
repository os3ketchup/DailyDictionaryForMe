package com.example.dailydictionaryforme.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation
@Entity
data class CategoryData(
    @Embedded
    val category: Category,
    @Relation(
        parentColumn = "category_id",
        entityColumn = "word_id"
    )
    var wordList:List<Word>
)
