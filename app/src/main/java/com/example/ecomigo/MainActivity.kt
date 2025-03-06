package com.example.ecomigo

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RecyclingAppUI()
        }
    }
}

@Composable
fun RecyclingAppUI() {
    val context = LocalContext.current
    val gradientBrush = Brush.linearGradient(
        colors = listOf(
            Color(0xFFCFEFC1),
            Color(0xFFFFFFFF),
            Color(0xFFFFF3CD)
        ),
        start = Offset(0f, 1000f),
        end = Offset(1000f, 0f)

    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = gradientBrush),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.earth_pixel),
            contentDescription = "Pixel Earth",
            modifier = Modifier.size(200.dp),
            contentScale = ContentScale.Fit
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Reciclar",
            fontSize = 34.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF98CD5F),
            fontFamily = FontFamily.Default
        )
        Text(
            text = "nunca foi tão divertido",
            fontSize = 20.sp,
            color = Color.DarkGray
        )
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .padding(16.dp)
                .background(
                    Color(0xFF56762B).copy(alpha = 0.15f),
                    RoundedCornerShape(10.dp)
                ) // cor com 15% de opacidade
                .padding(16.dp)
        ) {
            Text(
                text = "Escolha o tipo de reciclagem, veja quais materiais descartar e encontre pontos de coleta perto de você. Simples, rápido e sustentável",
                textAlign = TextAlign.Center,
                color = Color.DarkGray
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                 val intent = Intent(context, GuiaReciclagemActivity::class.java)
                  context.startActivity(intent)
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF009951)),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
        ) {
            Text(text = "Começar", color = Color.White, fontSize = 16.sp)
        }
    }
}
