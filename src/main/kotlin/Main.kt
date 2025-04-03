import controller.ChessGame
import util.BoardManager
import util.DelayServerManager
import view.InputView
import view.OutputView
import java.net.Socket

fun main() {
    val server = Socket("127.0.0.1", 33769)
//     일반
//    ChessGame(InputView(), OutputView(), BoardManager(), ServerManager(server)).start()
//     딜레이
    ChessGame(InputView(), OutputView(), BoardManager(), DelayServerManager(server)).start()
}

