package net.updategames.gra.presantation

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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = getColor(R.color.black)
        setContentView(R.layout.activity_start)




        lifecycleScope.launch {
            delay(1500)
            runOnUiThread{
                if(isAndroid() == "1"){
                    val int = Intent(this@StartActivity, CustomActivity::class.java)
                    startActivity(int)
                }else{
                    val int = Intent(this@StartActivity, CustomActivity::class.java)
                    startActivity(int)
                }
            }
        }
    }

    private fun isAndroid() = Settings.Global.getString(contentResolver, Settings.Global.ADB_ENABLED)

}