package org.d3if1139.penghitungkalori.ui.hitung

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import org.d3if1139.penghitungkalori.R
import org.d3if1139.penghitungkalori.databinding.FragmentHitungBinding
import org.d3if1139.penghitungkalori.db.Kalori
import org.d3if1139.penghitungkalori.model.HasilKalori
import org.d3if1139.penghitungkalori.model.KategoriKalori

class HitungFragment : Fragment() {
    private lateinit var binding: FragmentHitungBinding
    private val viewModel: HitungViewModel by lazy{
        val db = Kalori.getInstance(requireContext())
        val factory = HitungViewModelFactory(db.dao)
        ViewModelProvider(this, factory)[HitungViewModel::class.java]
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHitungBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu, menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_histori -> {
                findNavController().navigate(R.id.action_hitungFragment_to_historiFragment)
                return true
            }
            R.id.menu_about -> {
                findNavController().navigate(
                    R.id.action_hitungFragment_to_aboutFragment
                )
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.button.setOnClickListener{ hitungKalori() }
        binding.saranButton.setOnClickListener { viewModel.mulaiNavigasi() }
        binding.shareButton.setOnClickListener { shareData() }
        binding.buttonReset.setOnClickListener{ reset() }
        viewModel.getHasilKalori().observe(requireActivity(), { showResult(it) })
        setupObserveres()
    }

    private fun setupObserveres() {
        viewModel.getNavigasi().observe(viewLifecycleOwner, {
            if(it == null) return@observe
            findNavController().navigate(HitungFragmentDirections.actionHitungFragmentToSaranFragment(it))
            viewModel.selesaiNavigasi()
        })
        viewModel.getHasilKalori().observe(requireActivity(), { showResult(it) })
    }


    private fun shareData(){
        val selectedId = binding.radioGroup.checkedRadioButtonId
        val gender = if (selectedId == R.id.priaRadioButton)
            getString(R.string.pria)
        else
            getString(R.string.wanita)
        val message = getString(R.string.bagikan_template,
            binding.beratBadanInp.text,
            binding.tinggiBadanInp.text,
            binding.usiaInp.text,
            gender,
            binding.kaloriTextView.text
        )
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain").putExtra(Intent.EXTRA_TEXT, message)
        if (shareIntent.resolveActivity(
                requireActivity().packageManager) != null){
            startActivity(shareIntent)
        }
    }

    private fun reset(){
        binding.beratBadanInp.text?.clear()
        binding.tinggiBadanInp.text?.clear()
        binding.usiaInp.text?.clear()
        binding.kaloriTextView.text = ""
        binding.kategoriTextView.text = ""
        binding.radioGroup.clearCheck()
        binding.buttonGroup.visibility = View.INVISIBLE
    }
    private fun hitungKalori(){
        val berat = binding.beratBadanInp.text.toString()
        if(TextUtils.isEmpty(berat)){
            Toast.makeText(context, R.string.berat_invalid, Toast.LENGTH_LONG).show()
            return
        }
        val tinggi = binding.tinggiBadanInp.text.toString()
        if(TextUtils.isEmpty(tinggi)){
            Toast.makeText(context, R.string.tinggi_invalid, Toast.LENGTH_LONG).show()
            return
        }
        val usia = binding.usiaInp.text.toString()
        if(TextUtils.isEmpty(usia)){
            Toast.makeText(context, R.string.usia_invalid, Toast.LENGTH_LONG).show()
            return
        }
        val selectedId = binding.radioGroup.checkedRadioButtonId
        viewModel.hitungKalori(
            berat.toDouble(),
            tinggi.toDouble(),
            selectedId == R.id.priaRadioButton,
            usia.toDouble()
        )
    }

    @SuppressLint("StringFormatMatches")
    private fun showResult(result: HasilKalori?){
        if (result == null) return
        binding.kaloriTextView.text = getString(R.string.kalori_x, result.kalori)
        binding.kategoriTextView.text = getString(R.string.kategori_x, getKategoriLabel(result.kategori))
        binding.buttonGroup.visibility = View.VISIBLE
    }

    private fun getKategoriLabel(kategori: KategoriKalori):String{
        val stringRes = when (kategori) {
            KategoriKalori.SEDIKIT -> R.string.sedikit
            KategoriKalori.BANYAK -> R.string.banyak
            KategoriKalori.SEDANG -> R.string.sedang
        }
        return getString(stringRes)
    }
}