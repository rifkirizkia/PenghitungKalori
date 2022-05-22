package org.d3if1139.penghitungkalori.ui.histori

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.d3if1139.penghitungkalori.db.KaloriDao
import java.lang.IllegalArgumentException

class HistoriViewModelFactory (
    private val db: KaloriDao
        ): ViewModelProvider.Factory{
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HistoriViewModel::class.java)){
            return HistoriViewModel(db) as T
        }
        throw  IllegalArgumentException("Unknow ViewModel class")
    }
        }