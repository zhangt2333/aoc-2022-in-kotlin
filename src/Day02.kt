enum class HandShape {
    ROCK, PAPER, SCISSOR;

    companion object {
        fun of(char: Char): HandShape = when (char) {
            'A', 'X' -> ROCK
            'B', 'Y' -> PAPER
            'C', 'Z' -> SCISSOR
            else -> throw IllegalArgumentException()
        }

        fun of(myResult: Result, opponentHand: HandShape): HandShape = when (opponentHand) {
            ROCK -> when (myResult) {
                Result.LOST -> SCISSOR
                Result.DRAW -> ROCK
                Result.WON -> PAPER
            }

            PAPER -> when (myResult) {
                Result.LOST -> ROCK
                Result.DRAW -> PAPER
                Result.WON -> SCISSOR
            }

            SCISSOR -> when (myResult) {
                Result.LOST -> PAPER
                Result.DRAW -> SCISSOR
                Result.WON -> ROCK
            }
        }
    }

    fun score(): Int = when (this) {
        ROCK -> 1
        PAPER -> 2
        SCISSOR -> 3
    }
}

enum class Result {
    LOST, DRAW, WON;

    companion object {
        fun of(char: Char): Result = when (char) {
            'X' -> LOST
            'Y' -> DRAW
            'Z' -> WON
            else -> throw IllegalArgumentException()
        }

        fun of(myHand: HandShape, opponentHand: HandShape): Result = when (myHand) {
            HandShape.ROCK -> when (opponentHand) {
                HandShape.ROCK -> DRAW
                HandShape.PAPER -> LOST
                HandShape.SCISSOR -> WON
            }

            HandShape.PAPER -> when (opponentHand) {
                HandShape.ROCK -> WON
                HandShape.PAPER -> DRAW
                HandShape.SCISSOR -> LOST
            }

            HandShape.SCISSOR -> when (opponentHand) {
                HandShape.ROCK -> LOST
                HandShape.PAPER -> WON
                HandShape.SCISSOR -> DRAW
            }
        }
    }

    fun score(): Int = when (this) {
        LOST -> 0
        DRAW -> 3
        WON -> 6
    }
}

fun main() {

    fun part1(input: List<String>): Int {
        return input.sumOf {
            val opponentHand = HandShape.of(it[0])
            val myHand = HandShape.of(it[2])
            Result.of(myHand, opponentHand).score() + myHand.score()
        }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf {
            val opponentHand = HandShape.of(it[0])
            val myResult = Result.of(it[2])
            HandShape.of(myResult, opponentHand).score() + myResult.score()
        }
    }

    val testInput = readLines("Day02_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 12)

    val input = readLines("Day02")
    println(part1(input))
    println(part2(input))
}
