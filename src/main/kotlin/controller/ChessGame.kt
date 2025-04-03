package controller

import model.RestartCommend
import model.SelectPosition
import model.SelectSquare
import model.Square
import model.data.BackGround
import model.data.Node
import model.data.PieceColor
import model.data.Type
import util.BoardManager
import util.ServerConnector
import view.InputView
import view.OutputView

class ChessGame(
    private val inputView: InputView,
    private val outputView: OutputView,
    private val boardManager: BoardManager,
    private val serverManager: ServerConnector
) {
    private lateinit var _turnColor: PieceColor
    private lateinit var _board: Array<Array<Square>>

    init {
        gameSetUp()
    }

    fun start() {
        while (true) {
            println("선택된 색: ${_turnColor.colorName}")
            printBoard()
            val selectedSquare = selectSquare()
            val selectedNextPosition = selectNextPosition(selectedSquare)
            if (selectedNextPosition.isRecommend()) return start()
            movePiece(selectedSquare, selectedNextPosition)
            turnChange()
            if (isOtherKingLive()) continue
            printBoard()
            if (requestRestartGame()) {
                gameSetUp()
                return start()
            }
            break
        }
    }

    private fun gameSetUp() {
        _board = boardManager.create()
        _turnColor = serverManager.getChessPieceColor()
    }

    private fun requestRestartGame(): Boolean {
        try {
            outputView.printRestartMessage()
            return RestartCommend(inputView.inputCommend()).isRestart()
        } catch (e: IllegalArgumentException) {
            outputView.printError(e.message)
            return requestRestartGame()
        }
    }

    private fun isOtherKingLive(): Boolean {
        _board.forEach { line ->
            line.forEach {
                if (it.isEqualOfType(Type.King) && it.isEqualOfColor(_turnColor)) return true
            }
        }
        return false
    }

    private fun turnChange() {
        _turnColor = if (_turnColor == PieceColor.WHITE) PieceColor.BLACK else PieceColor.WHITE
    }

    private fun movePiece(
        selectedSquare: SelectSquare,
        selectPosition: SelectPosition,
    ) {
        val oriNode = selectedSquare.getPositionNode()
        val newNode = selectPosition.getPositionNode()
        val oriSquare = _board[oriNode.row][oriNode.col]
        _board[oriNode.row][oriNode.col] = _board[oriNode.row][oriNode.col].pollPiece()
        _board[newNode.row][newNode.col] = _board[newNode.row][newNode.col].update(oriSquare)
        // 서버에
    }

    private fun selectNextPosition(
        selectedSquare: SelectSquare,
        message: String? = null,
    ): SelectPosition {
        try {
            val nextSquares = selectedSquare.calculateNextNodes()
            printHelperBoard(createHelperBoard(nextSquares), message)
            printNextPositionMessage(nextSquares)
            return SelectPosition(inputView.inputCommend(), nextSquares)
        } catch (e: IllegalArgumentException) {
            selectNextPosition(selectedSquare, e.message)
            return selectNextPosition(selectedSquare)
        }
    }

    private fun printNextPositionMessage(nextPositions: List<Node>) {
        outputView.printHelperMessage()
        if (nextPositions.isEmpty()) outputView.printEmptyNextNodes()
        nextPositions.forEachIndexed { index, node ->
            outputView.printNextNodes(index, node.row, node.col)
        }
    }

    private fun createHelperBoard(possibleNextPositions: List<Node>): Array<Array<Square>> {
        val helperBoard = Array(_board.size) { Array(_board[0].size) { Square(null, BackGround.DARK) } }
        _board.forEachIndexed { row, squares ->
            squares.forEachIndexed { col, square ->
                helperBoard[row][col] = square
            }
        }
        possibleNextPositions.forEach { node ->
            helperBoard[node.row][node.col] = helperBoard[node.row][node.col].getHelperContent()
        }
        return helperBoard
    }

    private fun selectSquare(): SelectSquare {
        try {
            val input = inputView.inputMovePiecePosition(_turnColor.colorName).split(",")
            val selectSquare = SelectSquare(input, _board, _turnColor)
            return selectSquare
        } catch (e: IllegalArgumentException) {
            outputView.printError(e.message)
            return selectSquare()
        } catch (e: Exception) {
            outputView.printError(e.message)
            return selectSquare()
        }
    }

    private fun printHelperBoard(board: Array<Array<Square>>, message: String? = null) {
        outputView.clear()
        outputView.printColumnIndex()
        outputView.println()
        board.forEachIndexed { index, squares ->
            outputView.printRowIndex(index)
            squares.forEach { square ->
                val content = square.getContent()
                outputView.printSquare(
                    content.icon,
                    content.iconColor,
                    content.bgColor,
                )
            }
            outputView.println()
        }
        message?.let { outputView.printError(message) }
    }

    private fun printBoard() {
        outputView.clear()
        outputView.printColumnIndex()
        outputView.println()
        _board.forEachIndexed { index, squares ->
            outputView.printRowIndex(index)
            squares.forEach { square ->
                val content = square.getContent()
                outputView.printSquare(
                    content.icon,
                    content.iconColor,
                    content.bgColor,
                )
            }
            outputView.println()
        }
    }
}