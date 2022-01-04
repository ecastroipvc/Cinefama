package com.pm.cinefama.fragments.filme.update

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.pm.cinefama.R
import com.pm.cinefama.data.entities.Filme
import com.pm.cinefama.data.viewmodel.FilmeViewModel
import com.pm.cinefama.utils.Utils.Companion.hideKeyboard
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*

class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()
    private lateinit var mFilmeViewModel: FilmeViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update, container, false)

        mFilmeViewModel = ViewModelProvider(this).get(FilmeViewModel::class.java)

        view.updateFilmeName.setText(args.currentFilme.name)

        setHasOptionsMenu(true)

        return view;
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_update_delete_filme, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        hideKeyboard()

        if (item.itemId == R.id.menu_update) {
            updateFilme()
        }

        if (item.itemId == R.id.menu_delete) {
            deleteFilme()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun updateFilme() {
        if (!isValid()) {
            return Toast.makeText(
                    requireContext(),
                    getString(R.string.empty_filme_name),
                    Toast.LENGTH_LONG
            ).show()
        }
        val filme = Filme(args.currentFilme.id,
            updateFilmeName.text.toString(),
            Integer.parseInt(updateFilmeDuration.toString()),
            updateFilmeDirectors.text.toString(),
            updateFilmeActors.text.toString(),
            updateFilmeGenre.text.toString(),
            updateFilmeRelease_date.toString(),
            Integer.parseInt(updateFilmeLegal_age.toString()),
            Integer.parseInt(updateFilmeTheater.toString()),
            updateFilmeSchedule.text.toString())

        mFilmeViewModel.updateFilme(filme)

        Toast.makeText(
                requireContext(),
                getString(R.string.filme_successfully_updated),
                Toast.LENGTH_LONG
        ).show()
        findNavController().navigate(R.id.action_updateFragment_to_listFragment)
    }

    private fun deleteFilme() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton(getString(R.string.yes)) { _, _ ->
            mFilmeViewModel.deleteFilme(args.currentFilme)
            Toast.makeText(
                    requireContext(),
                    getString(R.string.filme_successfully_deleted),
                    Toast.LENGTH_SHORT
            ).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton(getString(R.string.no)) { _, _ -> }
        builder.setTitle(getString(R.string.delete))
        builder.setMessage(getString(R.string.question_delete))
        builder.create().show()
    }

    private fun isValid(): Boolean {
        return !TextUtils.isEmpty(updateFilmeName.text.toString())
    }
}