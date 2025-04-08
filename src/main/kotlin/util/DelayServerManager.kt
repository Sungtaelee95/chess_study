package util

import Header
import Protocol
import model.data.MoveInformation
import model.data.PieceColor
import java.io.ObjectInputStream
import java.net.Socket

class DelayServerManager(
    private val socket: Socket
) : ServerConnector() {

    override suspend fun disconnectServer() {
        socket.close()
    }

    override suspend fun sendMoveInformation(moveInformation: MoveInformation) {
        Protocol(
            Header.SEND_MOVE_SLOW_HEADER,
            socket.inputStream,
            socket.outputStream,
            moveInformation.toByteArray()
        ).run()
    }

    override suspend fun receiveMoveInformation(): MoveInformation {
        val inputStream = socket.inputStream
        val ois = ObjectInputStream(inputStream)
        val inputObject = ois.readObject() as MoveInformation
        return inputObject
    }

    override suspend fun getChessPieceColor(): PieceColor {
        val result = Protocol(Header.GET_SLOW_CLIENT_COLOR_HEADER, socket.inputStream, socket.outputStream).run()
        val colorByte = result.first()
        return when (colorByte) {
            PieceColor.BLACK.colorByte -> PieceColor.BLACK
            PieceColor.WHITE.colorByte -> PieceColor.WHITE
            else -> PieceColor.BLACK
        }
    }

    override suspend fun getTurnColor(): PieceColor {
        val result = Protocol(Header.GET_TURN_COLOR_HEADER, socket.inputStream, socket.outputStream).run()
        val turnColor = result.first()
        return when (turnColor) {
            PieceColor.BLACK.colorByte -> PieceColor.BLACK
            PieceColor.WHITE.colorByte -> PieceColor.WHITE
            else -> PieceColor.BLACK
        }
    }
}