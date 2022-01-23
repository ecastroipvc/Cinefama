package com.pm.cinefama.fragments.filme_list

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.pm.cinefama.Maps
import com.pm.cinefama.R
import com.pm.cinefama.api.models.Filme
import com.pm.cinefama.api.requests.FilmeApi
import com.pm.cinefama.api.retrofit.ServiceBuilder
import com.pm.cinefama.utils.Utils.Companion.getToken
import com.pm.cinefama.utils.Utils.Companion.getUserIdInSession
import com.pm.cinefama.utils.Utils.Companion.hideKeyboard
import com.pm.cinefama.utils.Utils.Companion.somethingWentWrong
import com.pm.cinefama.utils.Utils.Companion.unauthorized
import kotlinx.android.synthetic.main.fragment_filmes_list.*
import kotlinx.android.synthetic.main.fragment_filmes_list.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FilmesListFragment : Fragment(){
    private  var  _view : View? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_filmes_list, container, false)
        _view = view

        setHasOptionsMenu(true)

        getAndSetData(view)

        view.btn_add_new_filme_from_filmes_list.setOnClickListener() {
            findNavController().navigate(R.id.action_reportsListFragment_to_addReportFragment)
        }

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.filmes_list_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        hideKeyboard()

        if (item.itemId == R.id.user_logout) {
            logout()
        }

        if(item.itemId == R.id.maps){
            val intent = Intent(requireContext(), Maps::class.java)
            startActivity(intent)
        }

        if(item.itemId == R.id.filmes_list_refresh){
            _view?.let { getAndSetData(it) }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun getAndSetData(view: View) {

        view.llProgressBarList.bringToFront()
        view.llProgressBarList.visibility = View.VISIBLE


        val adapter = FilmesListAdapter(getUserIdInSession())

        val recyclerView = view.recyclerview_filmes_list
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val request = ServiceBuilder.buildService(FilmeApi::class.java)
        val call = request.getFilmes(token = "Bearer ${getToken()}")

        call.enqueue(object : Callback<List<Filme>> {
            override fun onResponse(call: Call<List<Filme>>, response: Response<List<Filme>>) {

                llProgressBarList.visibility = View.GONE

                if (response.isSuccessful) {
                    val filmes: List<Filme> = response.body()!!
                    adapter.setData(filmes)
                } else {
                    if (response.code() == 401) {
                        unauthorized(navigatonHandlder = {
                            findNavController().navigate(R.id.action_filmesListFragment_to_userLoginFragment)
                        })
                    } else {
                        somethingWentWrong()
                    }
                }
            }

            override fun onFailure(call: Call<List<Filme>>, t: Throwable) {
                llProgressBarList.visibility = View.GONE
                somethingWentWrong()
            }
        })
    }

    private fun logout() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton(getString(R.string.yes)) { _, _ ->
            val preferences = requireActivity().getSharedPreferences("pref", Context.MODE_PRIVATE)
            preferences.edit().putString("token", null).apply()
            findNavController().navigate(R.id.action_filmesListFragment_to_userLoginFragment)
        }
        builder.setNegativeButton(getString(R.string.no)) { _, _ -> }
        builder.setTitle(getString(R.string.logout))
        builder.setMessage(getString((R.string.logout_question)))
        builder.create().show()
    }
}