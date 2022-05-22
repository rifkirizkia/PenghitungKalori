package org.d3if1139.penghitungkalori.ui.hitung

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.d3if1139.penghitungkalori.db.KaloriDao
import org.d3if1139.penghitungkalori.db.KaloriEntity
import org.d3if1139.penghitungkalori.model.HasilKalori
import org.d3if1139.penghitungkalori.model.KategoriKalori
import org.d3if1139.penghitungkalori.model.hitungKalori

class HitungViewModel(private val db: KaloriDao) : ViewModel() {
    private val hasilKalori = MutableLiveData<HasilKalori?>()
    private val navigasi = MutableLiveData<KategoriKalori?>()

    fun hitungKalori(berat: Double, tinggi: Double, isMale: Boolean, usia: Double) {
        val dataKalori = KaloriEntity(
            berat = berat,
            tinggi = tinggi,
            isMale = isMale,
            usia = usia
        )
        hasilKalori.value = dataKalori.hitungKalori()

        viewModelScope.launch {
            withContext(Dispatchers.IO){
                db.insert(dataKalori)
            }
        }
    }

    fun getHasilKalori(): LiveData<HasilKalori?> = hasilKalori

    fun mulaiNavigasi(){
        navigasi.value = hasilKalori.value?.kategori
    }

    fun selesaiNavigasi(){
        navigasi.value = null
    }

    fun getNavigasi() : LiveData<KategoriKalori?> = navigasi
}