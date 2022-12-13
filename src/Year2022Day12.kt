fun main() {
    data class Node(
        val i: Int,
        val j: Int,
        val char: Char,
        var distanceToEnd: Int = Int.MAX_VALUE,
    ) {
        val value = when (char) {
            'S' -> 0
            'E' -> 'z' - 'a'
            else -> char - 'a'
        }
        fun isCanMoveTo(other: Node) =
            other.value - value == 1 || value >= other.value
    }

    val directions = arrayOf(
        Pair(1, 0),
        Pair(-1, 0),
        Pair(0, 1),
        Pair(0, -1),
    )

    fun solve(lines: List<String>): List<List<Node>> {
        val G = lines.mapIndexed{ i, row ->
            row.mapIndexed { j, char ->
                Node(i, j, char)
            }
        }
        val queue = ArrayDeque<Pair<Node, Int>>()
        val end = G.asSequence().flatten().first { it.char == 'E' }
        end.distanceToEnd = 0
        // BFS from end
        queue.addLast(Pair(end, 0))
        while (queue.isNotEmpty()) {
            val (now, distanceToEnd) = queue.removeFirst()
            if (now.distanceToEnd != distanceToEnd) continue
            for ((di, dj) in directions) {
                G.getOrNull(now.i + di)?.getOrNull(now.j + dj)?.let { next ->
                    if (next.isCanMoveTo(now) && next.distanceToEnd > now.distanceToEnd + 1) {
                        next.distanceToEnd = now.distanceToEnd + 1
                        queue.addLast(Pair(next, next.distanceToEnd))
                    }
                }
            }
        }
        return G
    }

    fun part1(lines: List<String>): Int {
        return solve(lines).asSequence().flatten().first { it.char == 'S' }.distanceToEnd
    }

    fun part2(lines: List<String>): Int {
        return solve(lines).asSequence().flatten().filter { it.value == 0 }.minOf { it.distanceToEnd }
    }

    val testLines = readLines(true)
    assertEquals(31, part1(testLines))
    assertEquals(29, part2(testLines))

    val lines = readLines()
    println(part1(lines))
    println(part2(lines))
}