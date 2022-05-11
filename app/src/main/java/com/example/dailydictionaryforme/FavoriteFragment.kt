package com.example.dailydictionaryforme

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.dailydictionaryforme.adapter.CategoryByWordAdapter
import com.example.dailydictionaryforme.data.Word
import com.example.dailydictionaryforme.data.WordSerial
import com.example.dailydictionaryforme.database.MyDatabase
import com.example.dailydictionaryforme.databinding.FragmentAboutWordsBinding
import com.example.dailydictionaryforme.databinding.FragmentFavoriteBinding


class FavoriteFragment : Fragment() {
    lateinit var binding: FragmentFavoriteBinding
    lateinit var database: MyDatabase
    lateinit var categoryByWordAdapter: CategoryByWordAdapter
    lateinit var list: ArrayList<Word>
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navHostFragment =
            requireActivity().supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment
        navController = navHostFragment.navController


        database = MyDatabase.getInstance(requireContext())
        list = ArrayList()
        database.wordDao().getAllWord().forEach {
            if (it.favorite==1){
                list.add(it)
            }
        }
        categoryByWordAdapter = CategoryByWordAdapter(requireContext(),list,navController)
        binding.rvFavorite.adapter = categoryByWordAdapter
    }


}