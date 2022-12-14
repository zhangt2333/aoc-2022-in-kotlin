import kotlin.math.max
import kotlin.math.min

fun main() {
    val entrypoint = Point(0, 500)

    val directions = arrayOf(
        Point(+1, 0),
        Point(+1, -1),
        Point(+1, +1),
    )

    fun pointFallDown(now: Point, G: Set<Point>, borderLine: Int): Point {
        return directions.asSequence()
            .map { Point(now.x + it.x, now.y + it.y) }
            .filter { it !in G && it.x < borderLine }
            .firstOrNull() ?: now
    }

    fun drawLine(a: Point, b: Point, G: MutableSet<Point>) {
        for (x in min(a.x, b.x)..max(a.x, b.x)) {
            for (y in min(a.y, b.y)..max(a.y, b.y)) {
                G.add(Point(x, y))
            }
        }
    }

    fun parse(lines: List<String>): MutableSet<Point> {
        val G = mutableSetOf<Point>()
        for (line in lines) {
            line.splitToSequence(" -> ")
                .map {
                    val (y, x) = it.split(",")
                    Point(x.toInt(), y.toInt())
                }
                .zipWithNext()
                .forEach { (a, b) -> drawLine(a, b, G) }
        }
        return G
    }

    fun part1(lines: List<String>): Int {
        val G = parse(lines)
        val maxX = G.asSequence().map { it.x }.max()
        val originalPointNumber = G.size
        while (true) {
            var now = entrypoint.copy()
            while (true) {
                val nxt = pointFallDown(now, G, maxX + 1)
                if (nxt != now) now = nxt
                else break
            }
            if (now.x >= maxX) break
            G.add(now)
        }
        return G.size - originalPointNumber
    }

    fun part2(lines: List<String>): Int {
        val G = parse(lines)
        val borderLine = G.asSequence().map { it.x }.max() + 2
        val originalPointNumber = G.size
        while (entrypoint !in G) {
            var now = entrypoint.copy()
            while (true) {
                val nxt = pointFallDown(now, G, borderLine)
                if (nxt != now) now = nxt
                else break
            }
            G.add(now)
        }
        return G.size - originalPointNumber
    }

    val testLines = readLines(true)
    assertEquals(24, part1(testLines))
    assertEquals(93, part2(testLines))

    val lines = readLines()
    println(part1(lines))
    println(part2(lines))
}