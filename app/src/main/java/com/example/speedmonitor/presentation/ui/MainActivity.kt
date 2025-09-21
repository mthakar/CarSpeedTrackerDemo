package com.example.speedmonitor.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.speedmonitor.data.repository.FleetRepositoryImpl
import com.example.speedmonitor.presentation.speed.CarSpeedProvider
import com.example.speedmonitor.presentation.viewmodel.SpeedViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainActivity : ComponentActivity() {

    private val customerId = "CUST_001"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repo = FleetRepositoryImpl(applicationContext)
        val carProvider = CarSpeedProvider(this)

        val vmFactory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return SpeedViewModel(repo, carProvider, customerId) as T
            }
        }

        val viewModel: SpeedViewModel by viewModels { vmFactory }

        setContent {
            val speed by viewModel.speed.collectAsState()
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Current speed: ${'$'}{speed} km/h")
                Button(onClick = { /* placeholder: could toggle logging */ }) {
                    Text("Refresh")
                }
            }
        }
    }
}
