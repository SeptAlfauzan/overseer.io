import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import core.domain.entities.Camera
import core.utils.CameraHelper
import core.utils.SocketHelper
import core.ui.widgets.CardInfo
import core.ui.widgets.DropDown
import core.ui.widgets.DropDownItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.ServerSocket
import java.util.*
import java.util.logging.SocketHandler

@Composable
@Preview
fun App() {
    var cameras: MutableList<Camera> by mutableStateOf(mutableListOf())
    var openingPyScript by remember { mutableStateOf(false) }
    val socketHelper = SocketHelper(5001)
    val pythonScriptPath =
        "D:\\codings\\python\\test\\main.py"// TODO: this code should be depend on your python program path
    val directory = "D:\\codings\\python\\test"// TODO: this code should be depend on you python project dir
    val activateScript =
        ".\\venv\\Scripts\\activate.bat"// TODO: the venv name should be depend on your python venv name
    val runPyCmd = "python $pythonScriptPath"
    val activateVenvCmd = "cd $directory && $activateScript"

    val processBuilder = ProcessBuilder("cmd.exe", "/c", "$activateVenvCmd && $runPyCmd")

    suspend fun runCameraProgram() {
        withContext(context = Dispatchers.IO) {
            openingPyScript = true

            val process = processBuilder.start()
            val inputStream = BufferedReader(InputStreamReader(process.inputStream))
            val errorStream = BufferedReader(InputStreamReader(process.errorStream))

            // Read the output
            println("Output Log:")
            var line: String? = inputStream.readLine()
            while (line != null) {
                println(line)
                line = inputStream.readLine()
            }

            // Read the error
            println("Error:")
            line = errorStream.readLine()
            while (line != null) {
                println(line)
                line = errorStream.readLine()
            }

            // Wait for the process to finish
            val exitCode = process.waitFor()
            println("Python script exited with code: $exitCode")
            openingPyScript = false
        }
    }

    LaunchedEffect(Unit){
        launch(Dispatchers.IO) {
            cameras = CameraHelper.getAvailableCameras().toMutableList()
        }
    }

    DisposableEffect(Unit) {
        socketHelper.listen { println(it) }
        onDispose { socketHelper.close() }
    }

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
                    DropDown(
                        dropdownItems =
                        cameras.map {
                            DropDownItem<Int>(id = it.camIndex.toString(), label = it.name, value = it.camIndex)
                        }
                    )
                    Button(
                        enabled = !openingPyScript,
                        onClick = {
//                        val venvProcess = processBuilderVenv.start()
//                        venvProcess.waitFor()
                            CoroutineScope(Dispatchers.IO).launch {
//                                runCameraProgram()
                            }

                        }) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(if (openingPyScript) "Camera is Open" else "Open Camera")
                            if (openingPyScript) CircularProgressIndicator(
                                color = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                        }
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
