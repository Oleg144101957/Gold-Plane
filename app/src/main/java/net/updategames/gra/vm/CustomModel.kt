package net.updategames.gra.vm

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import net.updategames.gra.domain.CheckAviaStatus

class CustomModel: ViewModel() {
    val liveLink = MutableLiveData("empty data")

    fun initVM(context: Context){
        viewModelScope.launch {
            val checkAviaStatus = CheckAviaStatus(context)
            liveLink.postValue(checkAviaStatus.returnLink())
        }
    }
}