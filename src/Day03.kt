import java.util.BitSet

fun main() {

    fun Char.priority(): Int = when (this) {
        in 'a'..'z' -> this - 'a' + 1
        in 'A'..'Z' -> this - 'A' + 27
        else -> throw IllegalArgumentException()
    }

    fun indexBitSet(items: String) = BitSet(60).apply {
        items.forEach { this[it.priority()] = true }
    }

    fun part1(input: List<String>): Int {
        return input.sumOf {
            indexBitSet(it.substring(0, it.length / 2)).apply {
                this.and(indexBitSet(it.substring(it.length / 2)))
            }.nextSetBit(0)
        }
    }

    fun part2(input: List<String>): Int {
        return input.map { indexBitSet(it) }.chunked(3).sumOf { (a, b, c) ->
                a.and(b)
                a.and(c)
                a.nextSetBit(0)
            }
    }

    val testInput = readLines("Day03_test")
    check(part1(testInput) == 157)
    check(part2(testInput) == 70)

    val input = readLines("Day03")
    println(part1(input))
    println(part2(input))
}
