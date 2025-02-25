package com.example.ecomigo

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val webView = findViewById<WebView>(R.id.webView)
        val btnVoltar = findViewById<Button>(R.id.btnVoltar)


        webView.settings.javaScriptEnabled = true

        // URL do Google Forms
        val googleFormUrl = "https://docs.google.com/forms/d/e/1FAIpQLSdnq72jzwLJoYjj0vbP2774bXKb_OfUKy_xftWWy8nqfEwk6A/viewform?usp=dialog"
        webView.loadUrl(googleFormUrl)


        webView.setWebViewClient(object : WebViewClient() {
            override fun doUpdateVisitedHistory(view: WebView?, url: String?, isReload: Boolean) {
                super.doUpdateVisitedHistory(view, url, isReload)
                btnVoltar.visibility = if (view?.canGoBack() == true) View.VISIBLE else View.GONE
            }
        })

        // Ação do botão de voltar
        btnVoltar.setOnClickListener {
            if (webView.canGoBack()) {
                webView.goBack()
            }
        }
    }
}

