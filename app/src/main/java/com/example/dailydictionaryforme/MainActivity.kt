package com.example.dailydictionaryforme

import android.content.ContentValues.TAG
import android.database.Observable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import com.example.dailydictionaryforme.data.Category
import com.example.dailydictionaryforme.data.CategoryData
import com.example.dailydictionaryforme.data.Word
import com.example.dailydictionaryforme.database.MyDatabase
import com.example.dailydictionaryforme.databinding.ActivityMainBinding
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

var incrementer = 0
private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    /*lateinit var db:MyDatabase*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        RxJavaPlugins.setErrorHandler(Timber::e)

    }


}