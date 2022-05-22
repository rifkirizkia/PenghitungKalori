package org.d3if1139.penghitungkalori.ui.histori

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.d3if1139.penghitungkalori.db.KaloriDao

class HistoriViewModel(private val db: KaloriDao): ViewModel(){
    val data = db.getLastKalori()
    fun hapusData() = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            db.clearData()
        }
    }
}