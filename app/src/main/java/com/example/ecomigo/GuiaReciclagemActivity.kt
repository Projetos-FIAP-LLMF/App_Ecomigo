package com.example.ecomigo

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class GuiaReciclagemActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guiareciclagem)

        val txtDescricao = findViewById<TextView>(R.id.txtDescricao)  // referência do TextView

        // referência das lixeiras
        val imgLixeiraPapel = findViewById<ImageView>(R.id.imgLixeiraPapel)
        val imgLixeiraVidro = findViewById<ImageView>(R.id.imgLixeiraVidro)
        val imgLixeiraPlastico = findViewById<ImageView>(R.id.imgLixeiraPlastico)
        val imgLixeiraMetal = findViewById<ImageView>(R.id.imgLixeiraMetal)
        val imgLixeiraOrg = findViewById<ImageView>(R.id.imgLixeiraOrg)
        val imgLixeiraNaoRec = findViewById<ImageView>(R.id.imgLixeiraNaoRec)

        imgLixeiraPapel.setOnClickListener {
            txtDescricao.text = """Você escolheu: Papel

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
        }

        imgLixeiraVidro.setOnClickListener {
            txtDescricao.text = """Você escolheu: Vidro

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
        }

        imgLixeiraPlastico.setOnClickListener {
            txtDescricao.text = """Você escolheu: Plástico

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
        }

        imgLixeiraMetal.setOnClickListener {
            txtDescricao.text = """Você escolheu: Metal

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
        }

        imgLixeiraOrg.setOnClickListener {
            txtDescricao.text = """Você escolheu: Orgânico

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
        }

        imgLixeiraNaoRec.setOnClickListener {
            txtDescricao.text = """Você escolheu: Não Reciclável

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
        }
    }
}