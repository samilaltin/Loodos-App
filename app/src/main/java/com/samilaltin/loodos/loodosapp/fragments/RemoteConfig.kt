package com.samilaltin.loodos.loodosapp.fragments


import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.FirebaseApp
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.samilaltin.loodos.loodosapp.BuildConfig

import com.samilaltin.loodos.loodosapp.R
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

    private var mRemoteConfig: FirebaseRemoteConfig? = null
    private var splashTime: Long = 3000
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView =
            inflater.inflate(R.layout.fragment_remote_config, container, false)
//        setRemoteConfig()
        goToSearchMovieFragment()
        return rootView
    }

    private fun setRemoteConfig() {
        mRemoteConfig = FirebaseRemoteConfig.getInstance()
        val configSettings = FirebaseRemoteConfigSettings.Builder()
            .setDeveloperModeEnabled(BuildConfig.DEBUG).build()
        mRemoteConfig!!.setConfigSettings(configSettings)
        mRemoteConfig!!.setDefaults(R.xml.remote_config)
        FirebaseRemoteConfig.getInstance().setConfigSettings(configSettings)
        fetch()
    }

    private fun fetch() {
        var cacheExpiration: Long = 3000
        if (mRemoteConfig!!.info.configSettings.isDeveloperModeEnabled) {
            cacheExpiration = 0
        }
        mRemoteConfig!!.fetch(cacheExpiration)
            .addOnCompleteListener(activity!!) { task ->
                if (task.isSuccessful) {
                    mRemoteConfig!!.activateFetched()

                    val textFromRemoteConfig = mRemoteConfig!!.getString("textFromRemoteConfig")
                    val textColor = mRemoteConfig!!.getString("textColor")

                    setTextFromRemoteConfig(textFromRemoteConfig, textColor)
                } else {
                    SomeSingleton.instance!!.showSnackBarOrToast("Remote Config Error")
                }
            }
    }

    private fun setTextFromRemoteConfig(textFromRemoteConfig: String, textColor: String) {
        txtFromRemoteConfig.text = textFromRemoteConfig
        txtFromRemoteConfig.setTextColor(Color.parseColor(textColor))
        goToSearchMovieFragment()
    }

    private fun goToSearchMovieFragment() {
        Handler().postDelayed({
            Utility.loadFragment(activity!!.supportFragmentManager, SearchMovie(), R.id.content_frame)
        }, splashTime)

    }

}


