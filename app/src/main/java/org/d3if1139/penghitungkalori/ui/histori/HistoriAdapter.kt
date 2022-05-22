package org.d3if1139.penghitungkalori.ui.histori

import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.d3if1139.penghitungkalori.R
import org.d3if1139.penghitungkalori.databinding.ItemHistoriBinding
import org.d3if1139.penghitungkalori.db.KaloriEntity
import org.d3if1139.penghitungkalori.model.KategoriKalori
import org.d3if1139.penghitungkalori.model.hitungKalori
import java.text.SimpleDateFormat
import java.util.*

class HistoriAdapter :
    ListAdapter<KaloriEntity, HistoriAdapter.ViewHolder>(DIFF_CALLBACK){
    companion object {
        private val DIFF_CALLBACK =
            object : DiffUtil.ItemCallback<KaloriEntity>() {
                override fun areItemsTheSame(
                    oldData: KaloriEntity, newData: KaloriEntity
                ): Boolean {
                    return oldData.id == newData.id
                }
                override fun areContentsTheSame(
                    oldData: KaloriEntity, newData: KaloriEntity
                ): Boolean {
                    return oldData == newData
                }
            }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemHistoriBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: ItemHistoriBinding
    ) : RecyclerView.ViewHolder(binding.root){

        private val dateFormatter = SimpleDateFormat("dd MMMM yyyy",
            Locale("id", "ID")
        )

        fun bind(item: KaloriEntity) = with(binding) {
            val hasilKalori = item.hitungKalori()
            kategoriTextView.text = hasilKalori.kategori.toString().substring(0, 1)
            val colorRes = when (hasilKalori.kategori) {
                KategoriKalori.SEDIKIT -> R.color.sedikit
                KategoriKalori.SEDANG -> R.color.sedang
                else -> R.color.banyak
            }
            val circleBg = kategoriTextView.background as GradientDrawable
            circleBg.setColor(ContextCompat.getColor(root.context, colorRes))

            tanggalTextView.text = dateFormatter.format(Date(item.tanggal))
            kaloriTextView.text = root.context.getString(
                R.string.hasil_x,
                hasilKalori.kalori, hasilKalori.kategori
            )

            val gender = root.context.getString(
                if (item.isMale) R.string.pria else R.string.wanita
            )
            dataTextView.text = root.context.getString(
                R.string.data_x,
                item.berat, item.tinggi, item.usia, gender)
        }
    }
}