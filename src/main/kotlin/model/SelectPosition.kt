package model

import model.data.Node

class SelectPosition(
    private val input: String,
    private val positions: List<Node>,
) {
    init {
        require(input.toIntOrNull() != null) { "숫자로 입력해주세요." }
        require(input.toInt() in 0..positions.size) { "유효하지 않은 번호를 입력하셨습니다. 확인 후 다시 입력해주세요." }
    }

    fun isRecommend() = input.toInt() == 0

    fun getPositionNode() = positions[input.toInt() - 1]
}
