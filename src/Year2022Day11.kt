internal class Monkey(text: String) {
    val items: ArrayDeque<Long>
    val operator: Char
    val operand: String
    val test: Int
    val trueTo: Int
    val falseTo: Int
    var inspectTimes: Int = 0

    init {
        val lines = text.split("\n")
        items = lines[1].substringAfter(": ")
            .split(", ").map { it.toLong() }.let { ArrayDeque(it) }
        operator = lines[2].substringAfter("old ").substringBefore(" ")[0]
        operand = lines[2].substringAfterLast(" ")
        test = lines[3].substringAfter("by ").toInt()
        trueTo = lines[4].substringAfter("monkey ").toInt()
        falseTo = lines[5].substringAfter("monkey ").toInt()
    }

    inline fun inspect(allMonkeys: List<Monkey>, worryLevelFunction: (Long) -> Long) {
        while (items.isNotEmpty()) {
            inspectTimes++
            val old = items.removeFirst()
            val operand = if (operand == "old") old else operand.toLong()
            val new = when (operator) {
                '*' -> old * operand
                '+' -> old + operand
                else -> throw IllegalArgumentException()
            }.let(worryLevelFunction)
            if (new % test == 0L) {
                allMonkeys[trueTo].items.addLast(new)
            } else {
                allMonkeys[falseTo].items.addLast(new)
            }
        }
    }
}

fun main() {
    fun part1(text: String): Long {
        val monkeys = text.splitToSequence("\n\n").map { Monkey(it) }.toList()
        repeat(20) {
            for (monkey in monkeys) {
                monkey.inspect(monkeys) { worryLevel -> worryLevel / 3 }
            }
        }
        return monkeys.sortedByDescending { it.inspectTimes }
            .take(2).fold(1L) { acc, monkey -> acc * monkey.inspectTimes }
    }

    fun part2(text: String): Long {
        val monkeys = text.splitToSequence("\n\n").map { Monkey(it) }.toList()
        val mod = monkeys.fold(1L) { acc, mon -> acc * mon.test }
        repeat(10000) {
            for (monkey in monkeys) {
                monkey.inspect(monkeys) { worryLevel -> worryLevel % mod }
            }
        }
        return monkeys.sortedByDescending { it.inspectTimes }
            .take(2).fold(1L) { acc, monkey -> acc * monkey.inspectTimes }
    }

    val testText = readText(true)
    assertEquals(10605L, part1(testText))
    assertEquals(2713310158L, part2(testText))

    val text = readText()
    println(part1(text))
    println(part2(text))
}