package util

import model.data.MoveInformation
import model.data.PieceColor

abstract class ServerConnector {
    abstract fun disconnectServer()
    abstract fun sendMoveInformation(moveInformation: MoveInformation)
    abstract fun receiveMoveInformation(): MoveInformation
    abstract fun getChessPieceColor(): PieceColor

    protected companion object {
        const val MOVE_HEADER = 0x15
        const val COLOR_HEADER = 0X16
        const val MOVE_SLOW_HEADER = 0x17
        const val COLOR_SLOW_HEADER = 0X18
    }
}