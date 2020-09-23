package org.kp.consumer.android.baseproject.viewmodel

import android.net.ConnectivityManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NetworkViewModel : ViewModel() {

    var isConnectedtoWifi = MutableLiveData<Boolean>()


    fun getIsConnectedtoWifi(): MutableLiveData<Boolean>{
        return isConnectedtoWifi
    }

    fun wifiConnectionChanged(isEnabled: Boolean): MutableLiveData<Boolean>{
        isConnectedtoWifi.postValue(isEnabled)
        return isConnectedtoWifi
    }
}