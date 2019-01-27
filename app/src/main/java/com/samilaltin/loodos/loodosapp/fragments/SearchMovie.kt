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
        rootView!!.findViewById<Button>(R.id.btnSearch).setOnClickListener { searchMovie() }
    }

    private fun searchMovie() {
        val movieTitle = rootView!!.findViewById<EditText>(R.id.txtSearch).text.toString()
        apiInterface = APIClient.client.create(APIInterface::class.java)
        val call = apiInterface!!.searchMovie(GlobalParameters.baseURL + movieTitle + GlobalParameters.APIKey)
        call.enqueue(object : CallBackInterface<ServiceResponse> {
            override fun onResponse(call: Call<ServiceResponse>, response: Response<ServiceResponse>) {
                if (response.body() != null) {
                    if (response.isSuccessful) {
                        setDatas(response.body()!!)
                    } else {
                        SomeSingleton.instance!!.showSnackBarOrToast(response.body()!!.error!!)
                    }
                } else {
//                    RequestSingleton.getInstance().showToast("Beklenmedik Bir Hata Oluştu.")
                }
            }
        })
    }

    private fun setDatas(serviceResponse: ServiceResponse) {
        val movieList = ArrayList<ServiceResponse>()
        movieList.add(serviceResponse)
        setAdapter(movieList)
    }

    private fun setAdapter(list: ArrayList<ServiceResponse>) {
        movie_list.layoutManager = LinearLayoutManager(activity)
        movieListAdapter = MovieListAdapter(list)
        movie_list.adapter = movieListAdapter
    }


}
