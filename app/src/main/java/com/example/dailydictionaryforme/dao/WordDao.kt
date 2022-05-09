package com.example.dailydictionaryforme.dao

import androidx.room.*
import com.example.dailydictionaryforme.data.Category
import com.example.dailydictionaryforme.data.CategoryData
import com.example.dailydictionaryforme.data.Word
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.selects.select

@Dao
interface WordDao {
    @Query("select * from word")
        fun getAllWords(): Observable<List<Word>>

    @Query("select * from word")
    fun getAllWord():List<Word>

    @Query("select * from word")
    fun getWord(): Word

    @Query("select * from word where category_id= :category_id")
    fun getWordById(category_id: Int):List<Word>

    @Update
    fun updateWord(word: Word)

    @Query("select * from word")
    fun deleteAllWords(): List<Word>

    @Insert
    fun addWord(word: Word): Single<Long>

    @Delete
    fun deleteWord(word: Word)


}