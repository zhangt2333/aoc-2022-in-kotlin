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

    val testLines = readLines(true)
    check(part1(testLines) == 157)
    check(part2(testLines) == 70)

    val lines = readLines()
    println(part1(lines))
    println(part2(lines))
}
