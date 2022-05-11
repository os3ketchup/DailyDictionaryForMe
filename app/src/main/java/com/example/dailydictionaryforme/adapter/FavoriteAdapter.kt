package com.example.dailydictionaryforme.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dailydictionaryforme.data.Word
import com.example.dailydictionaryforme.database.MyDatabase
import com.example.dailydictionaryforme.databinding.ItemRvBinding

class FavoriteAdapter(var context: Context ,var list: List<Word>): RecyclerView.Adapter<FavoriteAdapter.VH>() {

    inner class VH(private var itemRV: ItemRvBinding):RecyclerView.ViewHolder(itemRV.root){
        fun onBind(word: Word){
            val database = MyDatabase.getInstance(context)
            if (database.wordDao().getAllWord().isNotEmpty()) {
                database.categoryDao().getAllCategory().forEach {
                    if (it.category_id == word.category_id) {
                        itemRV.tvItemCategory.text = it.name_category
                    }
                }

                itemRV.tvItemName.text = word.name_word
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemRvBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}