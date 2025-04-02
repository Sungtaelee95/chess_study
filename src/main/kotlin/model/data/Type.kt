package model.data

sealed class Type(
    val icon: String,
    val dire: List<Direction>,
    val attackDire: List<Direction>,
    val distance: Int,
) {
    data object King : Type(
        "♚",
        listOf(
            Direction.UP,
            Direction.DOWN,
            Direction.LEFT,
            Direction.RIGHT,
            Direction.UP_RIGHT,
            Direction.UP_LEFT,
            Direction.DOWN_RIGHT,
            Direction.DOWN_LEFT,
        ),
        listOf(
            Direction.UP,
            Direction.DOWN,
            Direction.LEFT,
            Direction.RIGHT,
            Direction.UP_RIGHT,
            Direction.UP_LEFT,
            Direction.DOWN_RIGHT,
            Direction.DOWN_LEFT,
        ),
        1,
    )

    data object Queen : Type(
        "♛",
        listOf(
            Direction.UP,
            Direction.DOWN,
            Direction.LEFT,
            Direction.RIGHT,
            Direction.UP_RIGHT,
            Direction.UP_LEFT,
            Direction.DOWN_RIGHT,
            Direction.DOWN_LEFT,
        ),
        listOf(
            Direction.UP,
            Direction.DOWN,
            Direction.LEFT,
            Direction.RIGHT,
            Direction.UP_RIGHT,
            Direction.UP_LEFT,
            Direction.DOWN_RIGHT,
            Direction.DOWN_LEFT,
        ),
        8,
    )

    data object Rook : Type(
        "♜",
        listOf(Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT),
        listOf(Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT),
        8,
    )

    data object Bishop : Type(
        "♝",
        listOf(Direction.UP_RIGHT, Direction.UP_LEFT, Direction.DOWN_RIGHT, Direction.DOWN_LEFT),
        listOf(Direction.UP_RIGHT, Direction.UP_LEFT, Direction.DOWN_RIGHT, Direction.DOWN_LEFT),
        8,
    )

    data object Knight : Type(
        "♞",
        listOf(
            Direction.UP1_LEFT2,
            Direction.UP1_RIGHT2,
            Direction.UP2_RIGHT1,
            Direction.UP2_LEFT1,
            Direction.DOWN1_LEFT2,
            Direction.DOWN1_RIGHT2,
            Direction.DOWN2_LEFT1,
            Direction.DOWN2_RIGHT1,
        ),
        listOf(
            Direction.UP1_LEFT2,
            Direction.UP1_RIGHT2,
            Direction.UP2_RIGHT1,
            Direction.UP2_LEFT1,
            Direction.DOWN1_LEFT2,
            Direction.DOWN1_RIGHT2,
            Direction.DOWN2_LEFT1,
            Direction.DOWN2_RIGHT1,
        ),
        1,
    )

    data object Pawn : Type(
        "♙",
        listOf(Direction.DOWN),
        listOf(Direction.DOWN_LEFT, Direction.DOWN_RIGHT),
        1,
    )
}

enum class Direction(
    val value: Node,
) {
    UP_LEFT(Node(-1, -1)),
    UP(Node(-1, 0)),
    UP_RIGHT(Node(-1, 1)),

    LEFT(Node(0, -1)),
    RIGHT(Node(0, 1)),

    DOWN_LEFT(Node(1, -1)),
    DOWN(Node(1, 0)),
    DOWN_RIGHT(Node(1, 1)),

    // 나이트
    UP1_LEFT2(Node(-1, -2)),
    UP1_RIGHT2(Node(-1, 2)),
    UP2_LEFT1(Node(-2, -1)),
    UP2_RIGHT1(Node(-2, 1)),

    DOWN1_LEFT2(Node(1, -2)),
    DOWN1_RIGHT2(Node(1, 2)),
    DOWN2_LEFT1(Node(2, -1)),
    DOWN2_RIGHT1(Node(2, 1)),
}

data class Node(
    val row: Int,
    val col: Int,
)
