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
        holder.itemView.lst_filmeDirectors.text = currentItem.directors
        holder.itemView.lst_filmeActors.text = currentItem.actors
        holder.itemView.lst_filmeGenre.text = currentItem.genre
        holder.itemView.lst_filmeRelease_date.text = currentItem.release_date
        holder.itemView.lst_filmeLegal_age.text = currentItem.legal_age.toString()
        holder.itemView.lst_filmeDuration.text = currentItem.duration.toString()
        holder.itemView.lst_filmeRoom.text = currentItem.theater.toString()
        holder.itemView.lst_filmeSchedule.text = currentItem.schedule

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