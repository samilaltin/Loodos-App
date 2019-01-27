package com.samilaltin.loodos.loodosapp

import android.os.Bundle
import android.util.Log
import com.samilaltin.loodos.loodosapp.common.GlobalParameters
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
        SomeSingleton.instance!!.init(this, this.findViewById(android.R.id.content),this)
    }

    override fun onBindViewModel() {
        super.onBindViewModel()
        if (Utility.hasNetwork(SomeSingleton.instance?.getConnectivityManager())!!) {
            Log.d(GlobalParameters.TAG_LOG, getString(R.string.internet_connection_success))
            Utility.loadFragment(supportFragmentManager, RemoteConfig(), R.id.content_frame)
        } else {
            val connectionWarning = getString(R.string.check_internet_connection_warning)
            SomeSingleton.instance!!.showSnackBarOrToast(connectionWarning)
            Log.d(GlobalParameters.TAG_LOG, getString(R.string.internet_connection_failed))
        }
    }

}


