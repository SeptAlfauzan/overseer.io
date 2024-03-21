import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import core.widgets.CardInfo

@Composable
@Preview
fun App() {
    var text by remember { mutableStateOf("Hello, World!") }

    MaterialTheme {
        Surface(color = Color(0xFFf6f6f6)) {
            Box(modifier = Modifier.padding(24.dp)) {

                Column(modifier = Modifier.fillMaxSize()) {
                    Text(
                        "Selamat datang",
                        style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(bottom = 24.dp)
                    )
                    Text(
                        "Hasil report hari ini",
                        style = MaterialTheme.typography.body1.copy(color = Color.Black.copy(alpha = 0.4f)),
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        CardInfo(label = "Study", value = 10, icon = Icons.Default.Info)
                        CardInfo(label = "On-Phone", value = 10, icon = Icons.Default.Phone)
                        CardInfo(label = "Sleep", value = 10, icon = Icons.Default.Info)
                    }
                    Button(onClick = {
                        text = "Hello, Desktop!"
                    }) {
                        Text(text)
                    }
                }
            }
        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
