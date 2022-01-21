package com.pm.cinefama.fragments.update_filme

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.pm.cinefama.R
import com.pm.cinefama.api.dto.FilmeDto
import com.pm.cinefama.api.requests.FilmeApi
import com.pm.cinefama.api.retrofit.ServiceBuilder
import com.pm.cinefama.utils.Utils.Companion.getToken
import com.pm.cinefama.utils.Utils.Companion.hideKeyboard
import com.pm.cinefama.utils.Utils.Companion.somethingWentWrong
import com.pm.cinefama.utils.Utils.Companion.unauthorized
import kotlinx.android.synthetic.main.fragment_update_filme.*
import kotlinx.android.synthetic.main.fragment_update_filme.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateFilmeFragment : Fragment() {
    private val args by navArgs<UpdateFilmeFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update_filme, container, false)

        setHasOptionsMenu(true)

        view.update_filme_name.setText(args.currentFilme.name)
        view.update_filme_duration.setText(args.currentFilme.duration)
        view.update_filme_directors.setText(args.currentFilme.directors)
        view.update_filme_actors.setText(args.currentFilme.actors)
        view.update_filme_genre.setText(args.currentFilme.genre)
        view.update_filme_release_date.setText(args.currentFilme.release_date)
        view.update_filme_legal_age.setText(args.currentFilme.legal_age)
        view.update_filme_theater.setText(args.currentFilme.theater)
        view.update_filme_schedule.setText(args.currentFilme.schedule)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_delete_menu_filme, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        hideKeyboard()

        if (item.itemId == R.id.menu_delete_filme) {
            deleteFilme()
        }

        if (item.itemId == R.id.menu_update_filme) {
            updateFilme()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun updateFilme() {
        if (TextUtils.isEmpty(update_filme_name.text.toString()) || TextUtils.isEmpty(
                update_filme_genre.text.toString()
            )
        ) {
            Toast.makeText(
                requireContext(),
                getString(R.string.fill_title_and_description),
                Toast.LENGTH_LONG
            )
                .show()
        } else {
            val request = ServiceBuilder.buildService(FilmeApi::class.java)
            val call = request.updateFilme(
                token = "Bearer ${getToken()}",
                id = args.currentFilme.id,
                name = update_filme_name.text.toString(),
                duration = Integer.parseInt(update_filme_duration.text.toString()),
                directors = update_filme_directors.text.toString(),
                actors = update_filme_actors.text.toString(),
                genre = update_filme_genre.text.toString(),
                release_date = update_filme_release_date.text.toString(),
                legal_age = Integer.parseInt(update_filme_legal_age.text.toString()),
                theater = Integer.parseInt(update_filme_theater.text.toString()),
                schedule = update_filme_schedule.text.toString(),
            )

            call.enqueue(object : Callback<FilmeDto> {
                override fun onResponse(call: Call<FilmeDto>, response: Response<FilmeDto>) {
                    if (response.isSuccessful) {
                        val report: FilmeDto = response.body()!!

                        if (report.status == "OK") {
                            Toast.makeText(
                                requireContext(),
                                getString(R.string.successfull_updated_filme),
                                Toast.LENGTH_LONG
                            ).show()
                            findNavController().navigate(R.id.action_updateFilmeFragment_to_filmesListFragment)
                        } else {
                            Toast.makeText(
                                requireContext(), getString(
                                    resources.getIdentifier(
                                        report.message, "string",
                                        context?.packageName
                                    )
                                ), Toast.LENGTH_LONG
                            ).show()
                        }
                    } else {
                        if (response.code() == 401) {
                            unauthorized(navigatonHandlder = {
                                findNavController().navigate(R.id.action_updateFilmeFragment_to_userLoginFragment)
                            })
                        } else {
                            somethingWentWrong()
                        }
                    }
                }

                override fun onFailure(call: Call<FilmeDto>, t: Throwable) {
                    somethingWentWrong()
                }
            })
        }
    }

    private fun deleteFilme() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton(getString(R.string.yes)) { _, _ ->

            val request = ServiceBuilder.buildService(FilmeApi::class.java)
            val call = request.deleteFilme(
                token = "Bearer ${getToken()}",
                id = args.currentFilme.id
            )

            call.enqueue(object : Callback<FilmeDto> {
                override fun onResponse(call: Call<FilmeDto>, response: Response<FilmeDto>) {
                    if (response.isSuccessful) {
                        val report: FilmeDto = response.body()!!

                        if(report.status == "OK") {
                            Toast.makeText(
                                requireContext(),
                                getString(R.string.successfull_deleted_filme),
                                Toast.LENGTH_LONG
                            ).show()
                            findNavController().navigate(R.id.action_updateFilmeFragment_to_filmesListFragment)
                        }
                        else {
                            Toast.makeText(
                                requireContext(), getString(
                                    resources.getIdentifier(
                                        report.message, "string",
                                        context?.packageName
                                    )
                                ), Toast.LENGTH_LONG
                            ).show()
                        }

                    } else {

                        if(response.code() == 401){
                            unauthorized(navigatonHandlder = {
                                findNavController().navigate(R.id.action_updateFilmeFragment_to_userLoginFragment)
                            })
                        }
                        else {
                            somethingWentWrong()
                        }
                    }
                }

                override fun onFailure(call: Call<FilmeDto>, t: Throwable) {
                    somethingWentWrong()
                }
            })
        }
        builder.setNegativeButton(getString(R.string.no)) { _, _ -> }
        builder.setTitle(getString(R.string.delete_filme))
        builder.setMessage(getString(R.string.question_delete_filme))
        builder.create().show()
    }
}