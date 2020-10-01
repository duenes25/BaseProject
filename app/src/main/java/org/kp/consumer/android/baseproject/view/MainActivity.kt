package org.kp.consumer.android.baseproject.view

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kp.consumer.android.baseproject.MyAdapter
import org.kp.consumer.android.baseproject.R
import org.kp.consumer.android.baseproject.db.AppDatabase
import org.kp.consumer.android.baseproject.model.BaseItem
import org.kp.consumer.android.baseproject.viewmodel.NetworkViewModel
import org.kp.consumer.android.logginglibrary.logIt


class MainActivity : AppCompatActivity() {

    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    private lateinit var networkViewModel:NetworkViewModel

    // Holds the lits of items statically for the RecyclerVew
    private val itemsList = arrayOf(
        BaseItem(
            0,
            "Raising Arizona",
            "1987"
        ),
        BaseItem(
            1,
            "Vampire's Kiss",
            "1988"
        ),
        BaseItem(
            2,
            "Con Air",
            "1997"),
        BaseItem(
            3,
            "Gone in 60 Seconds",
            "1997"
        ),
        BaseItem(
            4,
            "National Treasure",
            "2004"
        ),
        BaseItem(
            5,
            "The Wicker Man",
            "2006"
        ),
        BaseItem(
            6,
            "Ghost Rider",
            "2007"
        ),
        BaseItem(
            7,
            "Knowing",
            "2009"
        )
    )

    private lateinit var networkFragment: NetworkFragment

    /**
     * This is the onCreate Method
     *
     * @param savedInstanceState Any [Bundle]
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = AppDatabase(this)

        GlobalScope.launch {
            db.baseItemDao().insertAll(*itemsList)
        }


        viewManager = LinearLayoutManager(this)
        viewAdapter = MyAdapter(itemsList)

        recycler_view.apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }
        observeViewModels()

        registerNetworkListener()
        if (savedInstanceState == null) {
            networkFragment = NetworkFragment.newInstance()
        }

        logIt()

    }

    /**
     * Sets up the observers
     *
     * @param None
     * @return None
     */
    fun observeViewModels(){
        networkViewModel = ViewModelProvider(this).get(NetworkViewModel::class.java)
        networkViewModel.isConnectedtoWifi.observe(this, Observer {
            if (it){
                Log.d("test", "Available")// network available
                val ft2: FragmentTransaction = supportFragmentManager.beginTransaction()
                if(networkFragment.isAdded){
                    ft2.hide(networkFragment)
                }
                ft2.commit()
            }else{
                Log.d("test", "Not Available")// network available
                val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
                if (networkFragment.isAdded()) { // if the fragment is already in container
                    ft.show(networkFragment)
                } else { // fragment needs to be added to frame container
                    ft.add(R.id.root_layout, networkFragment, "networkFragment")
                }
                ft.commit()
            }
        })
    }

    fun registerNetworkListener() {
        val connectivityManager = application.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE)as ConnectivityManager
        val networkRequest =
            NetworkRequest.Builder().addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI).build()
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
    }

    var networkCallback: NetworkCallback = object : NetworkCallback() {
        override fun onAvailable(network: Network) {
            networkViewModel.wifiConnectionChanged(true)
        }

        override fun onLost(network: Network) {
            networkViewModel.wifiConnectionChanged(false)
        }
    }



}