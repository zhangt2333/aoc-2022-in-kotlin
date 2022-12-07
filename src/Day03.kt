fun main() {
    fun part1(input: List<String>): Int {
        var sum = 0
        for (line in input) {
            val visited = BooleanArray(60)
            for ((index, value) in line.withIndex()) {
                val v = if (value >= 'a') value - 'a' + 1 else value - 'A' + 27
                if (index < line.length / 2) {
                    visited[v] = true
                } else if (visited[v]) {
                    sum += v
                    break
                }
            }
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        var sum = 0
        for (index in input.indices.step(3)) {
            val c = (input[index].toSet() intersect input[index + 1].toSet()
                    intersect input[index + 2].toSet()).first()
            sum += if (c >= 'a') c - 'a' + 1 else c - 'A' + 27
        }
        return sum
    }

    val testInput = readInput("Day03_test")
    check(part1(testInput) == 157)
    check(part2(testInput) == 70)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
