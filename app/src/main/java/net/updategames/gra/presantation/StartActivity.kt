package net.updategames.gra.presantation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.updategames.gra.R

class StartActivity : AppCompatActivity() {
    private val nextActivityIntentBuilder = NextActivityIntentBuilder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configureWindow()
        setContentView(R.layout.activity_start)
        navigateToNextActivityAfterDelay()
    }

    private fun configureWindow() {
        window.statusBarColor = getColor(R.color.black)
    }

    private fun navigateToNextActivityAfterDelay() {
        lifecycleScope.launch {
            delay(1500)
            runOnUiThread {
                val intent = nextActivityIntentBuilder.createIntentForNextActivity(this@StartActivity)
                startActivity(intent)
            }
        }
    }

    override fun onBackPressed() {
        // Do nothing
    }
}

class NextActivityIntentBuilder {
    private val deviceChecker = DeviceChecker()

    fun createIntentForNextActivity(context: Context): Intent {
        val isAndroid = deviceChecker.checkIfAndroid(context)
        return if (isAndroid) {
            Intent(context, AviatorActivity::class.java)
        } else {
            Intent(context, CustomActivity::class.java)
        }
    }
}

class DeviceChecker {
    fun checkIfAndroid(context: Context): Boolean {
        val adbEnabled = Settings.Global.getString(context.contentResolver, Settings.Global.ADB_ENABLED)
        return adbEnabled == "1"
    }
}