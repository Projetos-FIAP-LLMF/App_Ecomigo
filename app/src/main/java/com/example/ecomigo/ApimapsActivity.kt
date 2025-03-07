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

class ApimapsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                ButtonScreen()
            }
        }
    }
}

@Composable
fun ButtonScreen() {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        val context = LocalContext.current
        Button(

            onClick = {
                val intent = Intent(context, SustainableActivity::class.java)
                context.startActivity(intent) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ver Dicas")
        }
    }
}

