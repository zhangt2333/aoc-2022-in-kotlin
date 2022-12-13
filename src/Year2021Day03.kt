// https://github.com/Mistborn94/advent-of-code-2021/blob/main/src/main/kotlin/day3/Day3.kt
fun main() {
    fun bitmask(size: Int) = 2.pow(size) - 1

    fun part1(lines: List<String>): Int {
        val gamma = lines
            .map { it.toList().map(Char::digitToInt) }
            .reduce { l1, l2 -> l1.zip(l2, Int::plus) }
            .map { if (it >= lines.size - it) 1 else 0 }
            .digitsToInt(2)
        return gamma * (gamma.inv() and bitmask(lines.first().length))
    }

    tailrec fun calcRating(G: List<List<Int>>, focusBit: Int, colIndex: Int = 0): Int {
        return if (G.size == 1) {
            G.first().digitsToInt(2)
        } else {
            val numberOfOne = G.sumOf { it[colIndex] }
            val comparisonBit = if (numberOfOne >= G.size - numberOfOne) focusBit else 1 - focusBit
            calcRating(G.filter { it[colIndex] == comparisonBit }, focusBit, colIndex + 1)
        }
    }

    fun part2(lines: List<String>): Int {
        val G = lines.map { it.toList().map(Char::digitToInt) }
        return calcRating(G, 1) * calcRating(G, 0)
    }

    val testLines = readLines(true)
    assertEquals(198, part1(testLines))
    assertEquals(230, part2(testLines))

    val lines = readLines()
    println(part1(lines))
    println(part2(lines))
}