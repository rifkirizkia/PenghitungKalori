package org.d3if1139.penghitungkalori.model

import org.d3if1139.penghitungkalori.db.KaloriEntity

fun KaloriEntity.hitungKalori(): HasilKalori{
    val rumusBeratL = 88.4
    val rumusTinggiL = 4.8
    val rumusUsiaL = 5.68
    val rumusBeratP = 447.6
    val rumusTinggiP = 3.10
    val rumusUsiaP = 4.33
    val kalori = if(isMale){
        (rumusBeratL + 13.4 * berat) + (rumusTinggiL * tinggi) - (rumusUsiaL * usia)
    }else{
        (rumusBeratP + 9.25 * berat) + (rumusTinggiP * tinggi) - (rumusUsiaP * usia)
    }
    val kategori = if(isMale){
        when{
            kalori < 1000 ->KategoriKalori.SEDIKIT
            kalori >= 1500 ->KategoriKalori.BANYAK
            else -> KategoriKalori.SEDANG
        }
    }else{
        when{
            kalori < 1000 ->KategoriKalori.SEDIKIT
            kalori >= 1500 ->KategoriKalori.BANYAK
            else -> KategoriKalori.SEDANG
        }
    }
    return HasilKalori(kalori, kategori)
}