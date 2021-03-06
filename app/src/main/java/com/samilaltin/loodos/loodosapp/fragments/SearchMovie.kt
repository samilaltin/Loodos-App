package com.samilaltin.loodos.loodosapp.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText

import com.samilaltin.loodos.loodosapp.R
import com.samilaltin.loodos.loodosapp.adapters.MovieListAdapter
import com.samilaltin.loodos.loodosapp.common.GlobalParameters
import com.samilaltin.loodos.loodosapp.common.SomeSingleton
import com.samilaltin.loodos.loodosapp.common.Utility
import com.samilaltin.loodos.loodosapp.pojo.ServiceResponse
import com.samilaltin.loodos.loodosapp.services.APIClient
import com.samilaltin.loodos.loodosapp.services.APIInterface
import com.samilaltin.loodos.loodosapp.services.CallBackInterface
import kotlinx.android.synthetic.main.fragment_search_movie.*
import retrofit2.Call
import retrofit2.Response
import java.util.ArrayList

/**
 * A simple [Fragment] subclass.
 *
 */
class SearchMovie : Fragment() {

    private var apiInterface: APIInterface? = null
    private var rootView: View? = null
    private lateinit var movieListAdapter: MovieListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_search_movie, container, false)
        init()
        return rootView
    }

    private fun init() {
        SomeSingleton.instance?.focusAndShowKeyboard(rootView?.findViewById(R.id.txtSearch))
        rootView?.findViewById<Button>(R.id.btnSearch)?.setOnClickListener { searchMovie() }
    }


    private fun searchMovie() {
        if (Utility.hasNetwork(SomeSingleton.instance?.getConnectivityManager())!!) {
            request()
        } else {
            SomeSingleton.instance?.showSnackBarOrToast(getString(R.string.check_internet_connection_warning))
        }

    }

    private fun request() {
        SomeSingleton.instance?.hideKeyboard()
        val movieTitle = rootView?.findViewById<EditText>(R.id.txtSearch)?.text.toString()
        apiInterface = APIClient.client.create(APIInterface::class.java)
        if (!movieTitle.isEmpty()) {
            progressBar.visibility = View.VISIBLE
            val call = apiInterface?.searchMovie(GlobalParameters.baseURL + movieTitle + GlobalParameters.APIKey)
            call?.enqueue(object : CallBackInterface<ServiceResponse> {
                override fun onResponse(call: Call<ServiceResponse>, response: Response<ServiceResponse>) {
                    response(response)
                }
            })
        } else {
            txtSearch.error = getString(R.string.not_be_empty)
        }

    }

    private fun response(response: Response<ServiceResponse>) {

        if (responseControl(response)) {
            setMovieDatas(response.body()!!)
            movie_list.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
        }

    }

    private fun setMovieDatas(serviceResponse: ServiceResponse) {
        val movieList = ArrayList<ServiceResponse>()
        movieList.add(serviceResponse)
        setMovieListAdapter(movieList)
    }

    private fun setMovieListAdapter(list: ArrayList<ServiceResponse>) {
        movie_list.layoutManager = LinearLayoutManager(activity)
        movieListAdapter = MovieListAdapter(list)
        movie_list.adapter = movieListAdapter
        movie_list.visibility = View.VISIBLE
    }

    private fun responseControl(response: Response<ServiceResponse>): Boolean {

        val controls = booleanArrayOf(
            response.body() == null,
            !response.isSuccessful,
            !response.body()?.response.equals("True")
        )

        val messages = arrayOf(
            getString(R.string.please_try_again_later),
            response.body()!!.error!!,
            response.body()!!.error!!
        )

        var position = 0
        for (bool in controls) {
            if (bool) {
                SomeSingleton.instance!!.showSnackBarOrToast(messages[position])
                movie_list.visibility = View.GONE
                progressBar.visibility = View.GONE
                return false
            }
            position += 1
        }

        return true
    }


}
