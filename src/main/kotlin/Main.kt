import controller.ChessGame
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import util.BoardManager
import util.DelayServerManager
import view.InputView
import view.OutputView
import java.io.BufferedReader
import java.io.PrintWriter
import java.lang.Thread.sleep
import java.net.Socket
import java.nio.ByteBuffer

fun main() {
    val server = Socket("127.0.0.1", 33769)
//     일반
//    ChessGame(InputView(), OutputView(), BoardManager(), ServerManager(server)).start()
//     딜레이
    ChessGame(InputView(), OutputView(), BoardManager(), DelayServerManager(server)).start()
}

class Protocol(
    private val type: Header,
    private val bufferedReader: BufferedReader,
    private val printWriter: PrintWriter,
    private val bytes: ByteArray = byteArrayOf(),
) {
    fun run(): ByteArray {
        val dataLengthByte = ByteBuffer
            .allocate(ProtocolSetting.DATA_LENGTH.value)
            .putInt(bytes.size)
            .array()

        val sendBytes = ByteArray(bytes.size + 5) { index ->
            when (index) {
                0 -> type.byte
                in 1..dataLengthByte.size -> dataLengthByte[index - 1]
                else -> bytes[index - 5]
            }
        }
        return when (type) {
            Header.GET_SLOW_COLOR -> getSlowColor(sendBytes)
            Header.SEND_MOVE_SLOW_HEADER -> sendMoveSlowInformation(sendBytes)
            Header.GET_TURN_COLOR -> byteArrayOf()
        }
    }

    private fun getSlowColor(sendBytes: ByteArray): ByteArray {
        CoroutineScope(Dispatchers.IO).launch {
            sendBytes.forEach { byte ->
                printWriter.println(byte)
                sleep(10)
            }
        }
        val result = bufferedReader.readLine()
        return byteArrayOf(result.toByte())
    }

    private fun sendMoveSlowInformation(sendBytes: ByteArray): ByteArray {
        CoroutineScope(Dispatchers.IO).launch {
            sendBytes.forEach { byte ->
                printWriter.println(byte)
                sleep(10)
            }
        }
        return byteArrayOf()
    }
}

enum class Header(val byte: Byte) {
    GET_SLOW_COLOR(0xC1.toByte()),
    SEND_MOVE_SLOW_HEADER(0xC2.toByte()),
    GET_TURN_COLOR(0xC4.toByte())
}

enum class ProtocolSetting(val value: Int) {
    HEAD_LENGTH(1),
    DATA_LENGTH(4),
    POSITION_DATA_LENGTH(4),
    COLOR_DATA_LENGTH(1)
}