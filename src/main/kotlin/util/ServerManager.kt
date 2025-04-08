import model.data.MoveInformation
import model.data.PieceColor
import util.ServerConnector
import java.net.Socket

class ServerManager(
    private val socket: Socket
) : ServerConnector() {

    override fun disconnectServer() {
        socket.close()
    }

    override fun sendMoveInformation(moveInformation: MoveInformation) {
        Protocol(
            Header.SEND_MOVE_INFORMATION_HEADER,
            socket.inputStream,
            socket.outputStream,
            moveInformation.toByteArray()
        ).run()
    }

    override fun receiveMoveInformation(): MoveInformation {
        val result =
            Protocol(
                Header.GET_OTHER_CLIENT_MOVE_INFORMATION,
                socket.inputStream,
                socket.outputStream,
            ).run()
        println("result: ${result.size}")
        return MoveInformation.fromByteArray(result)
    }

    override fun getChessPieceColor(): PieceColor {
        val result = Protocol(Header.GET_CLIENT_COLOR_HEADER, socket.inputStream, socket.outputStream).run()
        val colorByte = result.first()
        return when (colorByte) {
            PieceColor.BLACK.colorByte -> PieceColor.BLACK
            PieceColor.WHITE.colorByte -> PieceColor.WHITE
            else -> PieceColor.BLACK
        }
    }

    override fun getTurnColor(): PieceColor {
        val result =
            Protocol(Header.GET_TURN_COLOR_HEADER, socket.inputStream, socket.outputStream).run()
        val turnColor = result.first()
        return when (turnColor) {
            PieceColor.BLACK.colorByte -> PieceColor.BLACK
            PieceColor.WHITE.colorByte -> PieceColor.WHITE
            else -> PieceColor.BLACK
        }
    }
}