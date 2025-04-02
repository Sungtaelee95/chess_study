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

//    fun sample() {
//        val reset = "\u001B[0m"
//        val red = "\u001B[31m"
//        val green = "\u001B[32m"
//        val blue = "\u001B[34m"
//
//        println("${red}Red text$reset")
//        println("${green}Green text$reset")
//        println("${blue}Blue text$reset")
//
//        val redBackground = "\u001B[41m" // 빨간색 배경
//
//        println("$redBackground ${blue}Red text $reset") // 한 칸의 빨간 배경
//    }
}
