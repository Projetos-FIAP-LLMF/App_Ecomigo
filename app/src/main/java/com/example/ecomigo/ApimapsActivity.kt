package com.example.ecomigo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.foundation.layout.fillMaxSize
import com.google.maps.android.compose.*

class ApimapsActivity : ComponentActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        setContent {
            MapScreen(fusedLocationClient, this)
        }
    }
}

@Composable
fun MapScreen(fusedLocationClient: FusedLocationProviderClient, context: Context) {
    // Estado para armazenar a localização do usuário
    var userLocation by remember { mutableStateOf<LatLng?>(null) }

    // Estado para armazenar os pontos de coleta próximos
    var pontosDeColeta by remember { mutableStateOf<List<LatLng>>(emptyList()) }

    // Estado para controlar a câmera do mapa
    val cameraPositionState = rememberCameraPositionState()

    // Efeito para buscar a localização do usuário
    LaunchedEffect(Unit) {
        if (ActivityCompat.checkSelfPermission(
                context,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                location?.let {
                    val latLng = LatLng(it.latitude, it.longitude)
                    userLocation = latLng
                    cameraPositionState.position = CameraPosition.fromLatLngZoom(latLng, 14f)

                    // Simula a busca de pontos de coleta próximos
                    pontosDeColeta = buscarPontosProximos(latLng)
                }
            }
        } else {
            // Solicita permissão de localização
            ActivityCompat.requestPermissions(
                context as ComponentActivity,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )
        }
    }

    // Exibe o mapa
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        // Adiciona marcador para a localização do usuário
        userLocation?.let { location ->
            Marker(
                state = rememberMarkerState(position = location),
                title = "Você está aqui"
            )
        }

        // Adiciona marcadores para os pontos de coleta próximos
        pontosDeColeta.forEach { ponto ->
            Marker(
                state = rememberMarkerState(position = ponto),
                title = "Ponto de Coleta",
                snippet = "Descrição do ponto"
            )
        }
    }
}

// Função para simular a busca de pontos de coleta próximos
private fun buscarPontosProximos(localizacao: LatLng): List<LatLng> {
    return listOf(
        LatLng(localizacao.latitude + 0.01, localizacao.longitude + 0.01),
        LatLng(localizacao.latitude - 0.01, localizacao.longitude - 0.01),
        LatLng(localizacao.latitude + 0.02, localizacao.longitude - 0.02)
    )
}

