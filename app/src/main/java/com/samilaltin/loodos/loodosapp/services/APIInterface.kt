package com.samilaltin.loodos.loodosapp.services

import com.samilaltin.loodos.loodosapp.pojo.ServiceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * Created by saltin on 26.01.2019
 */
interface APIInterface {
    @GET
    fun searchMovie(
        @Url url: String
    ): Call<ServiceResponse>
}