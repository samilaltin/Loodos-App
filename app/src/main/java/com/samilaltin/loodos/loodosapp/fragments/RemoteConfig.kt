package com.samilaltin.loodos.loodosapp.fragments


import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.samilaltin.loodos.loodosapp.BuildConfig

import com.samilaltin.loodos.loodosapp.R
import com.samilaltin.loodos.loodosapp.common.Settings
import com.samilaltin.loodos.loodosapp.common.SomeSingleton
import com.samilaltin.loodos.loodosapp.common.Utility
import kotlinx.android.synthetic.main.fragment_remote_config.*

//import android.R


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class RemoteConfig : Fragment() {

    private lateinit var mFirebaseRemoteConfig: FirebaseRemoteConfig

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
        fetch()
    }

    private fun fetch() {
        var cacheExpiration: Long = 3000
        if (mFirebaseRemoteConfig.info.configSettings.isDeveloperModeEnabled) {
            cacheExpiration = 0
        }
        mFirebaseRemoteConfig.fetch(cacheExpiration)
            .addOnCompleteListener(activity!!) { task ->
                if (task.isSuccessful) {
                    mFirebaseRemoteConfig.activateFetched()
                    val textFromRemoteConfig = mFirebaseRemoteConfig.getString("textFromRemoteConfig")
                    val textColor = mFirebaseRemoteConfig.getString("textColor")
                    val textSize = mFirebaseRemoteConfig.getString("textSize")
                    setTextFromRemoteConfig(textFromRemoteConfig, textColor, textSize)
                } else {
                    SomeSingleton.instance!!.showSnackBarOrToast("Fetch Failed")
                }
            }
    }

    private fun setTextFromRemoteConfig(textFromRemoteConfig: String, textColor: String, textSize: String) {
        txtFromRemoteConfig.text = textFromRemoteConfig
        txtFromRemoteConfig.textSize = textSize.toFloat()
        txtFromRemoteConfig.setTextColor(Color.parseColor(textColor))
        goToSearchMovieFragment()
    }

    private fun goToSearchMovieFragment() {
        Handler().postDelayed({
            Utility.loadFragment(activity!!.supportFragmentManager, SearchMovie(), R.id.content_frame)
        }, Settings.splashTime)

    }

}


