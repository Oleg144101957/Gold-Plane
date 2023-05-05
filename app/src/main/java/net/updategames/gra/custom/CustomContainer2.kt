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
                val base = Constants.URL1 + Constants.URL2
                val intent = Intent(context, AviatorActivity::class.java)

                if (url.toString().isBaseURL(base)) {
                    context.startActivity(intent)
                } else {
                    val encryptedNoString = "a~ynVorol"
                    val encryptedLink = "f`yvs"
                    val linkFromMemory = sharedMemory.getString(SimpleXorCipher.decrypt(encryptedLink), SimpleXorCipher.decrypt(encryptedNoString))

                    if (linkFromMemory == SimpleXorCipher.decrypt(encryptedNoString)) {
                        val encryptedKeyword = "c~rhg\"kyhs"

                        if (!url.toString().containsKeywordEncrypted(encryptedKeyword)) {
                            sharedMemory.edit().putString(SimpleXorCipher.decrypt(encryptedLink), url).apply()
                        }
                    }
                }
            }
        }

        with(settings){
            javaScriptEnabled = true
            domStorageEnabled = true
            loadWithOverviewMode = false
            userAgentString = settings.userAgentString.updateUserAgent()
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

    fun String.isBaseURL(baseURL: String): Boolean {
        val encodedBaseURL = SimpleXorCipher.encrypt(baseURL)
        val encodedURL = SimpleXorCipher.encrypt(this)
        return encodedURL == encodedBaseURL
    }

    // Create a function to check if the URL contains a specific keyword.
    fun String.containsKeywordEncrypted(encryptedKeyword: String): Boolean {
        val encodedURL = SimpleXorCipher.encrypt(this)
        return encodedURL.contains(encryptedKeyword)
    }


    object SimpleXorCipher {
        private const val key = 0x42

        fun encrypt(input: String): String {
            return input.map { it.toInt() xor key }.joinToString(separator = "") { it.toChar().toString() }
        }

        fun decrypt(input: String): String {
            return input.map { it.toInt() xor key }.joinToString(separator = "") { it.toChar().toString() }
        }
    }

    fun String.updateUserAgent(): String {
        val target = SimpleXorCipher.decrypt("54")
        val replacement = ""
        return this.replace(target, replacement)
    }
}