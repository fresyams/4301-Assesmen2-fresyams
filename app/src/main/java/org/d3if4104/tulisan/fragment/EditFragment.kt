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
import org.d3if4104.tulisan.databinding.FragmentEditBinding
import org.d3if4104.tulisan.db.Tulisan
import org.d3if4104.tulisan.db.TulisanDB
import org.d3if4104.tulisan.vm.TulisanVM

class EditFragment : Fragment() {
    private lateinit var binding: FragmentEditBinding

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        judul()
        setHasOptionsMenu(true)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null) {
            val message = arguments!!.getString("message")
            binding.etTulisanUpdate.setText(message)
        }

    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.button, menu)
        inflater.inflate(R.menu.menu, menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val application = requireNotNull(this.activity).application
        val dataSource = TulisanDB.getInstance(application).TulisanDAO
        val viewModelFactory = TulisanVMFactory(dataSource, application)
        val diaryViewModel = ViewModelProvider(this, viewModelFactory).get(TulisanVM::class.java)

        return when (item.itemId) {
            R.id.item_action -> {
                if (inputCheck(arguments!!.getLong("id"), diaryViewModel)) {
                    requireView().findNavController().popBackStack()
                    Toast.makeText(requireContext(), "Tulisanmu Berhasil Diupdate", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Tulisan gagal diupdate", Toast.LENGTH_SHORT).show()
                }
                return true
            }

            R.id.hapus_tulisan -> {
                diaryViewModel.onClickDelete(arguments!!.getLong("id"))
                requireView().findNavController().popBackStack()
                Toast.makeText(requireContext(),
                        "Tulisanmu berhasil dihapus!", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.about ->{
                requireView().findNavController().navigate(R.id.action_editFragment_to_aboutFragment)
                return true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun inputCheck(id: Long, diaryViewModel: TulisanVM): Boolean {
        return when {
            binding.etTulisanUpdate.text.trim().isEmpty() -> false

            else -> {
                doUpdate(id, diaryViewModel)
                true
            }
        }
    }

    private fun doUpdate(id: Long, diaryViewModel: TulisanVM) {
        val message = binding.etTulisanUpdate.text.toString()
        val date = System.currentTimeMillis()

        val diary = Tulisan(id, message, date)
        diaryViewModel.onClickUpdate(diary)
    }

    private fun judul() {
        val getActivity = activity!! as MainActivity
        getActivity.supportActionBar?.title = "Ubah Tulisanmu "
    }


}
