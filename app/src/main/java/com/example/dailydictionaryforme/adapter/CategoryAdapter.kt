package com.example.dailydictionaryforme.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.Menu
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.view.menu.MenuPopupHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.dailydictionaryforme.R
import com.example.dailydictionaryforme.data.Category
import com.example.dailydictionaryforme.databinding.ItemRvCategoryBinding

class CategoryAdapter(var context: Context, var list: List<Category>, val rvClick: RvClick) :
    RecyclerView.Adapter<CategoryAdapter.VH>() {

    inner class VH(private var itemRV: ItemRvCategoryBinding) :
        RecyclerView.ViewHolder(itemRV.root) {
        fun onBind(category: Category) {
            itemRV.tvItemCategory.text = category.name_category

            itemRV.ivInfoIn.setOnClickListener {
                val popupMenu = PopupMenu(context, it)
                popupMenu.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.ic_edit -> {
                            Toast.makeText(context, "edited${category.name_category}", Toast.LENGTH_SHORT).show()
                            true
                        }
                        R.id.ic_delete -> {
                            Toast.makeText(context, "deleted${category.name_category}", Toast.LENGTH_SHORT).show()
                            true
                        }
                        else -> false

                    }
                }
                popupMenu.inflate(R.menu.item_popup)
                try {
                    val fieldMPopup = PopupMenu::class.java.getDeclaredField("mPopup")
                    fieldMPopup.isAccessible = true
                    val mPopup = fieldMPopup.get(popupMenu)
                    mPopup.javaClass.getDeclaredMethod("setForceShowIcon",Boolean::class.java)
                        .invoke(mPopup,true)

                }catch (e:Exception){

                }finally {
                    popupMenu.show()
                }

            }

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemRvCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface RvClick {
        fun itemClick(category: Category)
    }

}