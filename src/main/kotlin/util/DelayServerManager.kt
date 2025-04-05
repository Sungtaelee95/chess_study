package util

import Protocol
import model.data.MoveInformation
import model.data.PieceColor
import java.io.ObjectInputStream
import java.io.PrintWriter
import java.net.Socket

class DelayServerManager(
    private val socket: Socket
) : ServerConnector() {

    override fun disconnectServer() {
        socket.close()
    }

    override fun sendMoveInformation(moveInformation: MoveInformation) {
        val pw = PrintWriter(socket.outputStream, true)
        val br = socket.inputStream.bufferedReader()
        Protocol(Header.SEND_MOVE_SLOW_HEADER, br, pw, moveInformation.toByteArray()).run()
    }

    override fun receiveMoveInformation(): MoveInformation {
        val inputStream = socket.inputStream
        val ois = ObjectInputStream(inputStream)
        val inputObject = ois.readObject() as MoveInformation
        return inputObject
    }

    override fun getChessPieceColor(): PieceColor {
        val pw = PrintWriter(socket.outputStream, true)
        val br = socket.inputStream.bufferedReader()
        val result = Protocol(Header.GET_SLOW_COLOR, br, pw).run()
//        val colorDataByte = result
//            .sliceArray(ProtocolSetting.HEAD_LENGTH.value + ProtocolSetting.DATA_LENGTH.value until result.size)
//            .first()
//
//        val color = if (colorDataByte == PieceColor.BLACK.colorByte) PieceColor.BLACK else PieceColor.WHITE
        return PieceColor.BLACK
    }
}