package net.updategames.gra.presantation

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.ValueCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import net.updategames.gra.R
import net.updategames.gra.custom.CustomContainer
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

        vm.liveLink.observe(this, Observer {
            Log.d("123123", " it is $it")
            if (it != "empty data"){
                binding.progressBar23.visibility = View.GONE
                startView(it)
            } else {
                Log.d("123123", "it = emty data")
            }
        })
    }

    private fun startView(link: String){
        val webView = CustomContainer(this, object: OnFileChoose{
            override fun onChooseCallbackActivated(paramChooseCallback: ValueCallback<Array<Uri?>>) {
                chooseCallback = paramChooseCallback
            }
        })

        // Vulkan: https://vulkantop.pro/v777newb/index.php?refCode=va_w170258c138064l13852guap479_&click_id=rdekf0hajvi
        // PinUp: https://pin-up.ua/sign-up?st=ufm1aGDa&s1=rdekf0hajv9&s2=asodm&s3=&s4=&s5=&pc=30&trId=ch8d5ghct2h41r08n2gg&source=

        webView.setupWebView(getContent)
        webView.loadUrl(link)
        binding.root.addView(webView)
    }



}
