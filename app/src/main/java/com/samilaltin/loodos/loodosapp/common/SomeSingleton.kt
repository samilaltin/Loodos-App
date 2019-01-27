package com.samilaltin.loodos.loodosapp.common

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.support.design.widget.Snackbar
import android.view.View
import android.widget.Toast
import java.lang.RuntimeException
import android.app.Activity
import android.view.inputmethod.InputMethodManager
import android.support.v4.content.ContextCompat.getSystemService
import android.widget.EditText


/**
 * Created by saltin on 26.01.2019
 */
class SomeSingleton
private constructor() {
    private var context: Context? = null
    private var view: View? = null
    private var activity: Activity? = null

    fun init(context: Context, view: View, activity: Activity) {
        this.context = context
        this.view = view
        this.activity = activity
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

    fun hideKeyboard() {
        val imm = activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = activity!!.currentFocus
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun focusAndShowKeyboard(editText: EditText) {
        editText.requestFocus()
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm!!.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
    }


}

