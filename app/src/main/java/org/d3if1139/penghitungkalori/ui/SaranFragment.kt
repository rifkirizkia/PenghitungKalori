package org.d3if1139.penghitungkalori.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import org.d3if1139.penghitungkalori.R
import org.d3if1139.penghitungkalori.databinding.FragmentSaranMakananBinding
import org.d3if1139.penghitungkalori.model.KategoriKalori

class SaranFragment : Fragment(){
    private lateinit var binding: FragmentSaranMakananBinding
    private val args: SaranFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSaranMakananBinding.inflate(inflater, container, false)
        return binding.root
    }
    private fun updateUI(kategoriKalori: KategoriKalori){
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        when (kategoriKalori){
            KategoriKalori.SEDIKIT -> {
                actionBar?.title = getString(R.string.judul_sedikit)
                binding.imageView.setImageResource(R.drawable.sedikit)
                binding.saranTextView.text = getString(R.string.saran_sedikit)
            }
            KategoriKalori.SEDANG -> {
                actionBar?.title = getString(R.string.judul_sedang)
                binding.imageView.setImageResource(R.drawable.sedang)
                binding.saranTextView.text = getString(R.string.saran_sedang)
            }
            KategoriKalori.BANYAK -> {
                actionBar?.title = getString(R.string.judul_banyak)
                binding.imageView.setImageResource(R.drawable.banyak)
                binding.saranTextView.text = getString(R.string.saran_banyak)
            }
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        updateUI(args.kategori)
    }
}