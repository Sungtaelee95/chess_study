package util

import model.data.MoveInformation
import model.data.PieceColor
import java.net.Socket

class ServerManager(socket: Socket) : ServerConnector() {

    override fun disconnectServer() {
        TODO("Not yet implemented")
    }

    override fun sendMoveInformation(moveInformation: MoveInformation) {
        TODO("Not yet implemented")
    }

    override fun receiveMoveInformation(): MoveInformation {
        TODO("Not yet implemented")
    }

    override fun getChessPieceColor(): PieceColor {
        TODO("Not yet implemented")
    }
}