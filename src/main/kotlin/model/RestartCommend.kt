package model

import kotlin.text.toInt
import kotlin.text.toIntOrNull

class RestartCommend(
    private val input: String
) {
    init {
        require(input.length == 1) { "공백없이 0 혹은 1 만 입력해주세요." }
        require(input.toIntOrNull() != null) { "0(종료) 혹은 1(재시작) 숫자를 입력해주세요." }
        require(input.toInt() in 0..1) { "0 혹은 1 을 입력해주세요. 0: 종료, 1: 재시작" }
    }

    fun isRestart(): Boolean {
        return input.toInt() == 1
    }
}