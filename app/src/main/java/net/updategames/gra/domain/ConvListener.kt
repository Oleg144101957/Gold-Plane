package net.updategames.gra.domain

import android.util.Log
import com.appsflyer.AppsFlyerConversionListener

open class ConvListener(
    private val recievedData: (MutableMap<String, Any>?) -> Unit) : AppsFlyerConversionListener {

    override fun onConversionDataSuccess(inputData: MutableMap<String, Any>?) {
        recievedData(inputData)
    }

    override fun onConversionDataFail(inputData: String?) {

    }

    override fun onAppOpenAttribution(inputData: MutableMap<String, String>?) {

    }

    override fun onAttributionFailure(inputData: String?) {

    }
}

class ConvListenerWrapper(
    private val onConversionDataSuccess: (MutableMap<String, Any>?) -> Unit,
    private val onConversionDataFail: (String?) -> Unit = {  },
    private val onAppOpenAttribution: (MutableMap<String, String>?) -> Unit = { },
    private val onAttributionFailure: (String?) -> Unit = {  }
) {

    private val listener = object : ConvListener(onConversionDataSuccess) {
        override fun onConversionDataFail(inputData: String?) {
            this@ConvListenerWrapper.onConversionDataFail(inputData)
        }

        override fun onAppOpenAttribution(inputData: MutableMap<String, String>?) {
            this@ConvListenerWrapper.onAppOpenAttribution(inputData)
        }

        override fun onAttributionFailure(inputData: String?) {
            this@ConvListenerWrapper.onAttributionFailure(inputData)
        }
    }

    fun getListener(): ConvListener = listener
}
