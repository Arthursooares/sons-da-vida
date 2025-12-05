package com.sonsdavida.app

import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sonsdavida.app.ui.theme.SonsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SonsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Transparent
                ) {
                    FocusStudyScreen()
                }
            }
        }
    }
}

@Composable
fun FocusStudyScreen() {
    // Fundo em degradê com clima de foco / noite de estudos
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF0F172A), // azul bem escuro
                        Color(0xFF1E293B), // azul médio
                        Color(0xFF020617)  // quase preto
                    )
                )
            )
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Cabeçalho
            Text(
                text = "Foco nos Estudos",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFE5E7EB),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Crie seu ambiente sonoro ideal\npara se concentrar por mais tempo.",
                fontSize = 16.sp,
                color = Color(0xFFCBD5F5),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Container dos cards de som
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                SoundCard(
                    label = "Chuva Suave",
                    description = "Gotas de chuva constantes, perfeitas para bloquear ruídos externos.",
                    iconResId = R.drawable.icon_rain,
                    soundResId = R.raw.rain
                )

                SoundCard(
                    label = "Relaxing Lo-fi",
                    description = "Música relaxante para manter o foco sem distrair.",
                    iconResId = R.drawable.icon_relaxing,
                    soundResId = R.raw.relaxing
                )

                SoundCard(
                    label = "Floresta",
                    description = "Folhas, vento e natureza criando um ambiente sereno.",
                    iconResId = R.drawable.icon_forest,
                    soundResId = R.raw.forest
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Uma frase motivacional embaixo
            Text(
                text = "Dica: escolha 1 ou 2 sons, coloque um fone e mergulhe na tarefa. 🎧",
                fontSize = 14.sp,
                color = Color(0xFF9CA3AF),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
    }
}

/**
 * Card estiloso para cada som:
 * - Ícone
 * - Título
 * - Descrição
 * - Estado visual: Tocando / Tocar
 */
@Composable
fun SoundCard(
    label: String,
    description: String,
    iconResId: Int,
    soundResId: Int
) {
    var estaTocando by remember { mutableStateOf(false) }
    val context = LocalContext.current

    // MediaPlayer para esse som específico
    val mediaPlayer = remember {
        MediaPlayer.create(context, soundResId).apply {
            isLooping = true // ambiente contínuo
        }
    }

    // Liberar o recurso quando esse composable sair da tela
    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer.release()
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                estaTocando = if (estaTocando) {
                    mediaPlayer.pause()
                    false
                } else {
                    mediaPlayer.start()
                    true
                }
            },
        colors = CardDefaults.cardColors(
            containerColor = if (estaTocando)
                Color(0xFF1D283A)
            else
                Color(0xFF020617)
        ),
        shape = RoundedCornerShape(18.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Ícone
            Image(
                painter = painterResource(iconResId),
                contentDescription = label,
                modifier = Modifier
                    .size(56.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Textos
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = label,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFFE5E7EB)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = description,
                    fontSize = 14.sp,
                    color = Color(0xFF9CA3AF)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Estado (Tocar / Pausado)
            Column(
                horizontalAlignment = Alignment.End
            ) {
                val statusText = if (estaTocando) "Pausar" else "Tocar"
                val statusColor = if (estaTocando) Color(0xFF34D399) else Color(0xFF60A5FA)

                Text(
                    text = statusText,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = statusColor
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = if (estaTocando) "Reproduzindo..." else "Toque para iniciar",
                    fontSize = 12.sp,
                    color = Color(0xFF6B7280)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewFocusStudyScreen() {
    SonsTheme {
        FocusStudyScreen()
    }
}
