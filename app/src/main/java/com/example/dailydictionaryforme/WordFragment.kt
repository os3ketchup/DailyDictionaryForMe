package com.example.dailydictionaryforme

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dailydictionaryforme.databinding.FragmentSettingsBinding
import com.example.dailydictionaryforme.databinding.FragmentWordBinding


class WordFragment : Fragment() {
    private  val TAG = "WordFragment"
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