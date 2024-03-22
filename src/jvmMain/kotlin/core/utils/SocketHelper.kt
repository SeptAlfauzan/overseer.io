package core.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.ServerSocket

class SocketHelper(port: Int) {
    private var _server: ServerSocket? = null

    init {
        _server = ServerSocket(port)
    }

    fun listen(cbOnMessage: (String) -> Unit){
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.IO) {
                println("Socket server started")
                while (true) {
                    val client =
                    _server?.accept()
                    val message = client?.getInputStream()?.bufferedReader()?.readText()

                    message?.let {
                        cbOnMessage(it)
                    }
                }
            }
        }
    }

    fun close(){
        _server?.accept()?.close()
        _server = null
    }
}