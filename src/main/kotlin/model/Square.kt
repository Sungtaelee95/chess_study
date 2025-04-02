package model

import model.data.*

class Square(
    private val piece: Piece? = null,
    private val bgColor: BackGround,
) {
    fun getContent(): Content {
        val pieceColor = piece?.getPieceColorName() ?: ""
        val pieceIcon = piece?.getIcon() ?: ""
        return Content(
            pieceIcon,
            pieceColor,
            bgColor.color,
        )
    }

    fun isEqualOfType(type: Type) = piece?.isEqualOfType(type) == true

    fun isEqualOfColor(pieceColor: PieceColor) = piece?.isEqualOfColor(pieceColor) == true

    fun containsPiece() = piece != null

    fun nextDirs(): List<Node> {
        piece ?: return emptyList()
        return piece.getDirections()
    }

    fun getDistance(): Int {
        piece ?: return 0
        return piece.getDistance()
    }

    fun getHelperContent() = Square(this.piece, BackGround.ASSISTANT)

    fun pawnFirstMovie(
        row: Int,
        col: Int,
    ) = piece?.getPawnFirstNode(row, col) ?: Node(row, col)

    fun getAttackDirections() = piece?.getAttackDirections() ?: emptyList()

    fun pollPiece(): Square =
        Square(
            null,
            this.bgColor,
        )

    fun update(square: Square): Square =
        Square(
            square.piece,
            this.bgColor,
        )
}
