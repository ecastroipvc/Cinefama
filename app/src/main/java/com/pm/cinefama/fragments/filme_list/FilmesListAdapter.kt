package com.pm.cinefama.fragments.filme_list

import android.content.Context
import android.graphics.Color
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.pm.cinefama.R
import com.pm.cinefama.api.models.Filme
import kotlinx.android.synthetic.main.custom_row.view.*
import kotlinx.android.synthetic.main.custom_row_filmes_list.view.*

class FilmesListAdapter(userIdInSession: String?) : RecyclerView.Adapter<FilmesListAdapter.MyViewHolder>() {
    private var filmesList = emptyList<Filme>()
    private  val _userIdInSession = userIdInSession
    private  var  _ctx : Context? = null

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        _ctx = parent.context

        return FilmesListAdapter.MyViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.custom_row_filmes_list,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = filmesList[position]
        holder.itemView.filmes_list_name.text = currentItem.name
        holder.itemView.filmes_list_duration.text = currentItem.duration.toString()
        holder.itemView.filmes_list_directors.text = currentItem.directors
        holder.itemView.filmes_list_actors.text = currentItem.actors
        holder.itemView.filmes_list_genre.text = currentItem.genre
        holder.itemView.filmes_list_release_date.text = currentItem.release_date
        holder.itemView.filmes_list_legal_age.text = currentItem.legal_age.toString()
        holder.itemView.filmes_list_theater.text = currentItem.theater.toString()
        holder.itemView.filmes_list_schedule.text = currentItem.schedule

        if(position%2 == 0){
            holder.itemView.setBackgroundColor(Color.parseColor("#f2f2f2"))
        }
        else {
            holder.itemView.setBackgroundColor(Color.parseColor("#e6e6e6"))
        }

        holder.itemView.rowLayout_filmes_list.setOnClickListener {
            if(_userIdInSession == currentItem.users_id.toString()){
                val action =
                    FilmesListFragmentDirections.actionFilmesListFragmentToUpdateFilmeFragment(
                        currentItem
                    )
                holder.itemView.findNavController().navigate(action)
            }
            else {
                Toast.makeText(_ctx,R.string.only_edit_your_filmes, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun getItemCount(): Int {
        return filmesList.size
    }

    fun setData(filmesList: List<Filme>){
        this.filmesList = filmesList
        notifyDataSetChanged()
    }
}