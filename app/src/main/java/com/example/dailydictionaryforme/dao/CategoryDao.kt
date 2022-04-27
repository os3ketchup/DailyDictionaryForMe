package com.example.dailydictionaryforme.dao

import androidx.room.*
import com.example.dailydictionaryforme.data.Category
import com.example.dailydictionaryforme.data.CategoryData
import com.example.dailydictionaryforme.data.Word
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

@Dao
interface CategoryDao {
    @Transaction
    @Query("select * from category")
    fun getCategoryByWord(): List<CategoryData>

    @Query("select * from category")
    fun getAllCategories(): Observable<List<Category>>

    @Query("select * from category")
    fun getAllCategory(): List<Category>

    @Insert
    fun addCategory(category: Category): Single<Long>

    @Insert
    fun addAllCategory(vararg category: Category)

    @Insert
    fun addListCategory(list: List<Category>)

    @Delete
    fun deleteCategory(category: Category)




    @Update
    fun editCategory(category: Category)


}