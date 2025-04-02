package util

import model.Square
import model.data.BackGround
import model.data.Piece
import model.data.PieceColor
import model.data.Type

class BoardManager {
    fun create(): Array<Array<Square>> {
        val board = Array<Array<Square>>(8) { emptyArray() }
        board[0] = firstLine.copyOf()
        board[1] = secondLine.copyOf()
        board[6] = sevenLine.copyOf()
        board[7] = eightLine.copyOf()
        for (row in 0..7) {
            if (row !in 2..5) continue
            var squareColor = if (row % 2 == 0) BackGround.WHITE else BackGround.DARK
            val line = Array(8) { Square(null, squareColor) }
            for (col in 0..7) {
                line[col] = Square(null, squareColor)
                squareColor = if (squareColor == BackGround.WHITE) BackGround.DARK else BackGround.WHITE
            }
            board[row] = line
        }
        return board
    }

    companion object {
        private val firstLine =
            arrayOf(
                Square(Piece(Type.Rook, PieceColor.BLACK), BackGround.WHITE),
                Square(Piece(Type.Knight, PieceColor.BLACK), BackGround.DARK),
                Square(Piece(Type.Bishop, PieceColor.BLACK), BackGround.WHITE),
                Square(Piece(Type.King, PieceColor.BLACK), BackGround.DARK),
                Square(Piece(Type.Queen, PieceColor.BLACK), BackGround.WHITE),
                Square(Piece(Type.Bishop, PieceColor.BLACK), BackGround.DARK),
                Square(Piece(Type.Knight, PieceColor.BLACK), BackGround.WHITE),
                Square(Piece(Type.Rook, PieceColor.BLACK), BackGround.DARK),
            )
        private val secondLine =
            arrayOf(
                Square(Piece(Type.Pawn, PieceColor.BLACK), BackGround.DARK),
                Square(Piece(Type.Pawn, PieceColor.BLACK), BackGround.WHITE),
                Square(Piece(Type.Pawn, PieceColor.BLACK), BackGround.DARK),
                Square(Piece(Type.Pawn, PieceColor.BLACK), BackGround.WHITE),
                Square(Piece(Type.Pawn, PieceColor.BLACK), BackGround.DARK),
                Square(Piece(Type.Pawn, PieceColor.BLACK), BackGround.WHITE),
                Square(Piece(Type.Pawn, PieceColor.BLACK), BackGround.DARK),
                Square(Piece(Type.Pawn, PieceColor.BLACK), BackGround.WHITE),
            )
        private val sevenLine =
            arrayOf(
                Square(Piece(Type.Pawn, PieceColor.WHITE), BackGround.WHITE),
                Square(Piece(Type.Pawn, PieceColor.WHITE), BackGround.DARK),
                Square(Piece(Type.Pawn, PieceColor.WHITE), BackGround.WHITE),
                Square(Piece(Type.Pawn, PieceColor.WHITE), BackGround.DARK),
                Square(Piece(Type.Pawn, PieceColor.WHITE), BackGround.WHITE),
                Square(Piece(Type.Pawn, PieceColor.WHITE), BackGround.DARK),
                Square(Piece(Type.Pawn, PieceColor.WHITE), BackGround.WHITE),
                Square(Piece(Type.Pawn, PieceColor.WHITE), BackGround.DARK),
            )

        private val eightLine =
            arrayOf(
                Square(Piece(Type.Rook, PieceColor.WHITE), BackGround.DARK),
                Square(Piece(Type.Knight, PieceColor.WHITE), BackGround.WHITE),
                Square(Piece(Type.Bishop, PieceColor.WHITE), BackGround.DARK),
                Square(Piece(Type.King, PieceColor.WHITE), BackGround.WHITE),
                Square(Piece(Type.Queen, PieceColor.WHITE), BackGround.DARK),
                Square(Piece(Type.Bishop, PieceColor.WHITE), BackGround.WHITE),
                Square(Piece(Type.Knight, PieceColor.WHITE), BackGround.DARK),
                Square(Piece(Type.Rook, PieceColor.WHITE), BackGround.WHITE),
            )
    }
}
