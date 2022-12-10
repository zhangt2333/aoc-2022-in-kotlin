fun main() {
    operator fun IntRange.contains(other: IntRange): Boolean =
        first <= other.first && other.last <= last

    fun IntRange.overlap(other: IntRange): Boolean =
        !(other.last < first || last < other.first)

    fun part1(input: List<String>): Int {
        return input.map {
            val (l1, r1, l2, r2) = it.split(",", "-").map(String::toInt)
            Pair(l1..r1, l2..r2)
        }.count { (a, b) -> a in b || b in a }
    }

    fun part2(input: List<String>): Int {
        return input.map {
            val (l1, r1, l2, r2) = it.split(",", "-").map(String::toInt)
            Pair(l1..r1, l2..r2)
        }.count { (a, b) -> a.overlap(b)}
    }

    val testLines = readLines(true)
    check(part1(testLines) == 2)
    check(part2(testLines) == 4)

    val lines = readLines()
    println(part1(lines))
    println(part2(lines))
}
