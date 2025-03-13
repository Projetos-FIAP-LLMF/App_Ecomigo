package com.example.ecomigo


import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberMarkerState

class ApimapsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                MapScreen()

               // ButtonScreen()
            }
        }
    }
}

@Composable
fun MapScreen() {
    val saoPaulo = LatLng(-23.5505, -46.6333)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(saoPaulo, 10f)
    }
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ){
        Marker(
            state = rememberMarkerState(position = saoPaulo),
            title = "São Paulo",
            snippet = "Cidade maravilhosa"
        )
    }
}

//@Composable
//fun ButtonScreen() {
//    Column(
//        modifier = Modifier.fillMaxSize().padding(16.dp),
//        verticalArrangement = Arrangement.Center
//    ) {
//        val context = LocalContext.current
//        Button(
//
//            onClick = {
//                val intent = Intent(context, SustainableActivity::class.java)
//                context.startActivity(intent) },
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Text("Ver Dicas")
//        }
//    }
//}

