package com.samilaltin.loodos.loodosapp.common

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.support.design.widget.Snackbar
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import com.samilaltin.loodos.loodosapp.R
import com.samilaltin.loodos.loodosapp.adapters.MovieListAdapter
import com.samilaltin.loodos.loodosapp.fragments.SearchMovie
import com.samilaltin.loodos.loodosapp.pojo.ServiceResponse
import retrofit2.Response
import java.lang.RuntimeException
import java.util.ArrayList

/**
 * Created by saltin on 26.01.2019
 */
class SomeSingleton
private constructor() {
    private var context: Context? = null
    private var view: View? = null

    fun init(context: Context, view: View) {
        this.context = context
        this.view = view
    }

    init {
        if (ourInstance != null) {
            val msg = "Use instance"
            throw RuntimeException(msg)
        }
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var ourInstance: SomeSingleton? = null
        val instance: SomeSingleton?
            get() {
                if (ourInstance == null) {

                    synchronized(SomeSingleton::class.java) {
                        if (ourInstance == null)
                            ourInstance = SomeSingleton()
                    }
                }
                return ourInstance
            }
    }

    private fun showToast(text: CharSequence) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show()
    }

    private fun showSnackBar(text: CharSequence) {
        view?.let { Snackbar.make(it, text, Snackbar.LENGTH_LONG).show() }
    }

    fun showSnackBarOrToast(charSequence: CharSequence) {
        val androidVersion = android.os.Build.VERSION.SDK_INT
        if (androidVersion > 21) {
            showSnackBar(charSequence)
        } else {
            showToast(charSequence)
        }
    }

    fun getConnectivityManager(): ConnectivityManager {
        return context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }


}

