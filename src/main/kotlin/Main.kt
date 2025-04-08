import controller.ChessGame
import kotlinx.coroutines.runBlocking
import util.BoardManager
import view.InputView
import view.OutputView
import java.io.InputStream
import java.io.OutputStream
import java.net.Socket
import java.nio.ByteBuffer

fun main() = runBlocking {
    val server = Socket("127.0.0.1", 33769)
//     일반
    val game = ChessGame(InputView(), OutputView(), BoardManager(), ServerManager(server))
    game.gameSetUp()
    game.start()

//     딜레이
//    ChessGame(InputView(), OutputView(), BoardManager(), DelayServerManager(server)).start()
}

class Protocol(
    private val header: Header,
    private val inputStream: InputStream,
    private val outputStream: OutputStream,
    private val bytes: ByteArray = byteArrayOf(),
) {
    fun run(): ByteArray {
        val dataLengthByte = ByteBuffer
            .allocate(ProtocolSetting.DATA_LENGTH.value)
            .putInt(bytes.size)
            .array()

        val sendBytes = ByteArray(bytes.size + 5) { index ->
            when (index) {
                0 -> header.byte
                in 1..ProtocolSetting.DATA_LENGTH.value -> dataLengthByte[index - 1]
                else -> bytes[index - (ProtocolSetting.HEAD_LENGTH.value + ProtocolSetting.DATA_LENGTH.value)]
            }
        }
        return when (header) {
            // 지연
            Header.GET_SLOW_CLIENT_COLOR_HEADER -> getClientColor(sendBytes) // 수정 필요
            Header.SEND_MOVE_SLOW_HEADER -> sendMoveInformation(sendBytes) // 수정 필요
            //
            Header.GET_CLIENT_COLOR_HEADER -> getClientColor(sendBytes) // 완
            Header.GET_TURN_COLOR_HEADER -> getTurnColor(sendBytes) // 완
            Header.SEND_MOVE_INFORMATION_HEADER -> sendMoveInformation(sendBytes)
            Header.GET_OTHER_CLIENT_MOVE_INFORMATION -> receivedServerMessage()
        }
    }

    private fun getClientColor(sendBytes: ByteArray): ByteArray {
        outputStream.write(sendBytes)
        outputStream.flush()
        println("getClientColor")
        return receivedServerMessage()
    }

    private fun sendMoveInformation(sendBytes: ByteArray): ByteArray {
        outputStream.write(sendBytes)
        outputStream.flush()
        println("sendMoveInformation")
        return byteArrayOf()
    }

    private fun getTurnColor(sendBytes: ByteArray): ByteArray {
        outputStream.write(sendBytes)
        outputStream.flush()
        println("getTurnColor")
        return receivedServerMessage()
    }

    private fun receivedServerMessage(): ByteArray {
        println("읽는 단계?")
        val buffer = ByteArray(1024)
        val bytesRead = inputStream.read(buffer)
        if (bytesRead > 0) {
            val receivedBytes = buffer.copyOf(bytesRead)
            val data = receivedBytes.sliceArray(1 until receivedBytes.size) // 나머지 데이터
            val sizeArray = ByteArray(ProtocolSetting.DATA_LENGTH.value) { data[it] }
            val size = ByteBuffer.wrap(sizeArray).getInt()
            val contentArray = ByteArray(size) { data[it + ProtocolSetting.DATA_LENGTH.value] }
            return contentArray
        }
        return byteArrayOf()
    }
}

enum class Header(val byte: Byte) {
    // 지연
    GET_SLOW_CLIENT_COLOR_HEADER(0xC1.toByte()),
    SEND_MOVE_SLOW_HEADER(0xC2.toByte()),

    // 일반
    GET_TURN_COLOR_HEADER(0xC4.toByte()),
    GET_CLIENT_COLOR_HEADER(0xC5.toByte()),
    SEND_MOVE_INFORMATION_HEADER(0xC6.toByte()),
    GET_OTHER_CLIENT_MOVE_INFORMATION(0xC7.toByte())
}

enum class ProtocolSetting(val value: Int) {
    HEAD_LENGTH(1),
    DATA_LENGTH(4),
    POSITION_DATA_LENGTH(4),
    COLOR_DATA_LENGTH(1)
}