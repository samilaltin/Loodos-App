package com.samilaltin.loodos.loodosapp

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.app.AppCompatActivity
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

/**
 * Created by saltin on 26.01.2019
 */

@SuppressLint("Registered")
open class TemplateActivity : AppCompatActivity() {

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    protected fun startActivityInitProcess() {
        onCreateViewInstances()
        onBindViewModel()
        onCreateViewEvent()
        onActivityInitFinished()
    }

    protected open fun onCreateViewInstances() {
    }

    protected open fun onBindViewModel() {
    }

    protected fun onCreateViewEvent() {
    }

    protected open fun onActivityInitFinished() {
    }
}