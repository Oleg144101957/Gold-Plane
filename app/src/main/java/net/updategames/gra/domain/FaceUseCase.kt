package net.updategames.gra.domain

import android.content.Context
import android.util.Log
import com.facebook.applinks.AppLinkData
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class FaceUseCase(private val context: Context) {

    suspend fun getData() : String = suspendCoroutine{ cont ->
        Log.d("123123", "getData FaceUseCase is")
        AppLinkData.fetchDeferredAppLinkData(context){data ->
            Log.d("123123", "getData FaceUseCase is ${data.toString()}")
            cont.resume(data?.targetUri.toString())
        }
    }
}