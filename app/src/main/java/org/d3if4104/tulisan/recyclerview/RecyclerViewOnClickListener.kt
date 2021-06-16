package org.d3if4104.tulisan.recyclerview

import android.view.View
import org.d3if4104.tulisan.db.Tulisan

interface RecyclerViewOnClickListener {
    fun onRecyclerViewItemClicked(view: View, diary:Tulisan)
}