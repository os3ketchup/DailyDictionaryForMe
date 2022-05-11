package com.example.dailydictionaryforme.data

import java.io.Serializable

class WordSerial:Serializable{
    var categoryId:Int? = null
    var wordId:Int? = null
    var title:String? = null
    var description:String?=null
    var image:String? = null
    var likes:Int = 0
}