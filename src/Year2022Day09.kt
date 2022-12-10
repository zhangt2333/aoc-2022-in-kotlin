import kotlin.math.abs
import kotlin.math.sign

data class Point(
    var x: Int,
    var y: Int
) {
    fun move(char: Char) {
        when (char) {
            'U' -> y++
            'D' -> y--
            'L' -> x--
            'R' -> x++
        }
    }

    fun follow(head: Point) {
        val dx = head.x - x
        val dy = head.y - y
        if (abs(dx) > 1) {
            x += dx - dx.sign
            if (abs(dy) == 1) y += dy.sign
        }
        if (abs(dy) > 1) {
            y += dy - dy.sign
            if (abs(dx) == 1) x += dx.sign
        }
    }
}

fun main() {

    fun solve(input: List<String>, ropeSize: Int): Int {
        val visited = mutableSetOf<Point>()
        val ropes = Array(ropeSize) { Point(0, 0) }
        visited += ropes.last().copy()
        for (line in input) {
            val direction = line[0]
            val step = line.substring(2).toInt()
            repeat(step) {
                for ((i, rope) in ropes.withIndex()) {
                    if (i == 0) rope.move(direction)
                    else rope.follow(ropes[i - 1])
                }
                visited += ropes.last().copy()
            }
        }
        return visited.size
    }

    fun part1(input: List<String>): Int {
        return solve(input, 2)
    }

    fun part2(input: List<String>): Int {
        return solve(input, 10)
    }

    check(part1(readLines(true)) == 13)
    check(part2(readLines(true)) == 1)
    check(part2(readLines(true, "_test2")) == 36)

    val lines = readLines()
    println(part1(lines))
    println(part2(lines))
}
