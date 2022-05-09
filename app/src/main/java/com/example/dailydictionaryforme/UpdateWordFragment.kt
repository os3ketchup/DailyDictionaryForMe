package com.example.dailydictionaryforme

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.dailydictionaryforme.data.Word
import com.example.dailydictionaryforme.data.WordSerial
import com.example.dailydictionaryforme.database.MyDatabase
import com.example.dailydictionaryforme.databinding.FragmentUpdateWordBinding
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import kotlinx.android.synthetic.main.fragment_update_word.*
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.collections.forEach
import kotlin.collections.set


class UpdateWordFragment : Fragment() {
     private var pathImage: String = ""
    private var image = ""
    lateinit var binding: FragmentUpdateWordBinding
    lateinit var database: MyDatabase
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentUpdateWordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        database = MyDatabase.getInstance(requireContext())

        binding.toolbarUpdate.setOnClickListener {
            findNavController().popBackStack()
        }

        val cList = database.categoryDao().getAllCategory()
        val kList = ArrayList<String>()
        val iList = ArrayList<Int>()
        val mapI = HashMap<String, Int>()
        cList.forEach {
            kList.add(it.name_category!!)
            iList.add(it.category_id!!)
            mapI[it.name_category!!] = it.category_id
        }
        val wordSerial = arguments?.getSerializable("key") as WordSerial
        pathImage = wordSerial.image.toString()
        val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, kList)
        binding.autoCompleteUpdate.setAdapter(adapter)
        wordSerial.image = pathImage
        val bitMap = BitmapFactory.decodeFile(wordSerial.image)
        val bitClearMap = BitmapFactory.decodeFile("")
        (binding.etLayout.editText as MaterialAutoCompleteTextView).setText(
            adapter.getItem(0),
            false
        )
        binding.ivUpdateGallery.setImageBitmap(bitMap)
        binding.etWordUpdate.setText(wordSerial.title)
        binding.etDescriptionUpdate.setText(wordSerial.description)
        binding.ivClear.setOnClickListener {
            binding.ivUpdateGallery.setImageBitmap(bitClearMap)
            pathImage = ""
        }




        binding.ivAcceptUpdate.setOnClickListener {
            if (binding.etWordUpdate.text.toString().isNotEmpty()&&binding.etDescriptionUpdate.text.toString().isNotEmpty()){

                val title = binding.etWordUpdate.text.toString()
                val desc = binding.etDescriptionUpdate.text.toString()

                 image = pathImage


                val wordId = mapI[binding.autoCompleteUpdate.text.toString()]
                val word = Word(
                    word_id = wordSerial.wordId,
                    category_id = wordId,
                    name_word = title,
                    description = desc,
                    image = image
                )
                database.wordDao().updateWord(word)
                binding.etWordUpdate.text?.clear()
                binding.etDescriptionUpdate.text?.clear()
                Toast.makeText(requireContext(), "Successfully edited", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }else{
                Toast.makeText(requireContext(), "Please fill the gaps", Toast.LENGTH_SHORT).show()
            }


        }


        binding.ivUpdateGallery.setOnClickListener {
            startActivityForResult(
                Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                    addCategory(Intent.CATEGORY_OPENABLE)
                    type = "image/*"
                }, 3
            )


        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 3 && resultCode == Activity.RESULT_OK) {
            val uri = data?.data ?: return
            binding.ivUpdateGallery.setImageURI(uri)
            val inputStream = requireContext().contentResolver?.openInputStream(uri)
            val simpleDate = SimpleDateFormat("hh:mm:ss")
            val currentDate = simpleDate.format(Date())
            val file = File(requireContext().filesDir, "$currentDate.jpg")
            val outputStream = FileOutputStream(file)
            inputStream?.copyTo(outputStream)
            inputStream?.close()
            outputStream.close()

            pathImage = file.absolutePath


        }
    }


}