package com.example.dailydictionaryforme

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.dailydictionaryforme.adapter.ViewPagerAdapter
import com.example.dailydictionaryforme.data.Category
import com.example.dailydictionaryforme.data.Word
import com.example.dailydictionaryforme.database.MyDatabase
import com.example.dailydictionaryforme.databinding.FragmentBasicBinding
import com.google.android.material.tabs.TabLayoutMediator
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers


class BasicFragment : Fragment() {
    lateinit var binding: FragmentBasicBinding
    lateinit var adapter: ViewPagerAdapter
    lateinit var database: MyDatabase
    lateinit var list: List<Category>
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBasicBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navHostFragment =
            requireActivity().supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment
        navController = navHostFragment.navController
        database = MyDatabase.getInstance(requireActivity())
        database.categoryDao().getAllCategories().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                adapter = ViewPagerAdapter(requireContext(), it,navController)
                binding.rvBasic.adapter = adapter
                TabLayoutMediator(
                    binding.tabLayoutBasic, binding.rvBasic, true
                ) { tab, position ->
                    tab.text = it[position].name_category
                }.attach()
            }
    }

}