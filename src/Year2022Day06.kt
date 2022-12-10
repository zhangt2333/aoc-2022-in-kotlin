fun main() {
    fun String.allUnique(): Boolean {
        val set = mutableSetOf<Char>()
        return all { set.add(it) }
    }

    fun handle(line: String, size: Int): Int {
        return size + line.windowed(size).indexOfFirst { it.allUnique() }
    }

    fun part1(input: String): Int {
        return handle(input, 4)
    }

    fun part2(input: String): Int {
        return handle(input, 14)
    }

    val testText = readText(true)
    check(part1(testText) == 6)
    check(part2(testText) == 23)

    val text = readText()
    println(part1(text))
    println(part2(text))
}
