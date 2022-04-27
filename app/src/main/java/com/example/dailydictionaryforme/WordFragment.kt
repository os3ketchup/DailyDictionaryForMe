package com.example.dailydictionaryforme

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.dailydictionaryforme.adapter.WordAdapter
import com.example.dailydictionaryforme.adapter.pos
import com.example.dailydictionaryforme.data.CategoryData
import com.example.dailydictionaryforme.data.Word
import com.example.dailydictionaryforme.database.MyDatabase
import com.example.dailydictionaryforme.databinding.FragmentWordBinding


class WordFragment : Fragment() {
    private val TAG = "WordFragment"
    lateinit var database: MyDatabase
    lateinit var wordAdapter: WordAdapter
    lateinit var list:ArrayList<Word>
    private var _binding: FragmentWordBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWordBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        SettingsFragment.addButton = "Words"

        database = MyDatabase.getInstance(requireContext())
        if (database.wordDao().getAllWords().isNotEmpty()) {

            list = ArrayList()
            wordAdapter = WordAdapter(requireContext(),list)
            /*val data = database.categoryDao().getCategoryByWord() as ArrayList<CategoryData>*/
           /* val data = database.wordDao().getAllWords()*/
            database.wordDao().getAllWords().forEach {
                list.add(it)
            }



            wordAdapter = WordAdapter(requireContext(),list)

            binding.rvWords.adapter = wordAdapter
        }

    }


    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: ")
        SettingsFragment.addButton = "Categories"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}