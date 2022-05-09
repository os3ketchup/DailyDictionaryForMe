package com.example.dailydictionaryforme

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.dailydictionaryforme.data.Category
import com.example.dailydictionaryforme.data.Word
import com.example.dailydictionaryforme.database.MyDatabase
import com.example.dailydictionaryforme.databinding.FragmentAddWordBinding
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_add_word.*
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class AddWordFragment : Fragment() {
     private var pathImage:String = ""
    lateinit var category: Category
    lateinit var word: Word
    lateinit var database: MyDatabase
    private var _binding: FragmentAddWordBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddWordBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        database = MyDatabase.getInstance(requireContext())
        binding.toolbarAddWord.setNavigationOnClickListener {
            findNavController().popBackStack()

        }
        val bitClearMap = BitmapFactory.decodeFile("")
        binding.ivClear.setOnClickListener {
            binding.ivGallerys.setImageBitmap(bitClearMap)
            pathImage = ""
        }



        binding.ivGallerys.setOnClickListener {
            startActivityForResult(
                Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                    addCategory(Intent.CATEGORY_OPENABLE)
                    type = "image/*"
                },2
            )


        }


        val cList = database.categoryDao().getAllCategory()
        val kList = ArrayList<String>()
        val iList = ArrayList<Int>()
        val mapI = HashMap<String,Int>()
        val categoryName = ArrayList<Category>()

        categoryName.addAll(cList)
        categoryName.forEach {
            kList.add(it.name_category!!)
            iList.add(it.category_id!!)

            mapI[it.name_category!!] = it.category_id
        }


        val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, kList)
        binding.autoComplete.setAdapter(adapter)

        (binding.etLayout.editText as MaterialAutoCompleteTextView).setText(
            adapter.getItem(0),
            false
        )

        binding.ivAccept.setOnClickListener {
            if (binding.etWord.text.toString().isNotEmpty()&&binding.etDescription.text.toString().isNotEmpty()){

                val wordName =   binding.etWord.text.toString()
                val wordDesc = binding.etDescription.text.toString()
                val wordId = mapI[binding.autoComplete.text.toString()]
                val imageWord = pathImage


                word = Word(category_id = wordId, name_word = wordName, description = wordDesc, image = imageWord)
                /*database.wordDao().addWord(word)*/

                database.wordDao().addWord(word)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe{
                            t->
                    }
                binding.etWord.text?.clear()
                binding.etDescription.text?.clear()
                Toast.makeText(requireContext(), "Successfully added", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(requireContext(), "Please fill the gaps", Toast.LENGTH_SHORT).show()
            }

            }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @SuppressLint("SimpleDateFormat")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==2&&resultCode==Activity.RESULT_OK){
            val uri = data?.data?:return
            binding.ivGallerys.setImageURI(uri)
            val inputStream = requireContext().contentResolver?.openInputStream(uri)
            val simpleDate = SimpleDateFormat("hh:mm:ss")
            val currentDate = simpleDate.format(Date())
            val file = File(requireContext().filesDir,"$currentDate.jpg")
            val outputStream = FileOutputStream(file)
            inputStream?.copyTo(outputStream)
            inputStream?.close()
            outputStream.close()
            pathImage = file.absolutePath
            Toast.makeText(requireContext(), file.absolutePath, Toast.LENGTH_SHORT).show()
        }
    }

}