package model.data

import java.nio.ByteBuffer

data class MoveInformation(
    val oriNode: Node,
    val newNode: Node
) {
    fun toByteArray(): ByteArray {
        val oriPair = oriNode.getByteArray()
        val newPair = newNode.getByteArray()
        return oriPair + newPair
    }

    companion object {
        fun fromByteArray(array: ByteArray): MoveInformation {
            println("fromByteArray, arraySize: ${array.size}")
            val buffer = ByteBuffer.wrap(array)
            return MoveInformation(Node(buffer.getInt(), buffer.getInt()), Node(buffer.getInt(), buffer.getInt()))
        }
    }
}