package util

import model.data.MoveInformation
import model.data.PieceColor
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.PrintWriter
import java.lang.Thread.sleep
import java.net.Socket

class DelayServerManager(
    private val socket: Socket
) : ServerConnector() {

//    init {
//        sendMoveInformation(MoveInformation(Node(1, 1), Node(3, 3)))
//    }

    override fun disconnectServer() {
        socket.close()
    }

    override fun sendMoveInformation(moveInformation: MoveInformation) {
        val pw = PrintWriter(socket.outputStream, true)
        val bos = ByteArrayOutputStream()
        val oos = ObjectOutputStream(bos)
        oos.writeObject(moveInformation)
        val bytes = bos.toByteArray()
        pw.println(MOVE_SLOW_HEADER)
        sleep(10)
        pw.println(bytes.size)
        bytes.forEach {
            sleep(10)
            pw.println(it)
        }
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
        pw.println(COLOR_SLOW_HEADER)
        val color = if (br.readLine() == PieceColor.BLACK.value) PieceColor.BLACK else PieceColor.WHITE
        return color
    }
}