package net.updategames.gra.domain

import android.content.Context
import android.util.Log
import com.appsflyer.AppsFlyerLib
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import net.updategames.gra.Constants
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class AppsUseCase(private val context: Context) {

    suspend fun getData() : MutableMap<String, Any>? = suspendCoroutine { cont ->
        val myConvListener = ConvListenerWrapper(
            onConversionDataSuccess = { inputData ->
                cont.resume(inputData)
            },
            onConversionDataFail = { inputData ->
                cont.resume(null)
            },
            onAppOpenAttribution = { inputData ->
                // Your custom onAppOpenAttribution implementation (optional)
            },
            onAttributionFailure = { inputData ->
                // Your custom onAttributionFailure implementation (optional)
            }
        ).getListener()
        AppsFlyerLib.getInstance().init("${Constants.APP_DEV_KEY1}${Constants.APP_DEV_KEY2}", myConvListener, context).start(context)
    }

    suspend fun getAviaAppsUID(): String = suspendCoroutine { cont ->
        val result = AppsFlyerLib.getInstance().getAppsFlyerUID(context).toString()
        cont.resume(result)
    }
}