package org.d3if4104.tulisan.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import org.d3if4104.tulisan.R
import org.d3if4104.tulisan.databinding.RecyclerviewTulisanBinding
import org.d3if4104.tulisan.db.Tulisan
import org.d3if4104.tulisan.convertLongToDateString


class TulisanAdapter (private val tulisan: List<Tulisan>) : RecyclerView.Adapter<TulisanAdapter.TulisanViewHolder>() {
    var listener: RecyclerViewOnClickListener? = null


    inner class TulisanViewHolder(
        val recyclerviewTulisanBinding: RecyclerviewTulisanBinding
    ) : RecyclerView.ViewHolder(recyclerviewTulisanBinding.root)


    override fun getItemCount() = tulisan.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TulisanViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.recyclerview_tulisan, parent, false)
    )

    override fun onBindViewHolder(holder: TulisanViewHolder, position: Int) {

        holder.recyclerviewTulisanBinding.tvDate.text = convertLongToDateString(tulisan[position].tanggal)
        holder.recyclerviewTulisanBinding.tvMessage.text = tulisan[position].message


        holder.recyclerviewTulisanBinding.listTulisan.setOnClickListener {
            listener?.onRecyclerViewItemClicked(it, tulisan[position])
        }
    }
}