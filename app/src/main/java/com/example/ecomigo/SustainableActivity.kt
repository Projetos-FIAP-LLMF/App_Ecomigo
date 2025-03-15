package com.example.ecomigo

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class SustainableActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                SustainableScreen()
            }
        }
    }
}

@Composable
fun SustainableScreen() {
    var selectedCategory by remember { mutableStateOf("Consumo Consciente") }
    var selectedText by remember { mutableStateOf(getCategoryText("Consumo Consciente")) }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFFF1F8E9), Color.White)
                )
            )
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Sustentável",
            fontSize = 34.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF96C93D)
        )

        Text(
            text = "no seu dia a dia",
            fontSize = 26.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF3D3D3D)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Veja dicas de como ser mais sustentável no seu dia a dia",
            fontSize = 17.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF2E5545),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            SustainabilityIcon(R.drawable.ideia, "Consumo\nConsciente") {
                selectedCategory = "Consumo Consciente"
                selectedText = getCategoryText("Consumo Consciente")
            }
            SustainabilityIcon(R.drawable.garrafa, "Redução\n de Plástico") {
                selectedCategory = "Redução de Plástico"
                selectedText = getCategoryText("Redução de Plástico")
            }
            SustainabilityIcon(R.drawable.ferramentas, "Reutilização\n e Reparo") {
                selectedCategory = "Reutilização e Reparo"
                selectedText = getCategoryText("Reutilização e Reparo")
            }
            SustainabilityIcon(R.drawable.reciclagem, "Descarte\nResponsável") {
                selectedCategory = "Descarte Responsável"
                selectedText = getCategoryText("Descarte Responsável")
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Surface(
            shape = RoundedCornerShape(12.dp),
            shadowElevation = 8.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            color = Color(0xFFF5F5F5)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Você escolheu: $selectedCategory",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFD4A017)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = selectedText,
                    fontSize = 12.sp,
                    color = Color.DarkGray
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                val intent = Intent(context, SearchActivity::class.java)
                context.startActivity(intent)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6AA84F))
        ) {
            Text("Responder Pesquisa", color = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val intent = Intent(context, ApimapsActivity::class.java)
                context.startActivity(intent)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6AA84F))
        ) {
            Text("Voltar", color = Color.White)
        }
    }
}

@Composable
fun SustainabilityIcon(iconRes: Int, description: String, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(90.dp)
            .clickable { onClick() }
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = description,
            modifier = Modifier.size(56.dp)
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = description,
            fontSize = 13.sp,
            color = Color.DarkGray,
            textAlign = TextAlign.Center
        )
    }
}

fun getCategoryText(category: String): String {
    return when (category) {
        "Consumo Consciente" -> "✔ Compre apenas o necessário\n✔ Avalie se você realmente precisa do produto antes de comprá-lo\n✔ Evite embalagens desnecessárias\n✔ Prefira produtos sem embalagens plásticas ou recicláveis"
        "Redução de Plástico" -> "✔ Utilize sacolas reutilizáveis ao invés de plásticas\n✔ Prefira garrafas de vidro ou alumínio\n✔ Evite talheres e canudos de plástico descartável\n✔ Escolha produtos com embalagens sustentáveis"
        "Reutilização e Reparo" -> "✔ Conserte produtos antes de descartá-los\n✔ Doe roupas e objetos que não usa mais\n✔ Reutilize potes, vidros e outros materiais\n✔ Adote hábitos de economia circular"
        "Descarte Responsável" -> "✔ Separe corretamente os resíduos recicláveis\n✔ Descarte eletrônicos em pontos de coleta\n✔ Nunca jogue óleo de cozinha na pia\n✔ Participe de programas de reciclagem"
        else -> ""
    }
}
