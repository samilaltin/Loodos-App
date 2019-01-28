package com.samilaltin.loodos.loodosapp.fragments

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.samilaltin.loodos.loodosapp.BuildConfig

import com.samilaltin.loodos.loodosapp.R
import com.samilaltin.loodos.loodosapp.common.GlobalParameters
import com.samilaltin.loodos.loodosapp.common.SomeSingleton
import com.samilaltin.loodos.loodosapp.common.Utility
import kotlinx.android.synthetic.main.fragment_remote_config.*

class RemoteConfig : Fragment() {

    private val isHandlerStopped = false
    private lateinit var mFirebaseRemoteConfig: FirebaseRemoteConfig
    val handler = Handler()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_remote_config, container, false)
        setRemoteConfig()
        return rootView
    }


    private fun setRemoteConfig() {
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
        val configSettings = FirebaseRemoteConfigSettings.Builder()
            .setDeveloperModeEnabled(BuildConfig.DEBUG).build()
        mFirebaseRemoteConfig.setConfigSettings(configSettings)
        mFirebaseRemoteConfig.setDefaults(R.xml.remote_config)
        FirebaseRemoteConfig.getInstance().setConfigSettings(configSettings)
        Log.d(GlobalParameters.TAG_LOG, getString(R.string.remote_config_settings_completed))
        fetch()
    }

    private fun fetch() {
        var cacheExpiration: Long = 20000
        if (mFirebaseRemoteConfig.info.configSettings.isDeveloperModeEnabled) {
            cacheExpiration = 0
        }
        mFirebaseRemoteConfig.fetch(cacheExpiration)
            .addOnCompleteListener(activity!!) { task ->
                if (task.isSuccessful) {
                    Log.d(GlobalParameters.TAG_LOG, getString(R.string.fetch_succeeded))
                    mFirebaseRemoteConfig.activateFetched()
                    val textFromRemoteConfig = mFirebaseRemoteConfig.getString("textFromRemoteConfig")
                    val textColor = mFirebaseRemoteConfig.getString("textColor")
                    val textSize = mFirebaseRemoteConfig.getString("textSize")
                    setTextFromRemoteConfig(textFromRemoteConfig, textColor, textSize)
                } else {
                    SomeSingleton.instance?.showSnackBarOrToast(getString(R.string.fetch_failed))
                    Log.d(GlobalParameters.TAG_LOG, getString(R.string.fetch_failed))
                }
            }
    }

    private fun setTextFromRemoteConfig(textFromRemoteConfig: String, textColor: String, textSize: String) {
        txtFromRemoteConfig.text = textFromRemoteConfig
        txtFromRemoteConfig.textSize = textSize.toFloat()
        txtFromRemoteConfig.setTextColor(Color.parseColor(textColor))
        Log.d(GlobalParameters.TAG_LOG, getString(R.string.text_from_remote_config_is_set))
        goToSearchMovieFragment()
    }


    override fun onResume() {
        super.onResume()
        handler.post(runnableCode)

    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacksAndMessages(null)
    }

    private fun goToSearchMovieFragment() {
        handler.postDelayed({
            Utility.loadFragment(activity?.supportFragmentManager, SearchMovie(), R.id.content_frame)
        }, GlobalParameters.splashTime)

    }


    private val runnableCode = object : Runnable {
        override fun run() {
            goToSearchMovieFragment()
            handler.postDelayed(this, GlobalParameters.splashTime)
        }
    }

}


