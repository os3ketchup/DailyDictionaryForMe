package com.example.dailydictionaryforme.dao

import androidx.room.*
import com.example.dailydictionaryforme.data.Category
import com.example.dailydictionaryforme.data.CategoryData
import com.example.dailydictionaryforme.data.Word
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

@Dao
interface WordDao {
    @Query("select * from word")
        fun getAllWords(): Observable<List<Word>>

    @Query("select * from word")
    fun getAllWord():List<Word>

    @Query("select * from word")
    fun getWord(): Word
    @Update
    fun updateWord(word: Word)

    @Query("select * from word")
    fun deleteAllWords(): List<Word>

    @Insert
    fun addWord(word: Word): Single<Long>

    @Delete
    fun deleteWord(word: Word)


}