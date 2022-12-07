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

    val testInput = readLines("Day04_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readLines("Day04")
    println(part1(input))
    println(part2(input))
}
