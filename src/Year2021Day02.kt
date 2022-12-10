import kotlin.math.abs

fun main() {
    fun part1(lines: List<String>): Int {
        var x = 0
        var y = 0
        for (line in lines) {
            val delta = line.substringAfter(" ").toInt()
            when (line[0]) {
                'f' -> x += delta
                'u' -> y += delta
                'd' -> y -= delta
            }
        }
        return abs(x * y)
    }

    fun part2(lines: List<String>): Int {
        var x = 0
        var y = 0
        var aim = 0
        for (line in lines) {
            val delta = line.substringAfter(" ").toInt()
            when (line[0]) {
                'f' -> {
                    y += aim * delta
                    x += delta
                }
                'd' -> aim += delta
                'u' -> aim -= delta
            }
        }
        return abs(x * y)
    }

    val testLines = readLines(true)
    assertEquals(150, part1(testLines))
    assertEquals(900, part2(testLines))

    val lines = readLines()
    println(part1(lines))
    println(part2(lines))
}