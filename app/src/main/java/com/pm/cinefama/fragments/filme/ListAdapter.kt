package com.pm.cinefama.fragments.filme

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.pm.cinefama.R
import com.pm.cinefama.data.entities.Filme
import kotlinx.android.synthetic.main.custom_row.view.*

class ListAdapter: RecyclerView.Adapter<ListAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {}

    private var filmeList = emptyList<Filme>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListAdapter.MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_row, parent, false))
    }

    override fun onBindViewHolder(holder: ListAdapter.MyViewHolder, position: Int) {
        val currentItem = filmeList[position]
        holder.itemView.lst_filmeId.text = currentItem.id.toString()
        holder.itemView.lst_filmeName.text = currentItem.name

        if(position%2 == 0){
            holder.itemView.rowLayout.setBackgroundColor(Color.parseColor("#d6d4e0"))
        }
        else {
            holder.itemView.rowLayout.setBackgroundColor(Color.parseColor("#b8a9c9"))
        }

        holder.itemView.rowLayout.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToUpdateFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return filmeList.size
    }

    fun setData(filmes: List<Filme>){
        this.filmeList = filmes
        notifyDataSetChanged()
    }
}