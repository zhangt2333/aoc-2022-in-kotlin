fun main() {

    fun part1(input: List<String>): Int {
        val targetCycles = (20..220 step 40)
        var x = 1
        var cycle = 1
        var ans = 0

        fun dida() {
            if (++cycle in targetCycles)
                ans += cycle * x
        }

        for (line in input) {
            if (line.startsWith("addx")) {
                dida()
                x += line.substringAfter(" ").toInt()
                dida()
            } else {
                dida()
            }
        }
        return ans
    }

    fun part2(input: List<String>) {
        val g = BooleanArray(240)
        var x = 1
        var cycle = 1
        g[0] = true

        fun dida() {
            if ((++cycle - 1) % 40 in (x - 1..x + 1))
                g[cycle - 1] = true
        }

        for (line in input) {
            if (line.startsWith("addx")) {
                dida()
                x += line.substringAfter(" ").toInt()
                dida()
            } else {
                dida()
            }
        }
        for (i in (1..240)) {
            print(if (g[i - 1]) '#' else '.')
            if (i % 40 == 0) println()
        }
    }

    check(part1(readLines("Day10_test")) == 13140)
    part2(readLines("Day10_test"))

    val input = readLines("Day10")
    println(part1(input))
    part2(input)
}
