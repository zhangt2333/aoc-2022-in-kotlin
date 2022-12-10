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

        fun of(myHand: HandShape, opponentHand: HandShape): Result = when (myHand to opponentHand) {
            HandShape.ROCK to HandShape.SCISSOR, HandShape.PAPER to HandShape.ROCK, HandShape.SCISSOR to HandShape.PAPER -> WON
            HandShape.ROCK to HandShape.ROCK, HandShape.PAPER to HandShape.PAPER, HandShape.SCISSOR to HandShape.SCISSOR -> DRAW
            HandShape.ROCK to HandShape.PAPER, HandShape.PAPER to HandShape.SCISSOR, HandShape.SCISSOR to HandShape.ROCK -> LOST
            else -> throw java.lang.IllegalArgumentException()
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

    val testLines = readLines(true)
    check(part1(testLines) == 15)
    check(part2(testLines) == 12)

    val lines = readLines()
    println(part1(lines))
    println(part2(lines))
}
