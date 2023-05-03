package net.updategames.gra.custom

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Message
import android.util.Log
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.result.ActivityResultLauncher


class CustomContainer (context: Context, val onFileChoose: OnFileChoose) : WebView(context) {

    @SuppressLint("SetJavaScriptEnabled")
    fun setupWebView(getContent: ActivityResultLauncher<String>) {

        webViewClient = object: WebViewClient(){
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                //показать страницу и сохранить ссылку
                Log.d("123123", "onPageFinished url is $url")
            }
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

            @SuppressLint("SetJavaScriptEnabled")
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

        // Enable JavaScript
        settings.javaScriptEnabled = true

        // Allow zooming
        settings.builtInZoomControls = true
        settings.displayZoomControls = false

        // Enable caching for faster loading
        //settings.setAppCacheEnabled(true)
        //settings.setAppCachePath(context.cacheDir.path)
        settings.cacheMode = WebSettings.LOAD_DEFAULT

        // Enable DOM storage
        settings.domStorageEnabled = true

        settings.userAgentString = settings.userAgentString.replace("wv", "")

        // Enable WideViewPort and Zoom out
        settings.useWideViewPort = true
        settings.loadWithOverviewMode = true

        // Enable smooth transition for better user experience
        isHorizontalScrollBarEnabled = false
        isVerticalScrollBarEnabled = false

    }




}
