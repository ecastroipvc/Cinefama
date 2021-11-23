package com.pm.cinefama.fragments.filme

import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.pm.cinefama.R
import com.pm.cinefama.data.entities.Filme
import com.pm.cinefama.data.viewmodel.FilmeViewModel
import com.pm.cinefama.utils.Utils.Companion.hideKeyboard
import kotlinx.android.synthetic.main.fragment_add.*

class AddFragment : Fragment() {

    private lateinit var mFilmeViewModel: FilmeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        setHasOptionsMenu(true)

        mFilmeViewModel = ViewModelProvider(this).get(FilmeViewModel::class.java)

        return view;
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_add_filme, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        hideKeyboard()

        if (item.itemId == R.id.menu_add) {
            addFilme()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun addFilme() {
        if (!isValid()) {
            return Toast.makeText(
                requireContext(),
                getString(R.string.empty_filme_name),
                Toast.LENGTH_LONG
            ).show()
        }

        val filme = Filme(0, filmeName.text.toString())

        mFilmeViewModel.addFilme(filme)

        Toast.makeText(
            requireContext(),
            getString(R.string.filme_successfully_added),
            Toast.LENGTH_LONG
        ).show()

        findNavController().navigate(R.id.action_addFragment_to_listFragment)
    }

    private fun isValid() : Boolean {
        return !TextUtils.isEmpty(filmeName.text.toString())
    }
}