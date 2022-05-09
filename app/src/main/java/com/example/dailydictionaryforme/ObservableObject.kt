package com.example.dailydictionaryforme

import android.database.Observable
import com.example.dailydictionaryforme.adapter.isEdit
import io.reactivex.rxjava3.internal.operators.observable.ObservableCreate

object ObservableObject {
    fun listener(): ObservableCreate<Boolean> {
        return ObservableCreate<Boolean>{
            it.onNext(isEdit)
        }

    }
}