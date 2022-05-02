package com.example.dailydictionaryforme.adapter

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupMenu
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.dailydictionaryforme.MyWords.word
import com.example.dailydictionaryforme.R
import com.example.dailydictionaryforme.data.Category
import com.example.dailydictionaryforme.database.MyDatabase
import com.example.dailydictionaryforme.databinding.ItemRvBinding
import com.example.dailydictionaryforme.databinding.ItemRvCategoryBinding
import com.example.dailydictionaryforme.incrementer

import com.google.android.material.textfield.TextInputEditText

var t = 0

class CategoryAdapter(var context: Context, var list: List<Category>) :
    RecyclerView.Adapter<CategoryAdapter.VH>() {
    lateinit var database: MyDatabase

    inner class VH(private var itemRV: ItemRvCategoryBinding) :
        RecyclerView.ViewHolder(itemRV.root) {
        fun onBind(category: Category) {
            itemRV.tvItemCategory.text = category.name_category
            database = MyDatabase.getInstance(context)
            itemRV.ivInfoIn.setOnClickListener {
                val popupMenu = PopupMenu(context, it)

                popupMenu.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.ic_edit -> {
                            val dialog = AlertDialog.Builder(context).create()
                            val customDialog = LayoutInflater.from(context)
                                .inflate(R.layout.dialog_category, null, false)

                            customDialog.findViewById<Button>(R.id.button_category)
                                .setOnClickListener {
                                    val name =
                                        customDialog.findViewById<TextInputEditText>(R.id.et_dialog)

                                    val s = name.text.toString()

                                    category.name_category = s
                                    database.categoryDao().editCategory(category)
                                    Toast.makeText(
                                        context,
                                        category.name_category,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    dialog.cancel()
                                }


                            dialog.setView(customDialog)
                            dialog.show()

                            true
                        }
                        R.id.ic_delete -> {
                            incrementer++
                            database.categoryDao().deleteCategory(category)


                            true
                        }
                        else -> false
                    }
                }
                popupMenu.inflate(R.menu.item_popup)
                popupMenu.show()

                try {
                    val fieldMPopup = PopupMenu::class.java.getDeclaredField("mPopup")
                    fieldMPopup.isAccessible = true
                    val mPopup = fieldMPopup.get(popupMenu)
                    mPopup.javaClass.getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                        .invoke(mPopup, true)

                } catch (e: Exception) {

                } finally {
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
        t = position
    }

    override fun getItemCount(): Int {
        return list.size
    }


}


