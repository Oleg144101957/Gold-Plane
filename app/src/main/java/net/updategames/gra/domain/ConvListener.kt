package net.updategames.gra.domain

import android.util.Log
import com.appsflyer.AppsFlyerConversionListener

class ConvListener(
    private val recievedData: (MutableMap<String, Any>?) -> Unit) : AppsFlyerConversionListener {

    override fun onConversionDataSuccess(inputData: MutableMap<String, Any>?) {
        recievedData(inputData)
    }

    override fun onConversionDataFail(inputData: String?) {
        Log.d("123123", "onConversionDataFail")
    }

    override fun onAppOpenAttribution(inputData: MutableMap<String, String>?) {
        TODO("Not yet implemented")
    }

    override fun onAttributionFailure(inputData: String?) {
        TODO("Not yet implemented")
    }
}