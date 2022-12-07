fun main() {
    fun part1(input: List<String>): Int {
        val table = Array(4) { IntArray(4) }
        table[1][1] = 3
        table[1][2] = 0
        table[1][3] = 6
        table[2][1] = 6
        table[2][2] = 3
        table[2][3] = 0
        table[3][1] = 0
        table[3][2] = 6
        table[3][3] = 3
        var sum = 0
        for (line in input) {
            val v1 = line[0] - 'A' + 1
            val v2 = line[2] - 'X' + 1
            sum += table[v2][v1] + v2
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        val table = Array(4) { IntArray(7) }
        table[1][0] = 3
        table[1][3] = 1
        table[1][6] = 2
        table[2][0] = 1
        table[2][3] = 2
        table[2][6] = 3
        table[3][0] = 2
        table[3][3] = 3
        table[3][6] = 1
        var sum = 0
        for (line in input) {
            val v1 = line[0] - 'A' + 1
            val v2 = (line[2] - 'X') * 3
            sum += table[v1][v2] + v2
        }
        return sum
    }

    val testInput = readInput("Day02_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 12)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
