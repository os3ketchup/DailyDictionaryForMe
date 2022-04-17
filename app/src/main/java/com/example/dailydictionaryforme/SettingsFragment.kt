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
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.dailydictionaryforme.databinding.FragmentHostBinding
import com.example.dailydictionaryforme.databinding.FragmentSettingsBinding


class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    companion object {
        var addButton: String = "testing"
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
            when(addButton){
                "Categories"->{
                    val dialog = AlertDialog.Builder(requireContext()).create()
                    val customDialog = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_category,null,false)
                    dialog.setView(customDialog)
                    dialog.show()
                }
                "Words"->{
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