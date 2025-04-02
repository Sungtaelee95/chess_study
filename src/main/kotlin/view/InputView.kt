package view

import java.io.BufferedReader
import java.io.InputStreamReader

class InputView {
    private val br = BufferedReader(InputStreamReader(System.`in`))

    fun inputMovePiecePosition(colorName: String): String {
        println("움직이실 $colorName 말의 행과 열을','로 구분하여 입력해주세요. ex) 2,5")
        return inputCommend()
    }

    fun inputCommend(): String {
        print("입력: ")
        return readCommend()
    }

    private fun readCommend(): String = br.readLine()
}
