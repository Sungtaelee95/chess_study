import controller.ChessGame
import util.BoardManager
import view.InputView
import view.OutputView

fun main() {
    ChessGame(InputView(), OutputView(), BoardManager()).start()
}
