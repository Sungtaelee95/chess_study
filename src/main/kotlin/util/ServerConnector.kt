package util

import model.data.MoveInformation
import model.data.PieceColor

abstract class ServerConnector {
    abstract suspend fun disconnectServer()
    abstract suspend fun sendMoveInformation(moveInformation: MoveInformation)
    abstract suspend fun receiveMoveInformation(): MoveInformation
    abstract suspend fun getChessPieceColor(): PieceColor

    abstract suspend fun getTurnColor(): PieceColor
}