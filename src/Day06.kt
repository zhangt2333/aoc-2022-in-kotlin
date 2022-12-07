
fun main() {
    fun handle(line: List<Int>, size: Int): Int {
        val counter = IntArray(26)
        for (index in line.indices) {
            if (index <= size - 1) {
                counter[line[index]]++
            } else {
                val distinct = IntRange(index - size, index - 1)
                    .map { counter[line[it]] }
                    .count { it == 1 }
                if (distinct == size) {
                    return index
                }
                counter[line[index - size]]--
                counter[line[index]]++
            }
        }
        return line.size
    }

    fun part1(input: List<String>): Int {
        val line = input[0].map { it - 'a' }
        return handle(line, 4)
    }

    fun part2(input: List<String>): Int {
        val line = input[0].map { it - 'a' }
        return handle(line, 14)
    }

    val testInput = readInput("Day06_test")
    check(part1(testInput) == 6)
    check(part2(testInput) == 23)

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}
