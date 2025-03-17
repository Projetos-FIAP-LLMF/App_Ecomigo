package com.example.ecomigo

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

class ApimapsActivity : ComponentActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializa o FusedLocationProviderClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        setContent {
            MapScreen(fusedLocationClient, this)
        }
    }
}

@Composable
fun MapScreen(fusedLocationClient: FusedLocationProviderClient, context: Context) {
    var userLocation by remember { mutableStateOf<LatLng?>(null) }
    var pontosDeColeta by remember { mutableStateOf(emptyList<Pair<LatLng, String>>()) }
    var searchQuery by remember { mutableStateOf("") }
    var apiResponse by remember { mutableStateOf("Buscando pontos de reciclagem...") }
    val cameraPositionState = rememberCameraPositionState()
    val coroutineScope = rememberCoroutineScope()

    // 🔥 Obtém a localização atual do GPS
    LaunchedEffect(Unit) {
        if (ActivityCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                location?.let {
                    val latLng = LatLng(it.latitude, it.longitude)
                    userLocation = latLng
                    cameraPositionState.position = CameraPosition.fromLatLngZoom(latLng, 14f)
                    apiResponse = "Carregando pontos de reciclagem..."

                    coroutineScope.launch {
                        pontosDeColeta = fetchRecyclingPoints(latLng).also {
                            apiResponse =
                                if (it.isEmpty()) "Nenhum ponto encontrado!" else "Total de pontos encontrados: ${it.size}"
                        }
                    }
                }
            }
        } else {
            ActivityCompat.requestPermissions(
                context as ComponentActivity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1
            )
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            GoogleMap(
                modifier = Modifier.weight(1f),
                cameraPositionState = cameraPositionState
            ) {
                // 🔥 Marcador do usuário (localização atual)
                userLocation?.let { location ->
                    Marker(
                        state = rememberMarkerState(position = location),
                        title = "Você está aqui",
                        icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)
                    )
                }

                // 🔥 Marcadores dos pontos de coleta
                pontosDeColeta.forEach { (ponto, descricao) ->
                    Marker(
                        state = rememberMarkerState(position = ponto),
                        title = descricao,
                        snippet = "Ponto de Coleta",
                        icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)
                    )
                }
            }
        }

        // 🔥 Painel inferior para os botões e pesquisa
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFEFEBEB)) // 🔹 Fundo cinza claro para destacar os botões
                .padding(16.dp)
                .align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Pesquisar pontos de coleta...") }
            )

            Spacer(modifier = Modifier.height(12.dp))

            // 🔥 Botão de Pesquisa (Roxo)
            CustomButton(
                text = "🔎 Pesquisar",
                color = Color(0xFF6A1B9A) // Roxo escuro
            ) {
                coroutineScope.launch {
                    val results = searchRecyclingPoints(searchQuery)
                    pontosDeColeta = results
                    apiResponse =
                        if (results.isEmpty()) "Nenhum ponto encontrado!" else "Pontos encontrados: ${results.size}"
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = apiResponse,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 🔥 Botões extras (Ver Dicas e Voltar - Verde Escuro)
            CustomButton(
                text = "💡 Ver Dicas",
                color = Color(0xFF009951) // Verde escuro
            ) {
                val intent = Intent(context, SustainableActivity::class.java)
                context.startActivity(intent)
            }

            Spacer(modifier = Modifier.height(12.dp))

            CustomButton(
                text = "Voltar",
                color = Color(0xFF009951) // Verde escuro
            ) {
                val intent = Intent(context, RecyclingGuide::class.java)
                context.startActivity(intent)
            }
        }
    }
}

suspend fun searchRecyclingPoints(query: String): List<Pair<LatLng, String>> {
    return fetchRecyclingPoints(LatLng(-23.55052, -46.633308)) // Simula a busca em São Paulo
        .filter { it.second.contains(query, ignoreCase = true) }
}


suspend fun makeApiRequest(url: String): String {
    return withContext(Dispatchers.IO) {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(url)
            .header("User-Agent", "Mozilla/5.0")
            .build()
        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw Exception("Erro HTTP ${response.code}")
            response.body?.string() ?: throw Exception("Resposta vazia")
        }
    }
}

fun parseRecyclingPoints(response: String): List<Pair<LatLng, String>> {
    return try {
        val jsonObject = JSONObject(response)

        if (!jsonObject.has("elements")) {
            Log.e("parseRecyclingPoints", "JSON não contém 'elements'. Resposta: $response")
            return emptyList()
        }

        val elements = jsonObject.getJSONArray("elements")
        val locations = mutableListOf<Pair<LatLng, String>>()

        for (i in 0 until elements.length()) {
            val point = elements.getJSONObject(i)

            if (point.has("lat") && point.has("lon")) {
                val lat = point.getDouble("lat")
                val lon = point.getDouble("lon")
                val descricao = point.optJSONObject("tags")?.optString("name", "Ponto de Coleta")
                    ?: "Ponto de Coleta"
                locations.add(Pair(LatLng(lat, lon), descricao))
            }
        }

        if (locations.isEmpty()) {
            Log.w("parseRecyclingPoints", "Nenhum ponto de reciclagem encontrado!")
        }

        locations
    } catch (e: Exception) {
        Log.e("parseRecyclingPoints", "Erro ao processar JSON: ${e.message}")
        emptyList()
    }
}

// ✅ Função para buscar pontos de reciclagem na API Overpass
suspend fun fetchRecyclingPoints(location: LatLng): List<Pair<LatLng, String>> {
    val url =
        "https://overpass-api.de/api/interpreter?data=[out:json];(node[amenity=recycling](around:30000,${location.latitude},${location.longitude});way[amenity=recycling](around:30000,${location.latitude},${location.longitude});relation[amenity=recycling](around:30000,${location.latitude},${location.longitude}););out;"
    return try {
        val response = withContext(Dispatchers.IO) { makeApiRequest(url) }
        parseRecyclingPoints(response)
    } catch (e: Exception) {
        emptyList()
    }
}

// ✅ Componente de botão reutilizável
@Composable
fun CustomButton(text: String, color: Color, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = color) // Cor personalizada
    ) {
        Text(text, color = Color.White, fontSize = 18.sp)
    }
}
