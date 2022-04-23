package com.example.dailydictionaryforme

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.dailydictionaryforme.adapter.CategoryAdapter
import com.example.dailydictionaryforme.data.Category
import com.example.dailydictionaryforme.database.MyDatabase
import com.example.dailydictionaryforme.databinding.FragmentCategoryBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers


class CategoryFragment : Fragment() {
    lateinit var database: MyDatabase
    lateinit var list: ArrayList<Category>
    lateinit var category: Category
    lateinit var categoryAdapter: CategoryAdapter
    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        SettingsFragment.addButton = "Categories"
        setLayout()




    }


    private fun setLayout() {
        list = ArrayList()
        database = MyDatabase.getInstance(requireActivity())

        database.categoryDao().getAllCategories().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                categoryAdapter = CategoryAdapter(requireContext(),it,object :CategoryAdapter.RvClick{
                    override fun itemClick(category: Category) {

                    }
                })
                binding.rvCategory.adapter = categoryAdapter
            }










    /*    categoryAdapter = CategoryAdapter(list,object :CategoryAdapter.RvClick{
            override fun itemClick(category: Category) {

            }
        })*/



    }

    override fun onPause() {
        super.onPause()
        SettingsFragment.addButton = "Words"
        Toast.makeText(requireContext(), "onPause clicked", Toast.LENGTH_SHORT).show()
    }

    override fun onStop() {
        super.onStop()
        Toast.makeText(requireContext(), "ONSTOP clicked", Toast.LENGTH_SHORT).show()
    }



    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()

    }

}