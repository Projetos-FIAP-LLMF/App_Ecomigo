package com.example.ecomigo

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ecomigo.ui.theme.EcomigoTheme

class RecyclingGuide : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EcomigoTheme {
                GuiaReciclagemScreen()
            }
        }
    }
}

@Composable
fun GuiaReciclagemScreen() {
    var descricao by remember { mutableStateOf("Escolha o tipo de material a ser reciclado...") }

    val scrollState = rememberScrollState() // Estado do scroll

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFFF1F8E9), Color.White)
                )
            )
            .padding(16.dp)
            .verticalScroll(scrollState), // Adicionando scroll
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text("Guia de", fontSize = 30.sp, color = Color.Gray, fontWeight = FontWeight.SemiBold)
        Text("Reciclagem", fontSize = 30.sp, color = Color(0xFF4CAF50), fontWeight = FontWeight.Bold)

        Text(
            "Escolha um tipo de Reciclagem",
            fontSize = 18.sp,
            color = Color(0xFF388E3C),
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(top = 21.dp, bottom = 16.dp)
        )

        Row(Modifier.padding(top = 16.dp)) {
            LixeiraItem(R.drawable.papel) { descricao = getDescricaoPapel() }
            LixeiraItem(R.drawable.vidro) { descricao = getDescricaoVidro() }
            LixeiraItem(R.drawable.plastico) { descricao = getDescricaoPlastico() }
            LixeiraItem(R.drawable.metal) { descricao = getDescricaoMetal() }
        }

        Row(Modifier.padding(top = 14.dp)) {
            LixeiraItem(R.drawable.organico) { descricao = getDescricaoOrganico() }
            LixeiraItem(R.drawable.nao_reciclavel) { descricao = getDescricaoNaoReciclavel() }
        }

        // Caixa de descrição
        Text(
            descricao,
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
                .background(Color.White)
                .padding(12.dp)
        )

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
            Text("Pontos de coleta", color = Color.White)
        }
    }
}

@Composable
fun LixeiraItem(imageRes: Int, onClick: () -> Unit) {
    Image(
        painter = painterResource(id = imageRes),
        contentDescription = null,
        modifier = Modifier
            .size(60.dp, 70.dp)
            .padding(4.dp)
            .clickable { onClick() }
    )
}

fun getDescricaoPapel() = """Você escolheu: Papel

            A reciclagem do papel é um processo que transforma papéis usados em novos produtos, reduzindo a necessidade de cortar árvores e economizando água e energia.
            
            Aceitos:
            Jornais, revistas e folhas de caderno
            Embalagens de papel e papelão (caixas de sapato, cereais, etc.)
            Envelopes e rascunhos
            Papel de impressora, sulfite e cartolina
            Papel de presente (desde que não tenha plástico)
            
            Não Aceitos:
            Papel higiênico e guardanapos usados
            Papel plastificado ou metalizado (ex: alguns papéis de bala)
            Etiquetas adesivas e papel carbono
            Copos de papel com revestimento plástico
            
            Cuidados Antes de Reciclar:
            Retire grampos, clipes e fitas adesivas quando possível.
            Não molhe ou suje o papel – papéis engordurados ou muito molhados não podem ser reciclados.
            Dobre ou amasse apenas o necessário para facilitar o armazenamento.
            Separe o papel do lixo comum e armazene em local seco até o descarte.
            """

fun getDescricaoVidro() = """Você escolheu: Vidro

            A reciclagem do vidro permite reutilizar o material diversas vezes sem perder sua qualidade, economizando recursos naturais como areia, calcário e energia.
            
            Aceitos:
            Garrafas de vidro (refrigerante, sucos, cervejas, etc.)
            Potes e frascos de vidro (geleias, molhos, perfumes, remédios)
            Copos e taças de vidro (desde que não sejam temperados)
            Vidros de janelas e espelhos quebrados
            
            Não Aceitos:
            Vidro temperado ou blindado (ex: para-brisas de carro)
            Lâmpadas fluorescentes e incandescentes
            Espelhos e vidros de telas de eletrônicos
            Louças de cerâmica e porcelana
            
            Cuidados Antes de Reciclar:
            Lave os vidros para remover resíduos de alimentos e líquidos.
            Retire tampas metálicas ou plásticas antes do descarte.
            Evite quebrar o vidro, pois fragmentos podem dificultar a reciclagem.
            """

fun getDescricaoPlastico() = """Você escolheu: Plástico

            O plástico pode ser reciclado e transformado em novos produtos, reduzindo a poluição e economizando petróleo, a matéria-prima principal.
            
            Aceitos:
            Garrafas PET (água, refrigerante, sucos)
            Embalagens de produtos de limpeza e higiene (shampoo, detergente, desinfetante)
            Sacolas plásticas e plásticos flexíveis (desde que limpos)
            Tampas plásticas de garrafas
            
            Não Aceitos:
            Embalagens de isopor (EPS) em algumas cidades
            Sacos plásticos muito sujos ou engordurados
            Plásticos termofixos (cabos de panela, tomadas, botões)
            Canos de PVC e embalagens metalizadas
            
            Cuidados Antes de Reciclar:
            Enxágue as embalagens para remover resíduos.
            Amassar garrafas PET para economizar espaço.
            Separe os plásticos conforme o tipo, se possível.
            """

fun getDescricaoMetal() = """Você escolheu: Metal

            A reciclagem do metal ajuda a reduzir a extração de minérios e consome menos energia do que a produção de metais novos.
            
            Aceitos:
            Latas de alumínio (refrigerante, cerveja, sucos)
            Latas de aço (conservas, leite em pó)
            Tampinhas de garrafa metálicas
            Panelas de aço inox (sem cabo de madeira ou plástico)
            
            Não Aceitos:
            Esponjas de aço (bombril)
            Clipes, grampos e pequenos metais enferrujados
            Latas de tinta e aerossóis contaminados
            Cabos elétricos revestidos
            
            Cuidados Antes de Reciclar:
            Lave as latas antes de descartar.
            Amasse latas de alumínio para economizar espaço.
            Separe metais ferrosos e não ferrosos, se possível.
            """

fun getDescricaoOrganico() = """Você escolheu: Orgânico

            Resíduos orgânicos são aqueles de origem natural, como restos de alimentos, podendo ser compostados para gerar adubo natural.
            
            Aceitos:
            Cascas e restos de frutas, legumes e verduras
            Borra de café e saquinhos de chá
            Cascas de ovo e restos de pão
            Folhas secas e restos de jardinagem
            
            Não Aceitos:
            Óleos de cozinha e gorduras animais
            Restos de carne, peixe e laticínios (atraem pragas no composto)
            Excrementos de animais
            Plantas doentes ou tratadas com pesticidas
            
            Cuidados Antes de Reciclar:
            Utilize composteiras ou descarte em locais específicos.
            Evite misturar resíduos orgânicos com recicláveis.
            Armazene em local seco e arejado para evitar odores.
            """

fun getDescricaoNaoReciclavel() = """Você escolheu: Não Reciclável

            Alguns materiais não podem ser reciclados devido à sua composição ou contaminação. Eles devem ser descartados corretamente para evitar impactos ambientais.
            
            Exemplos de Materiais Não Recicláveis:
            Papel higiênico e fraldas descartáveis
            Guardanapos e lenços de papel sujos
            Espuma, isopor sujo e fita adesiva
            Embalagens metalizadas de salgadinhos e chocolates
            Louças, porcelanas e cerâmicas quebradas
            
            Cuidados Antes de Descartar:
            Sempre tente reduzir o uso de materiais não recicláveis.
            Separe corretamente para evitar contaminação de recicláveis.
            Descarte em aterros sanitários ou locais específicos.
            """
