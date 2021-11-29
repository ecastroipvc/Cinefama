package com.pm.cinefama.fragments.filme

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.pm.cinefama.R
import com.pm.cinefama.data.viewmodel.FilmeViewModel
import kotlinx.android.synthetic.main.fragment_list.view.*

class ListFragment : Fragment() {

    private  lateinit var mFilmeViewModel: FilmeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        // Recyclerview
        val adapter = ListAdapter()
        val recyclerView = view.recyclerview
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // UserViewModel
        mFilmeViewModel = ViewModelProvider(this).get(FilmeViewModel::class.java)
        mFilmeViewModel.readAllFilmes.observe(viewLifecycleOwner, Observer { filme ->
            adapter.setData(filme)
        })

        view.btnAddFilmeFromList.setOnClickListener(){
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        return view;
    }
}