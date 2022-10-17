package com.vados.nasa_photo.ui.navigation

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.gb.weather.view.weatherlist.WeatherListRecyclerAdapter
import com.vados.nasa_photo.databinding.FragmentMarketMenuBinding
import com.vados.nasa_photo.utils.showSnackBarErrorMsg
import molchanov.hammertesttask.viewmodel.MenuListAppState
import molchanov.hammertesttask.viewmodel.MenuListViewModel

class MarketMenuFragment:Fragment() {

    companion object {
        lateinit var viewModel: MenuListViewModel
        fun newInstance() = MarketMenuFragment()
    }
    var initConnection = false
    var isConnection = true

    private var _binding: FragmentMarketMenuBinding? = null
    private val binding: FragmentMarketMenuBinding
    get() {
        return _binding!!
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        _binding = FragmentMarketMenuBinding.inflate(inflater)

        //Подключаем рессивер для отслеживания состояния сети
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        context?.registerReceiver(networkStateReceiver, filter)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[MenuListViewModel::class.java]
        viewModel.getLiveData().observe(viewLifecycleOwner) { t -> renderData(t) }
    }

    private fun renderData(menuListAppState: MenuListAppState){
        when (menuListAppState){
            is MenuListAppState.Succes ->{
                binding.progressBar.isVisible = false
                binding.recyclerview.adapter = WeatherListRecyclerAdapter(menuListAppState.menuListDTO)
            }
            is MenuListAppState.Error -> {
                binding.progressBar.isVisible = false
                view?.showSnackBarErrorMsg("Данные не загружены")
            }
            is MenuListAppState.Loading -> {
                binding.progressBar.isVisible = true
            }
        }
    }

    //region Ресивер для контроля сети
    private var networkStateReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val noConnectivity =
                intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false)
            if (!noConnectivity) {
                onConnectionFound()
            } else {
                onConnectionLost()
            }
        }
    }

    fun onConnectionLost() {
        isConnection = false
        initConnection = true
        Toast.makeText(requireContext(),"Связь потеряна",LENGTH_LONG).show()
    }

    fun onConnectionFound() {
        isConnection = true
        if (initConnection) viewModel.getMenuItems()
        else initConnection = true
    }
    //endregion

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }
}