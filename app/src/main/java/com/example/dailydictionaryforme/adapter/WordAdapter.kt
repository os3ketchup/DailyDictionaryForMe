package com.example.dailydictionaryforme.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.dailydictionaryforme.*
import com.example.dailydictionaryforme.data.Word
import com.example.dailydictionaryforme.data.WordSerial
import com.example.dailydictionaryforme.database.MyDatabase
import com.example.dailydictionaryforme.databinding.ItemRvWordsBinding
import kotlinx.android.synthetic.main.fragment_add_word.view.*


var isEdit = false
var isDelete = false

class WordAdapter(var context: Context, var list: List<Word>, var navController: NavController) :
    RecyclerView.Adapter<WordAdapter.VH>() {
    private val TAG = "WordAdapter"
    lateinit var view: ImageView
    var listPosition: Int = 0
    lateinit var wordSerial: WordSerial
    inner class VH(private var itemRV: ItemRvWordsBinding) : RecyclerView.ViewHolder(itemRV.root) {
        val database = MyDatabase.getInstance(context)


        @SuppressLint("ResourceType")
        fun onBind(word: Word) {
            if (database.wordDao().getAllWord().isNotEmpty()) {
                database.categoryDao().getAllCategory().forEach {
                    if (it.category_id == word.category_id) {
                        itemRV.tvItemCategory.text = it.name_category
                    }
                }




                itemRV.tvItemName.text = word.name_word
            }

            itemRV.ivInfoIn.setOnClickListener {
                val popupMenu = PopupMenu(context, it)
                popupMenu.inflate(R.menu.item_popup)

                popupMenu.setOnMenuItemClickListener { item ->
                    wordSerial = WordSerial()
                    wordSerial.categoryId = word.category_id
                    wordSerial.wordId = word.word_id
                    wordSerial.title = word.name_word
                    wordSerial.description = word.description
                    wordSerial.image = word.image
                    when (item.itemId) {
                        R.id.ic_edit -> {
                            navController.navigate(R.id.updateWordFragment, bundleOf("key" to wordSerial))
//                            val dialog =
//                                AlertDialog.Builder(context, R.style.CustomAlertDialog).create()
//                            val customDialog = LayoutInflater.from(context)
//                                .inflate(R.layout.fragment_add_word, null, false)
//
//                            customDialog.iv_gallerys.setOnClickListener {
//                                val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
//                                    addCategory(Intent.CATEGORY_OPENABLE)
//                                    type = "image/*"
//
//                                }
//
//                                (context as Activity).startActivityForResult(
//                                    intent,
//                                    1
//
//                                )
//                                Toast.makeText(context, "${intent.data}", Toast.LENGTH_SHORT).show()
//                            }
//
//                            val cList = database.categoryDao().getAllCategory()
//                            val kList = ArrayList<String>()
//                            val iList = ArrayList<Int>()
//                            val mapI = HashMap<String, Int>()
//                            cList.forEach {
//                                kList.add(it.name_category!!)
//                                iList.add(it.category_id!!)
//                                mapI[it.name_category!!] = it.category_id
//                            }
//
//                            val adapter = ArrayAdapter(context, R.layout.dropdown_item, kList)
//                            customDialog.autoComplete.setAdapter(adapter)
//
//                            customDialog.iv_accept.setOnClickListener {
//
//                                val title = customDialog.et_word
//                                val desc = customDialog.et_description
//                                val s = title.text.toString()
//                                val t = desc.text.toString()
//
//                                word.name_word = s
//                                word.description = t
//                                word.category_id = mapI[customDialog.autoComplete.text.toString()]
//                                database.wordDao().updateWord(word)
//
//                            }
//                            dialog.setView(customDialog)
//                            dialog.show()


                            true
                        }
                        R.id.ic_delete -> {
                            database.wordDao().deleteWord(word)

                            true
                        }
                        else -> false
                    }
                }
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
        return VH(ItemRvWordsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(list[position])
        listPosition = position

    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface RvClick {
        fun itemClick(word: Word)
        fun openPopup(word: Word)
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?, view: ImageView) {

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            val uri = data?.data ?: return
            view.setImageURI(uri)
        }
    }


}