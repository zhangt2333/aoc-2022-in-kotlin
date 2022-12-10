import kotlin.math.abs

internal data class SubmarinePosition(
    var x: Int,
    var y: Int,
    var aim: Int,
)

fun main() {
    fun parse(lines: List<String>) = lines.map {
        val (direction, distance) = it.split(" ")
        direction to distance.toInt()
    }

    fun part1(lines: List<String>): Int {
        return parse(lines)
            .fold(Point(0, 0)) { p, (direction, distance) ->
                when (direction) {
                    "forward" -> p.x += distance
                    "up" -> p.y += distance
                    "down" -> p.y -= distance
                }
                p
            }.let { abs(it.x * it.y) }
    }

    fun part2(lines: List<String>): Int {
        return parse(lines)
            .fold(SubmarinePosition(0, 0, 0)) { p, (direction, distance) ->
                when (direction) {
                    "forward" -> {
                        p.y += p.aim * distance
                        p.x += distance
                    }
                    "down" -> p.aim += distance
                    "up" -> p.aim -= distance
                }
                p
            }.let { abs(it.x * it.y) }
    }

    val testLines = readLines(true)
    assertEquals(150, part1(testLines))
    assertEquals(900, part2(testLines))

    val lines = readLines()
    println(part1(lines))
    println(part2(lines))
}