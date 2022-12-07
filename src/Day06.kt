fun main() {
    fun handle(line: String, size: Int): Int {
        return size + line.windowed(size).indexOfFirst { it.toSet().size == size }
    }

    fun part1(input: String): Int {
        return handle(input, 4)
    }

    fun part2(input: String): Int {
        return handle(input, 14)
    }

    val testInput = readInputs("Day06_test")
    check(part1(testInput) == 6)
    check(part2(testInput) == 23)

    val input = readInputs("Day06")
    println(part1(input))
    println(part2(input))
}