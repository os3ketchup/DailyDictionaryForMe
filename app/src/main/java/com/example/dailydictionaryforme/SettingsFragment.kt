package com.example.dailydictionaryforme

import android.app.AlertDialog
import android.app.Dialog
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.dailydictionaryforme.adapter.CategoryAdapter
import com.example.dailydictionaryforme.data.Category
import com.example.dailydictionaryforme.database.MyDatabase
import com.example.dailydictionaryforme.databinding.FragmentHostBinding
import com.example.dailydictionaryforme.databinding.FragmentSettingsBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers


class SettingsFragment : Fragment() {
    lateinit var myDatabase: MyDatabase
    lateinit var category: Category
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!


    companion object {
        var addButton: String = "navigation"

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setLayout()


        binding.toolbarSettings.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.ivAdd.setOnClickListener {
            when (addButton) {
                "Categories" -> {
                    val dialog = AlertDialog.Builder(binding.root.context).create()
                    val customDialog = LayoutInflater.from(binding.root.context)
                        .inflate(R.layout.dialog_category, null, false)

                    customDialog.findViewById<Button>(R.id.button_category).setOnClickListener {
                        val name = customDialog.findViewById<EditText>(R.id.tv_item_dialog)

                        val    categoryName = name.text.toString()
                        category = Category(name_category = categoryName)
                        myDatabase = MyDatabase.getInstance(requireContext())


                        myDatabase.categoryDao().addCategory(category)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe{
                                t->
                            }
                        Toast.makeText(requireContext(), categoryName, Toast.LENGTH_SHORT).show()
                        dialog.cancel()
                    }


                    dialog.setView(customDialog)
                    dialog.show()
                }
                "Words" -> {
                    findNavController().navigate(R.id.action_settingsFragment_to_addWordFragment)
                }
            }
        }


    }

    private fun setLayout() {
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.container_settings) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavSettings.setupWithNavController(navController)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.categoryFragment -> {
                addButton = "Categories"
                Toast.makeText(this.requireContext(), addButton, Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.wordFragment -> {
                addButton = "Words"
                Toast.makeText(this.requireContext(), addButton, Toast.LENGTH_SHORT).show()
                return true
            }
            else -> false
        }
    }
}