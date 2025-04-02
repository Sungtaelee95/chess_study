package model.data

class Piece(
    private val type: Type,
    private val pieceColor: PieceColor,
) {
    fun getIcon() = type.icon

    fun getPieceColorName() = pieceColor.value

    fun getDirections(): List<Node> {
        if (type == Type.Pawn && pieceColor == PieceColor.WHITE) {
            return type.dire.map { Node(it.value.row * -1, it.value.col) }
        }
        return type.dire.map { direction -> Node(direction.value.row, direction.value.col) }
    }

    fun getDistance() = type.distance

    fun isEqualOfColor(pieceColor: PieceColor) = this.pieceColor == pieceColor

    fun isEqualOfType(type: Type) = type == this.type

    fun getPawnFirstNode(
        row: Int,
        col: Int,
    ): Node {
        if (pieceColor == PieceColor.BLACK && row == 1) {
            return Node(row + 2, col)
        }
        if (pieceColor == PieceColor.WHITE && row == 6) {
            return Node(row - 2, col)
        }
        return Node(row, col)
    }

    fun getAttackDirections(): List<Node> {
        if (pieceColor == PieceColor.WHITE) {
            return type.attackDire.map { Node(it.value.row * -1, it.value.col) }
        }
        return type.attackDire.map { Node(it.value.row, it.value.col) }
    }
}
