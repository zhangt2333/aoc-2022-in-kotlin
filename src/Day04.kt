fun main() {
    fun part1(input: List<String>): Int {
        var sum = 0
        for (line in input) {
            val (v1,v2,v3,v4) = line.split(",", "-").map(String::toInt)
            if ((v1 <= v3 && v3 <= v4 && v4 <= v2)
                || (v3 <= v1 && v1 <= v2 && v2 <= v4)) {
                sum++
            }
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        var sum = 0
        for (line in input) {
            val (v1,v2,v3,v4) = line.split(",", "-").map(String::toInt)
            if (!(v2 < v3 || v4 < v1)) {
                sum++
            }
        }
        return sum
    }

    val testInput = readInput("Day04_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}
