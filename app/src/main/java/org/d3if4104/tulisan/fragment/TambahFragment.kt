package org.d3if4104.tulisan.fragment

import TulisanVMFactory
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import org.d3if4104.tulisan.MainActivity
import org.d3if4104.tulisan.R
import org.d3if4104.tulisan.databinding.FragmentTambahBinding
import org.d3if4104.tulisan.db.TulisanDB
import org.d3if4104.tulisan.vm.TulisanVM

class TambahFragment :Fragment(R.layout.fragment_tambah){
    private lateinit var binding:FragmentTambahBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        judul()
        setHasOptionsMenu(true)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tambah, container, false)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.button, menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val application = requireNotNull(this.activity).application
        val dataSource = TulisanDB.getInstance(application).TulisanDAO
        val viewModelFactory = TulisanVMFactory(dataSource, application)
        val tulisanViewModel =
            ViewModelProvider(this, viewModelFactory).get(TulisanVM::class.java)

        return when (item.itemId) {
            R.id.item_action -> {
                if (inputCheck(tulisanViewModel)) {

                    requireView().findNavController().popBackStack()
                    Toast.makeText(requireContext(), "Tulisanmu Berhasil Ditambahkan", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(requireContext(), "Tulisan gagal di tambah", Toast.LENGTH_SHORT)
                        .show()
                }
                return true
            }


            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun inputCheck(tulisanViewModel: TulisanVM): Boolean {

        return when {
            binding.etTulisan.text.trim().isEmpty() -> false
            else -> {
                doInsert(tulisanViewModel)
                true
            }
        }
    }


    private fun doInsert(tulisanViewModel: TulisanVM) {

        val message = binding.etTulisan.text.toString()

        tulisanViewModel.onClickInsert(message)
    }

    private fun judul() {
        val getActivity = activity!! as MainActivity
        getActivity.supportActionBar?.title = "Ayo mulailah menulis "
    }


}