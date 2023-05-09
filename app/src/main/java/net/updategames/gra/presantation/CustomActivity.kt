package net.updategames.gra.presantation

import android.content.Context
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.ValueCallback
import android.webkit.WebView
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import net.updategames.gra.R
import net.updategames.gra.custom.CustomContainer2
import net.updategames.gra.custom.OnFileChoose
import net.updategames.gra.databinding.ActivityCustomBinding
import net.updategames.gra.vm.CustomModel

class CustomActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCustomBinding
    private lateinit var vm: CustomModel
    lateinit var chooseCallback: ValueCallback<Array<Uri?>>
    val getContent = registerForActivityResult(ActivityResultContracts.GetMultipleContents()) {
        chooseCallback.onReceiveValue(it.toTypedArray())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = getColor(R.color.black)
        binding = ActivityCustomBinding.inflate(layoutInflater).also { setContentView(it.root) }
        vm = ViewModelProvider(this)[CustomModel::class.java]
        vm.initVM(this)

        val linkFromMemory = returnStringFromSharedPref()

        Log.d("123123", "the link from memory in onCreate is  $linkFromMemory")

        if (linkFromMemory == "no_data"){
            vm.liveLink.observe(this, Observer {
                if (it != "empty data"){
                    binding.progressBar23.visibility = View.GONE
                    startWebView2(it)
                }
            })
        }else{
            vm.liveLink.postValue(linkFromMemory)
            startWebView2(linkFromMemory)
        }
    }

    private fun startWebView2(link: String) {
        val webView2 = CustomContainer2(this, object : OnFileChoose{
            override fun onChooseCallbackActivated(paramChooseCallback: ValueCallback<Array<Uri?>>) {
                chooseCallback = paramChooseCallback
            }
        })

        setWebClicks(webView2)
        webView2.loadUrl(link)
        binding.root.addView(webView2)
        webView2.startWebView2(getContent)
    }
    private fun returnStringFromSharedPref() : String{
        val sharedPref = getSharedPreferences("123123", Context.MODE_PRIVATE)
        val encryptedLinkKey = "f`yvs"
        val link = sharedPref.getString(CustomContainer2.SimpleXorCipher.decrypt(encryptedLinkKey), "no_data")
        return link.toString()
    }
    private fun setWebClicks(webview : WebView){
        onBackPressedDispatcher.addCallback(this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (webview.canGoBack()) {
                        webview.goBack()
                    }
                }
            })
    }
}
