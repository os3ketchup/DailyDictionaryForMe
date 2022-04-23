package com.example.dailydictionaryforme.dao

import androidx.room.*
import com.example.dailydictionaryforme.data.Category
import com.example.dailydictionaryforme.data.CategoryData
import com.example.dailydictionaryforme.data.Word
@Dao
interface WordDao {
    @Query("select * from word")
    fun getAllWords():List<Word>

    @Insert
    fun addWord(word: Word)

    @Delete
    fun deleteWord(word: Word)

    @Update
    fun updateWord(word: Word)
}