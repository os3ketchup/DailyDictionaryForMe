package com.example.dailydictionaryforme.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dailydictionaryforme.MyWords
import com.example.dailydictionaryforme.data.CategoryData
import com.example.dailydictionaryforme.data.Word
import com.example.dailydictionaryforme.database.MyDatabase
import com.example.dailydictionaryforme.databinding.ItemRvWordsBinding

class CategoryWordAdapter(
    var context: Context, var list: List<CategoryData>,
     val rvClick: RvClick
) : RecyclerView.Adapter<CategoryWordAdapter.VH>() {
    lateinit var database: MyDatabase

    inner class VH(private var itemRV: ItemRvWordsBinding) : RecyclerView.ViewHolder(itemRV.root) {
        private  val TAG = "CategoryWordAdapter"
        fun onBind(categoryData: CategoryData,position: Int) {
            database = MyDatabase.getInstance(context)
            itemRV.tvItemCategory.text = categoryData.category.name_category
            itemRV.tvItemName.text = categoryData.wordList[position].name_word
            Log.d(TAG, "onBind: ${database.categoryDao().getCategoryByWord()}")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemRvWordsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(list[position],position)
    }

    override fun getItemCount(): Int {
        return list.size
    }


    interface RvClick {
        fun itemClick(word: Word, position: Int)
    }
}