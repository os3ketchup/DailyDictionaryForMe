package com.example.dailydictionaryforme

import android.app.Activity
import android.app.Fragment
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.dailydictionaryforme.adapter.WordAdapter
import com.example.dailydictionaryforme.data.Word
import com.example.dailydictionaryforme.database.MyDatabase
import com.example.dailydictionaryforme.databinding.FragmentWordBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber


class WordFragment : androidx.fragment.app.Fragment() {
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController
    lateinit var imageUri: Uri
    private val TAG = "WordFragment"
    lateinit var database: MyDatabase
    lateinit var wordAdapter: WordAdapter
    lateinit var list: ArrayList<Word>
    private var _binding: FragmentWordBinding? = null
    private val binding get() = _binding!!

    @Deprecated("Deprecated in Java")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWordBinding.inflate(inflater, container, false)
        return binding.root
    }

    @Deprecated("Deprecated in Java")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        SettingsFragment.addButton = "Words"
        navHostFragment =
            requireActivity().supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment
        navController = navHostFragment.navController
        update()
    }

    private fun update() {
        database = MyDatabase.getInstance(requireContext())
        if (database.wordDao().getAllWord().isNotEmpty()) {
            list = ArrayList()
            database = MyDatabase.getInstance(requireActivity())
            database.wordDao().getAllWords().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    wordAdapter = WordAdapter(requireContext(), it, navController)
                    binding.rvWords.adapter = wordAdapter
                }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onPause() {
        super.onPause()
        Timber.d("onPause: ")
        SettingsFragment.addButton = "Categories"
    }

    @Deprecated("Deprecated in Java")
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && requestCode == Activity.RESULT_OK) {
            val uri = data?.data ?: return
            imageUri = uri
        }
    }
}