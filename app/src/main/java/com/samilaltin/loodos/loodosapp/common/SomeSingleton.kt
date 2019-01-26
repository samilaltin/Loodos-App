package com.samilaltin.loodos.loodosapp.common

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.support.design.widget.Snackbar
import android.view.View
import android.widget.Toast
import java.lang.RuntimeException

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
}

