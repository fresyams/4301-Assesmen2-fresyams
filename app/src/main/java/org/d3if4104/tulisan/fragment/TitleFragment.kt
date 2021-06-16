package org.d3if4104.tulisan.fragment

import TulisanVMFactory
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.d3if4104.tulisan.MainActivity
import org.d3if4104.tulisan.R
import org.d3if4104.tulisan.databinding.FragmentTitleBinding
import org.d3if4104.tulisan.db.Tulisan
import org.d3if4104.tulisan.db.TulisanDB
import org.d3if4104.tulisan.recyclerview.RecyclerViewOnClickListener
import org.d3if4104.tulisan.recyclerview.TulisanAdapter
import org.d3if4104.tulisan.vm.TulisanVM

class TitleFragment : Fragment() ,
        RecyclerViewOnClickListener {

    private lateinit var binding: FragmentTitleBinding

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        judul()
        setHasOptionsMenu(true)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_title, container, false)

        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val application = requireNotNull(this.activity).application
        val dataSource = TulisanDB.getInstance(application).TulisanDAO
        val viewModelFactory = TulisanVMFactory(dataSource, application)
        val diaryViewModel = ViewModelProvider(this, viewModelFactory).get(TulisanVM::class.java)
        diaryViewModel.tulisan.observe(viewLifecycleOwner, Observer {
            val adapter = TulisanAdapter(it)
            val recyclerView = binding.rvTulisan
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(this.requireContext())
            adapter.listener = this
        })

        binding.fabTulisTulisan.setOnClickListener {
            it.findNavController().navigate(R.id.action_titleFragment_to_tambahFragment)
        }


    }


    override fun onRecyclerViewItemClicked(view: View, diary: Tulisan) {
        when(view.id) {
            R.id.list_tulisan -> {
                val bundle = Bundle()
                bundle.putLong("id", diary.id)
                bundle.putString("message", diary.message)
                view.findNavController().navigate(R.id.action_titleFragment_to_editFragment, bundle)
            }
        }

    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val application = requireNotNull(this.activity).application
        val dataSource = TulisanDB.getInstance(application).TulisanDAO
        val viewModelFactory = TulisanVMFactory(dataSource, application)
        val diaryViewModel = ViewModelProvider(this, viewModelFactory).get(TulisanVM::class.java)

        return when (item.itemId) {
            R.id.hapus_tulisan -> {
                diaryViewModel.onClickClear()
                Toast.makeText(requireContext(),
                        "Tulisan Berhasil Dihapus", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.about -> {
                requireView().findNavController().navigate(R.id.action_titleFragment_to_aboutFragment)
                return true
            }

            else -> super.onOptionsItemSelected(item)
        }

    }

    private fun judul() {
        val getActivity = activity!! as MainActivity
        getActivity.supportActionBar?.title = "Tulisan "
    }

}
