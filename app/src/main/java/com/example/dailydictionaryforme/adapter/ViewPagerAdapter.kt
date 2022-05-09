package com.example.dailydictionaryforme.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.dailydictionaryforme.data.Category
import com.example.dailydictionaryforme.data.CategoryData
import com.example.dailydictionaryforme.data.Word
import com.example.dailydictionaryforme.database.MyDatabase
import com.example.dailydictionaryforme.databinding.FragmentWordBinding
import com.example.dailydictionaryforme.databinding.FragmentWordsBinding
import com.example.dailydictionaryforme.databinding.ItemRvBinding
import com.example.dailydictionaryforme.databinding.ItemRvViewpagerBinding
import kotlinx.android.synthetic.main.fragment_word.view.*

class ViewPagerAdapter(var context: Context, private val mList:List<Category>,var navController: NavController): RecyclerView.Adapter<ViewPagerAdapter.VH>() {

    inner class VH( var itemRV: ItemRvViewpagerBinding):RecyclerView.ViewHolder(itemRV.root){

        val database = MyDatabase.getInstance(context)
        fun onBind(category: Category){
                val words = database.wordDao().getWordById(category.category_id!!)

                itemRV.root.adapter = CategoryByWordAdapter(context,words,navController)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemRvViewpagerBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(mList[position])
    }

    override fun getItemCount(): Int {
        return mList.size
    }
}