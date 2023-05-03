package net.updategames.gra.custom

import android.net.Uri
import android.webkit.ValueCallback

interface OnFileChoose {

    fun onChooseCallbackActivated(paramChooseCallback: ValueCallback<Array<Uri?>>)

}