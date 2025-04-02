package model.data

enum class BackGround(
    val color: String,
) {
    DARK("\u001B[42m"),
    WHITE("\u001B[103m"),
    ASSISTANT("\u001B[105m"),
}
