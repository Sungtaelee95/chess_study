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
import view.InputView
import view.OutputView

class ChessGame(
    private val inputView: InputView,
    private val outputView: OutputView,
    private val boardManager: BoardManager,
) {
    private lateinit var turnColor: PieceColor
    private lateinit var board: Array<Array<Square>>

    init {
        gameSetUp()
    }

    fun start() {
        while (true) {
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
        board = boardManager.create()
        turnColor = PieceColor.WHITE
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
        board.forEach { line ->
            line.forEach {
                if (it.isEqualOfType(Type.King) && it.isEqualOfColor(turnColor)) return true
            }
        }
        return false
    }

    private fun turnChange() {
        turnColor = if (turnColor == PieceColor.WHITE) PieceColor.BLACK else PieceColor.WHITE
    }

    private fun movePiece(
        selectedSquare: SelectSquare,
        selectPosition: SelectPosition,
    ) {
        val oriNode = selectedSquare.getPositionNode()
        val newNode = selectPosition.selectedPosition()
        val oriSquare = board[oriNode.row][oriNode.col]
        board[oriNode.row][oriNode.col] = board[oriNode.row][oriNode.col].pollPiece()
        board[newNode.row][newNode.col] = board[newNode.row][newNode.col].update(oriSquare)

        println(board[oriNode.row][oriNode.col])
        println(board[newNode.row][newNode.col])
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
        val helperBoard = Array(board.size) { Array(board[0].size) { Square(null, BackGround.DARK) } }
        board.forEachIndexed { row, squares ->
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
            val input = inputView.inputMovePiecePosition(turnColor.colorName).split(",")
            val selectSquare = SelectSquare(input, board, turnColor)
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
    }
}

// 폰 색상으로 방향 조절 내일까지
// 수요일까지 게임 완성.
// 목요일부터 서버 작성.
// 코드스타일 정용복 대리님께 여쭤보기
// 쓰레드 사용하기,
// 프로토콜 사용하기
// 블랙팀은 서버에 1바이트씩 보내고 10밀리를 쉬어야함.
// 레드팀은 제한 없음.
