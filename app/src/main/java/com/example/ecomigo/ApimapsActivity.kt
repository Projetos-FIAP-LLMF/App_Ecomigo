package com.example.ecomigo

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.style.TextAlign
import com.google.maps.android.compose.*
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse
import com.google.android.gms.tasks.Task

class ApimapsActivity : ComponentActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var placesClient: PlacesClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializa o FusedLocationProviderClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // Inicializa o PlacesClient com a chave da API
        Places.initialize(applicationContext, "AIzaSyBf16092Ew_w5eskQ7vsQOE1LzZy6HpKRM") // Substitua pela sua chave
        placesClient = Places.createClient(this)

        setContent {
            MapScreen(fusedLocationClient, placesClient, this)
        }
    }
}

@Composable
fun MapScreen(
    fusedLocationClient: FusedLocationProviderClient,
    placesClient: PlacesClient,
    context: Context
) {
    // Estado para armazenar a localização do usuário
    var userLocation by remember { mutableStateOf<LatLng?>(null) }

    // Estado para armazenar os pontos de coleta próximos
    var pontosDeColeta by remember { mutableStateOf<List<Place>>(emptyList()) }

    // Estado para controlar a câmera do mapa
    val cameraPositionState = rememberCameraPositionState()

    // Estado para a pesquisa manual
    var searchQuery by remember { mutableStateOf("") }

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

                    // Busca pontos de coleta próximos
                    buscarPontosProximos(placesClient, latLng, context) { places ->
                        pontosDeColeta = places
                    }
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

    // Exibe o mapa e os botões de navegação
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Mapa
        GoogleMap(
            modifier = Modifier.weight(1f),
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
            pontosDeColeta.forEach { place ->
                place.latLng?.let { latLng ->
                    Marker(
                        state = rememberMarkerState(position = latLng),
                        title = place.name,
                        snippet = place.address
                    )
                }
            }
        }

        // Campo de pesquisa manual
        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            placeholder = { Text("Pesquisar pontos de coleta...") }
        )

        // Botão de pesquisa manual
        Button(
            onClick = {
                // Busca pontos de coleta com base na pesquisa manual
                buscarPontosPorNome(placesClient, searchQuery, context) { places ->
                    pontosDeColeta = places
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text("Pesquisar", color = Color.White, fontSize = 16.sp)
        }

        // Mensagem se nenhum ponto for encontrado
        if (pontosDeColeta.isEmpty()) {
            Text(
                text = "Nenhum ponto de coleta encontrado. Tente ampliar a área de busca ou pesquisar manualmente.",
                modifier = Modifier.padding(16.dp),
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
        }

        // Botões de navegação
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Botão "Ver Dicas"
            Button(
                onClick = {
                    val intent = Intent(context, SustainableActivity::class.java)
                    context.startActivity(intent)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6AA84F))
            ) {
                Text("Ver Dicas", color = Color.White, fontSize = 16.sp)
            }

            // Espaçamento entre os botões
            Spacer(modifier = Modifier.height(16.dp))

            // Botão "Voltar"
            Button(
                onClick = {
                    val intent = Intent(context, RecyclingGuide::class.java)
                    context.startActivity(intent)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6AA84F))
            ) {
                Text("Voltar", color = Color.White, fontSize = 16.sp)
            }
        }
    }
}

// Função para buscar pontos de coleta próximos usando a API do Google Places
private fun buscarPontosProximos(
    placesClient: PlacesClient,
    localizacao: LatLng,
    context: Context,
    onResult: (List<Place>) -> Unit
) {
    // Define os campos que você quer buscar
    val placeFields = listOf(Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS, Place.Field.TYPES)

    // Cria a requisição de busca
    val request = FindCurrentPlaceRequest.newInstance(placeFields)

    // Verifica permissões
    if (ActivityCompat.checkSelfPermission(
            context,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    ) {
        val task: Task<FindCurrentPlaceResponse> = placesClient.findCurrentPlace(request)
        task.addOnSuccessListener { response ->
            // Filtra os lugares pelo nome ou descrição
            val places = response.placeLikelihoods
                .map { it.place }
                .filter { place ->
                    // Verifica se o nome do lugar contém palavras-chave
                    place.name?.contains("lixo", ignoreCase = true) == true ||
                            place.name?.contains("coleta", ignoreCase = true) == true ||
                            place.name?.contains("reciclagem", ignoreCase = true) == true ||
                            place.name?.contains("resíduos", ignoreCase = true) == true
                }
            onResult(places)
        }.addOnFailureListener { exception ->
            exception.printStackTrace()
        }
    }
}

// Função para buscar pontos de coleta por nome
private fun buscarPontosPorNome(
    placesClient: PlacesClient,
    query: String,
    context: Context,
    onResult: (List<Place>) -> Unit
) {
    // Define os campos que você quer buscar
    val placeFields = listOf(Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS, Place.Field.TYPES)

    // Cria a requisição de busca
    val request = FindCurrentPlaceRequest.newInstance(placeFields)

    // Verifica permissões
    if (ActivityCompat.checkSelfPermission(
            context,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    ) {
        val task: Task<FindCurrentPlaceResponse> = placesClient.findCurrentPlace(request)
        task.addOnSuccessListener { response ->
            // Filtra os lugares pelo nome ou descrição
            val places = response.placeLikelihoods
                .map { it.place }
                .filter { place ->
                    // Verifica se o nome do lugar contém a query de pesquisa
                    place.name?.contains(query, ignoreCase = true) == true
                }
            onResult(places)
        }.addOnFailureListener { exception ->
            exception.printStackTrace()
        }
    }
}