fun main() {
    fun part1(lines: List<String>): Int {
        return lines.asSequence()
            .map { it.toInt() }
            .zipWithNext()
            .count { (a, b) -> b > a }
    }

    fun part2(lines: List<String>): Int {
        return lines.asSequence()
            .map { it.toInt() }
            .windowed(3)
            .map { it.sum() }
            .zipWithNext()
            .count { (a, b) -> b > a }
    }

    val testLines = readLines(true)
    assertEquals(7, part1(testLines))
    assertEquals(5, part2(testLines))

    val lines = readLines()
    println(part1(lines))
    println(part2(lines))
}