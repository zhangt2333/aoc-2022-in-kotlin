import kotlin.math.abs

fun main() {
    val re = Regex("Sensor at x=(.*), y=(.*): closest beacon is at x=(.*), y=(.*)")

    data class Sensor(
        val x: Int,
        val y: Int,
        val r: Int, // scanningDistance
    ) {
        fun canDiscover(x: Int, y: Int): Boolean =
            abs(this.x - x) + abs(this.y - y) <= this.r
    }

    data class Beacon(
        val x: Int,
        val y: Int,
    )

    fun parse(lines: List<String>): Pair<MutableList<Sensor>, MutableSet<Beacon>> {
        val sensors = mutableListOf<Sensor>()
        val beacons = mutableSetOf<Beacon>()
        for (line in lines) {
            val (sx, sy, bx, by) = re.matchEntire(line)!!.destructured
                .toList().map { it.toInt() }
            sensors.add(Sensor(sx, sy, abs(sx - bx) + abs(sy - by)))
            beacons.add(Beacon(bx, by))
        }
        return sensors to beacons
    }

    fun part1(lines: List<String>, expectedY: Int): Int {
        val (sensors, beacons) = parse(lines)

        var minX = 0
        var maxX = 0
        sensors.forEach {
            val dx = it.r - abs(it.y - expectedY)
            if (dx >= 0) {
                minX = minX.coerceAtMost(it.x - dx)
                maxX = maxX.coerceAtLeast(it.x + dx)
            }
        }

        return (minX..maxX).count { x ->
            sensors.any { it.canDiscover(x, expectedY) } &&
                    Beacon(x, expectedY) !in beacons
        }
    }

    fun part2(lines: List<String>): Long {
        val (sensors, _) = parse(lines)

        val minY = sensors.minOf { it.y }.coerceAtLeast(0)
        val maxY = sensors.maxOf { it.y }.coerceAtMost(4000000)

        for (y in minY..maxY) {
            val segments = sensors.asSequence().mapNotNull {
                val dx = it.r - abs(it.y - y)
                if (dx >= 0) Pair(it.x - dx, it.x + dx)
                else null
            }.sortedWith(compareBy({ it.first }, { it.second }))

            var last = 0
            segments.forEach {
                if (it.first > last) return (last + 1) * 4000000L + y
                last = last.coerceAtLeast(it.second)
            }
        }
        return -1
    }

    val testLines = readLines(true)
    assertEquals(26, part1(testLines, 10))
    assertEquals(56000011L, part2(testLines))

    val lines = readLines()
    println(part1(lines, 2000000))
    println(part2(lines))
}