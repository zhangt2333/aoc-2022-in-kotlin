fun main() {
    // O(size * log size) -> O(size * log n) -> O(size)
    fun List<Int>.topN(n: Int): List<Int> {
        if (this.size == n) return this
        val x = this.random()
        val small = this.filter { it < x }
        val equal = this.filter { it == x }
        val big = this.filter { it > x }
        if (big.size >= n) return big.topN(n)
        if (equal.size + big.size >= n) return (equal + big).takeLast(n)
        return small.topN(n - equal.size - big.size) + equal + big
    }

    fun part1(input: String): Int {
        val data = input.split("\n\n").map { it.lines().sumOf(String::toInt) }
        return data.topN(1).sum()
    }

    fun part2(input: String): Int {
        val data = input.split("\n\n").map { it.lines().sumOf(String::toInt) }
        return data.topN(3).sum()
    }

    val input = readInputs("Day01")

    println(part1(input))
    println(part2(input))
}
