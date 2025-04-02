package model

import model.data.Node
import model.data.PieceColor
import model.data.Type

class SelectSquare(
    private val input: List<String>,
    private val board: Array<Array<Square>>,
    private val turnColor: PieceColor,
) {
    init {
        require(input.size == 2) { "row, col 값을 ',' 기준으로 다시 입력해주세요." }
        require(input[0].toIntOrNull() != null) { "row 값을 숫자로 입력해주세요." }
        require(input[1].toIntOrNull() != null) { "col 값을 숫자로 입력해주세요." }
        require(input[0].toInt() in 1..8) { "올바르지 않은 행 값 입니다." }
        require(input[1].toInt() in 1..8) { "올바르지 않은 열 값 입니다." }
        val row = input[0].toInt() - 1
        val col = input[1].toInt() - 1
        val square = board[row][col]
        require(square.isEqualOfColor(turnColor)) { "빈칸과 현재턴과 다른 색상은 선택할 수 없습니다." }
    }

    fun getPositionNode() = Node(input[0].toInt() - 1, input[1].toInt() - 1)

    fun calculateNextNodes(): List<Node> {
        val nodes = mutableListOf<Node>()
        val row = input[0].toInt() - 1
        val col = input[1].toInt() - 1
        val distance = board[row][col].getDistance()
        for (dire in board[row][col].nextDirs()) {
            for (dist in 1..distance) {
                val nr = row + dire.row * dist
                val nc = col + dire.col * dist
                if (nr < 0 || nc < 0 || nr >= 8 || nc >= 8) continue
                if (board[nr][nc].containsPiece()) {
                    if (!board[nr][nc].isEqualOfColor(turnColor)) nodes.add(Node(nr, nc))
                    break
                }
                nodes.add(Node(nr, nc))
            }
        }
        if (board[row][col].isEqualOfType(Type.Pawn)) {
            val node = Node(row, col)
            val firstMoveNode = pawnFirstMoveNode(node)
            if (firstMoveNode.row != row) {
                nodes.add(firstMoveNode)
            }
            nodes.addAll(getPawnAttackNodes(node))
        }
        return nodes.sortedWith(
            Comparator { o1, o2 -> if (o1.row == o2.row) o1.col.compareTo(o2.col) else o1.row.compareTo(o2.row) }
        )
    }

    private fun pawnFirstMoveNode(node: Node) = board[node.row][node.col].pawnFirstMovie(node.row, node.col)

    private fun getPawnAttackNodes(node: Node): List<Node> {
        val attackList = mutableListOf<Node>()
        for (direction in board[node.row][node.col].getAttackDirections()) {
            val nr = node.row + direction.row
            val nc = node.col + direction.col
            if (nr < 0 || nc < 0 || nr >= 8 || nc >= 8) continue
            if (board[nr][nc].containsPiece()) attackList.add(Node(nr, nc))
        }
        return attackList
    }
}
