package view

class OutputView {
    private val reset = "\u001B[0m"

    fun printTurnWaitMessage() {
        println("상대방의 입력을 기다리고 있습니다.")
    }

    fun printRestartMessage() {
        println("상대방의 왕을 잡아 게임이 종료되었습니다.\n다시 시작하려면 1, 종료하시려면 0을 입력해주세요.")
    }

    fun printHelperMessage() = println("진행하고 싶은 방향을 확인하고 번호를 입력해주세요.\n0을 입력하면 이전으로 돌아갑니다.")

    fun printNextNodes(
        index: Int,
        row: Int,
        col: Int,
    ) {
        println("${index + 1}번 ${row + 1}행 ${col + 1}열 로 이동")
    }

    fun printEmptyNextNodes() {
        println("이동 가능한 방향이 없습니다. 0을 눌러 다시 선택해주세요.")
    }

    fun printSquare(
        icon: String,
        iconColor: String,
        bgColor: String,
    ) {
        val content = String.format("%-2s", icon)
        print("$bgColor$iconColor$content$reset")
    }

    fun printRowIndex(index: Int) {
        print(String.format("%-2s", index + 1))
    }

    fun printColumnIndex() {
        print(" ")
        for (i in 1..8) {
            print(String.format("%2d", i))
        }
    }

    fun printError(message: String?) {
        message?.let {
            println(message)
        } ?: println("예기치 못한 예외상황이 발생하였습니다.")
    }

    fun println() {
        print("\n")
    }

    fun clear() {
        ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor()
    }
}
