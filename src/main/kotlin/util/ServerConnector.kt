package util

import model.data.MoveInformation
import model.data.PieceColor

abstract class ServerConnector {
    abstract fun disconnectServer()
    abstract fun sendMoveInformation(moveInformation: MoveInformation)
    abstract fun receiveMoveInformation(): MoveInformation
    abstract fun getChessPieceColor(): PieceColor

    abstract fun getTurnColor(): PieceColor
}