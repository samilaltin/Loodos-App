package com.samilaltin.loodos.loodosapp.common

import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager


/**
 * Created by saltin on 26.01.2019
 */
object Utility {

    fun loadFragment(manager: FragmentManager, fragment: Fragment, frameId: Int) {
        val transaction = manager.beginTransaction()
        transaction.replace(frameId, fragment)
        try {
            transaction.commit()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun hasNetwork(connectivityManager: ConnectivityManager?): Boolean? {
        var isConnected: Boolean? = false
        val activeNetwork: NetworkInfo? = connectivityManager?.activeNetworkInfo
        if (activeNetwork != null && activeNetwork.isConnected)
            isConnected = true
        return isConnected
    }


}