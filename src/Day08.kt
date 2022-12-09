internal val <E> List<List<E>>.rowSize get() = size
internal val <E> List<List<E>>.columnSize get() = first().size

internal inline fun <T> Iterable<T>.countDoWhile(predicate: (T) -> Boolean): Int {
    var counter = 0
    for (item in this) {
        counter++
        if (!predicate(item)) return counter
    }
    return counter
}

fun main() {

    fun part1(input: List<String>): Int {
        val G = input.map { it.map { c -> c.digitToInt() }.toList() }

        fun isVisible(i: Int, j: Int): Boolean {
            val top = (0 until i).reversed()
            val down = i + 1 until G.rowSize
            val left = (0 until j).reversed()
            val right = j + 1 until G.columnSize
            return top.all { G[i][j] > G[it][j] }
                    || down.all { G[i][j] > G[it][j] }
                    || left.all { G[i][j] > G[i][it] }
                    || right.all { G[i][j] > G[i][it] }
        }

        return sequence {
            for (i in 0 until G.rowSize) {
                for (j in 0 until G.columnSize) {
                    yield(isVisible(i, j))
                }
            }
        }.count { it }
    }

    fun part2(input: List<String>): Int {
        val G = input.map { it.map { it.digitToInt() }.toList() }

        fun score(i: Int, j: Int): Int {
            val top = (0 until i).reversed()
            val down = i + 1 until G.rowSize
            val left = (0 until j).reversed()
            val right = j + 1 until G.columnSize

            return top.countDoWhile { G[i][j] > G[it][j] } *
                    down.countDoWhile { G[i][j] > G[it][j] } *
                    left.countDoWhile { G[i][j] > G[i][it] } *
                    right.countDoWhile { G[i][j] > G[i][it] }
        }

        return sequence {
            for (i in 0 until G.rowSize) {
                for (j in 0 until G.columnSize) {
                    yield(score(i, j))
                }
            }
        }.max()
    }

    val testInput = readLines("Day08_test")
    check(part1(testInput) == 21)
    check(part2(testInput) == 8)

    val input = readLines("Day08")
    println(part1(input))
    println(part2(input))
}
