package com.samilaltin.loodos.loodosapp

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.FrameLayout
import com.samilaltin.loodos.loodosapp.common.SomeSingleton
import com.samilaltin.loodos.loodosapp.common.Utility
import com.samilaltin.loodos.loodosapp.fragments.RemoteConfig

class MainActivity : TemplateActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startActivityInitProcess()
    }

    override fun onCreateViewInstances() {
        super.onCreateViewInstances()
        SomeSingleton.instance!!.init(this, this.findViewById(android.R.id.content))
        val contentFrame = findViewById<FrameLayout>(R.id.content_frame)
    }

    override fun onBindViewModel() {
        super.onBindViewModel()
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Utility.hasNetwork(connectivityManager)!!) {
            Utility.loadFragment(supportFragmentManager, RemoteConfig(), R.id.content_frame)
        } else {
            val connectionWarning = getString(R.string.check_internet_connection_warning)
            SomeSingleton.instance!!.showSnackBarOrToast(connectionWarning)
        }
    }

}

