package com.example.dailydictionaryforme.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dailydictionaryforme.dao.CategoryDao

import com.example.dailydictionaryforme.dao.WordDao
import com.example.dailydictionaryforme.data.Category
import com.example.dailydictionaryforme.data.Word


@Database(
    entities = [Category::class,Word::class],
    version = 1
)

abstract class MyDatabase:RoomDatabase() {
    abstract fun categoryDao():CategoryDao
    abstract fun wordDao():WordDao
    companion object{
        private var instance:MyDatabase?=null

        @Synchronized
        fun getInstance(context: Context):MyDatabase{
            if (instance==null){
                instance = Room.databaseBuilder(context,MyDatabase::class.java,"wordDb")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }
            return instance!!
        }
    }
}