package net.updategames.gra.custom

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Message
import android.util.Log
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.result.ActivityResultLauncher
import net.updategames.gra.Constants
import net.updategames.gra.presantation.AviatorActivity

class CustomContainer2(context: Context, val onFileChoose: OnFileChoose) : WebView(context) {

    val sharedMemory = context.getSharedPreferences("123123", Context.MODE_PRIVATE)

    fun startWebView2(getContent: ActivityResultLauncher<String>){
        webViewClient = object : WebViewClient(){
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                Log.d("123123", "The URL is $url")
                val base = Constants.URL1+Constants.URL2

                val intent = Intent(context, AviatorActivity::class.java)

                if (url == base){
                    context.startActivity(intent)
                } else {
                    val linkFromMemory = sharedMemory.getString("link", "noString")
                    if (linkFromMemory == "noString"){
                        val isContainWord: Boolean? = url?.contains("goldplane")

                        if (isContainWord != true){
                            sharedMemory.edit().putString("link", url).apply()
                        }
                    }
                }
            }
        }

        with(settings){
            javaScriptEnabled = true
            domStorageEnabled = true
            loadWithOverviewMode = false
            userAgentString = settings.userAgentString.replace("wv", "")
        }

        webChromeClient = object : WebChromeClient(){
            override fun onShowFileChooser(
                webView: WebView?,
                filePathCallback: ValueCallback<Array<Uri?>>,
                fileChooserParams: FileChooserParams?
            ): Boolean {
                onFileChoose.onChooseCallbackActivated(filePathCallback)
                getContent.launch("image/*")
                return true
            }

            override fun onCreateWindow(
                view: WebView?,
                isDialog: Boolean,
                isUserGesture: Boolean,
                resultMsg: Message
            ): Boolean {
                val createdWV = WebView(context)
                with(createdWV.settings){
                    javaScriptEnabled = true
                    javaScriptCanOpenWindowsAutomatically = true
                    domStorageEnabled = true
                    setSupportMultipleWindows(true)
                }
                createdWV.webChromeClient = this
                val trans = resultMsg.obj as WebView.WebViewTransport
                trans.webView = createdWV
                resultMsg.sendToTarget()
                return true
            }
        }
    }
}