package project.dheeraj.netflag.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object NetworkUtils: ConnectivityManager.NetworkCallback() {

    private val networkLiveData: MutableLiveData<Boolean> = MutableLiveData()


    fun getNetworkLiveData(context: Context): LiveData<Boolean> {

        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(this)
        }
        else {
            val builder = NetworkRequest.Builder()
            connectivityManager.registerNetworkCallback(builder.build(),this)
        }

        var isConnected = false

        connectivityManager.allNetworks.forEach { net ->

            val networkCapability = connectivityManager.getNetworkCapabilities(net)

            networkCapability?.let { capability ->
                if (capability.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
                    isConnected = false
                    return@forEach
                }
            }

        }

        return networkLiveData
    }

    override fun onAvailable(network: Network) {
        networkLiveData.postValue(true)
    }

    override fun onLost(network: Network) {
        networkLiveData.postValue(false)
    }

}