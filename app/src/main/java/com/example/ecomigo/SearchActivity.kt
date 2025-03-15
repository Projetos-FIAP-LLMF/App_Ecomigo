package com.example.ecomigo

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class SearchActivity : ComponentActivity() {
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                SearchScreen()
            }
        }
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun SearchScreen() {
    val context = LocalContext.current
    var webView: WebView? = remember { null }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFFF1F8E9), Color.White)
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),  // Adicionando rolagem
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Pesquisa:",
                fontSize = 28.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF3D3D3D)
            )

            Text(
                text = "Hábitos de Reciclagem",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF96C93D)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .background(
                        Color(0xFF56762B).copy(alpha = 0.15f),
                        RoundedCornerShape(10.dp)
                    )
                    .padding(16.dp)
            ) {
                Text(
                    text = "Pedimos que você reserve alguns minutos para responder a este formulário. Suas respostas serão muito importantes para análises futuras e ajudarão a melhorar a conscientização sobre reciclagem. Agradecemos sua colaboração!",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF2E5545),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            AndroidView(
                factory = { ctx ->
                    WebView(ctx).apply {
                        settings.javaScriptEnabled = true
                        webViewClient = object : WebViewClient() {
                            override fun doUpdateVisitedHistory(
                                view: WebView?,
                                url: String?,
                                isReload: Boolean
                            ) {
                                super.doUpdateVisitedHistory(view, url, isReload)
                            }
                        }
                        loadUrl("https://docs.google.com/forms/d/e/1FAIpQLSdnq72jzwLJoYjj0vbP2774bXKb_OfUKy_xftWWy8nqfEwk6A/viewform?usp=dialog")
                        webView = this
                    }
                },
                update = { webView = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

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
                Text("Voltar", color = Color.White)
            }

            Spacer(modifier = Modifier.height(20.dp))  // Espaço extra no final para melhor rolagem
        }
    }
}


