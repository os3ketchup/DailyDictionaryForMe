package com.example.dailydictionaryforme

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.dailydictionaryforme.data.Word
import com.example.dailydictionaryforme.data.WordSerial
import com.example.dailydictionaryforme.database.MyDatabase
import com.example.dailydictionaryforme.databinding.FragmentAboutWordsBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers


class AboutWordsFragment : Fragment() {
    lateinit var binding:FragmentAboutWordsBinding
    lateinit var word: Word
    lateinit var database: MyDatabase
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAboutWordsBinding.inflate(inflater,container,false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbarAddWord.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        database = MyDatabase.getInstance(requireContext())
        val wordSerial = arguments?.getSerializable("info") as WordSerial
        binding.tvWord.text = "Word: ${wordSerial.title}"
        binding.tvDescription.text = "Description: ${wordSerial.description}"
        val bitMap = BitmapFactory.decodeFile(wordSerial.image)
        database.categoryDao().getAllCategory().forEach {
            if (it.category_id == wordSerial.categoryId) {
                binding.tvCategory.text = "Category: ${it.name_category}"
                binding.toolbarAddWord.title = it.name_category
            }
        }
        binding.ivGallery.setImageBitmap(bitMap)
        if (wordSerial.likes==1){
            binding.ivLike.setImageResource(R.drawable.ic_likes)
        }else{
            binding.ivLike.setImageResource(R.drawable.ic_favorite)
        }
        binding.ivLike.setOnClickListener {
            val wordName =   wordSerial.title
            val categoryId = wordSerial.categoryId
            val wordDesc = wordSerial.description
            val wordId = wordSerial.wordId
            val imageWord = wordSerial.image
            word = Word(word_id = wordId, category_id = categoryId, name_word = wordName, description = wordDesc, image = imageWord, favorite = wordSerial.likes)
            if (word.favorite==0){
                binding.ivLike.setImageResource(R.drawable.ic_likes)
                wordSerial.likes = 1
            }else{
                binding.ivLike.setImageResource(R.drawable.ic_favorite)
                wordSerial.likes = 0
            }
            val favorite = wordSerial.likes
            word = Word(word_id = wordId, category_id = categoryId, name_word = wordName, description = wordDesc, image = imageWord, favorite = favorite)
            database.wordDao().updateWord(word)
        }
    }
}