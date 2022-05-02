package com.example.dailydictionaryforme

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.dailydictionaryforme.adapter.CategoryWordAdapter
import com.example.dailydictionaryforme.data.Category
import com.example.dailydictionaryforme.data.Word
import com.example.dailydictionaryforme.database.MyDatabase
import com.example.dailydictionaryforme.databinding.FragmentAddWordBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

lateinit var map:HashMap<String,Int>
class AddWordFragment : Fragment() {

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
        binding.ivAccept.setOnClickListener {
          val wordName =   binding.etWord.text.toString()
            val wordDesc = binding.etDescription.text.toString()
            val wordId = mapI[binding.autoComplete.text.toString()]
            val imageWord = 1
            word = Word(category_id = wordId, name_word = wordName, description = wordDesc, image = imageWord)
            /*database.wordDao().addWord(word)*/

            database.wordDao().addWord(word)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{
                        t->
                }

            /*Toast.makeText(requireContext(), "${database.wordDao().getWord().name_word}", Toast.LENGTH_SHORT).show()*/
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}