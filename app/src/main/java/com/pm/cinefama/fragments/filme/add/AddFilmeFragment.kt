package com.pm.cinefama.fragments.filme.add

import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.pm.cinefama.R
import com.pm.cinefama.api.dto.FilmeDto
import com.pm.cinefama.api.requests.FilmeApi
import com.pm.cinefama.api.retrofit.ServiceBuilder
import com.pm.cinefama.utils.Utils.Companion.getToken
import com.pm.cinefama.utils.Utils.Companion.getUserIdInSession
import com.pm.cinefama.utils.Utils.Companion.hideKeyboard
import com.pm.cinefama.utils.Utils.Companion.somethingWentWrong
import com.pm.cinefama.utils.Utils.Companion.unauthorized
import kotlinx.android.synthetic.main.fragment_add_filme.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddFilmeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_filme, container, false)

        setHasOptionsMenu(true)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_menu_filme, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        hideKeyboard()

        if (item.itemId == R.id.menu_add_filme) {
            addFilme()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun addFilme() {
        if (TextUtils.isEmpty(add_filme_name.text.toString()) || TextUtils.isEmpty(
                add_filme_directors.text.toString()
            )
        ) {
            Toast.makeText(
                requireContext(),
                getString(R.string.fill_title_and_description),
                Toast.LENGTH_LONG
            )
                .show()
        } else {
            llProgressBar.bringToFront()
            llProgressBar.visibility = View.VISIBLE

            val request = ServiceBuilder.buildService(FilmeApi::class.java)
            val call = request.createFilme(
                token = "Bearer ${getToken()}",
                users_id = getUserIdInSession(),
                name = add_filme_name.text.toString(),
                duration = Integer.parseInt(add_filme_duration.text.toString()),
                directors = add_filme_directors.text.toString(),
                actors = add_filme_actors.text.toString(),
                genre = add_filme_genre.text.toString(),
                release_date = add_filme_release_date.text.toString(),
                legal_age = Integer.parseInt(add_filme_legal_age.text.toString()),
                theater = Integer.parseInt(add_filme_theater.text.toString()),
                schedule = add_filme_schedule.text.toString(),
            )

            call.enqueue(object : Callback<FilmeDto> {
                override fun onResponse(call: Call<FilmeDto>, response: Response<FilmeDto>) {
                    llProgressBar.visibility = View.GONE

                    if (response.isSuccessful) {
                        val filme: FilmeDto = response.body()!!

                        if (filme.status == "OK") {
                            Toast.makeText(
                                requireContext(),
                                getString(R.string.successfull_added_new_filme),
                                Toast.LENGTH_LONG
                            ).show()
                            findNavController().navigate(R.id.action_addFilmeFragment_to_filmesListFragment)
                        } else {
                            Toast.makeText(
                                requireContext(), getString(
                                    resources.getIdentifier(
                                        filme.message, "string",
                                        context?.packageName
                                    )
                                ), Toast.LENGTH_LONG
                            ).show()
                        }
                    } else {
                        if (response.code() == 401) {
                            unauthorized(navigatonHandlder = {
                                findNavController().navigate(R.id.action_addFilmeFragment_to_userLoginFragment)
                            })
                        } else {
                            somethingWentWrong()
                        }
                    }
                }

                override fun onFailure(call: Call<FilmeDto>, t: Throwable) {
                    llProgressBar.visibility = View.GONE
                    somethingWentWrong()
                }
            })
        }
    }
}