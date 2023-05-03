package net.updategames.gra

import android.app.Application
import android.util.Log
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.onesignal.OneSignal
import kotlin.coroutines.resume

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        OneSignal.initWithContext(this)
    }
}