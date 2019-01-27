package com.samilaltin.loodos.loodosapp.services

import com.samilaltin.loodos.loodosapp.common.SomeSingleton
import retrofit2.Call
import retrofit2.Response

/**
 * Created by saltin on 26.01.2019
 */
interface ServiceCallBack {

    interface ServiceCallBack<T> : retrofit2.Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) {

        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            SomeSingleton.instance?.showSnackBarOrToast("Oops.. Something went wrong")
            call.cancel()
        }
    }
}