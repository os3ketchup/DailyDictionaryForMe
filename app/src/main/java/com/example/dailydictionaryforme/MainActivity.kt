package com.example.dailydictionaryforme

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.dailydictionaryforme.data.Category
import com.example.dailydictionaryforme.data.CategoryData
import com.example.dailydictionaryforme.data.Word
import com.example.dailydictionaryforme.database.MyDatabase
import com.example.dailydictionaryforme.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    /*lateinit var db:MyDatabase*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()


       /*  db = MyDatabase.getInstance(this)
        val category1 = Category(name_category = "Dunyo")
        val category2 = Category(name_category = "Texnologiya")
        val category3 = Category(name_category = "Mahalliy")*/




        /*db.categoryDao().addAllCategory(category1, category2, category3)

        val data = db.categoryDao().getCategoryByWord()
        Toast.makeText(this, data.toString(), Toast.LENGTH_SHORT).show()*/

    }


}