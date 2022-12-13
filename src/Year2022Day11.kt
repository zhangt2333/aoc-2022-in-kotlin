private class Monkey(text: String) {
    val itemWorryLevels: MutableList<Long>
    val operation: (Long) -> Long
    val testDivisor: Int
    val trueTarget: Int
    val falseTarget: Int
    var inspectTimes: Long = 0

    init {
        val lines = text.split("\n")
        itemWorryLevels = lines[1].substringAfter(": ")
            .split(", ").map { it.toLong() }.let { ArrayDeque(it) }
        val operator = lines[2].substringAfter("old ").substringBefore(" ")[0]
        val operand = lines[2].substringAfterLast(" ")
        operation = when (operator) {
            '*' -> { old -> old * (operand.toLongOrNull() ?: old)}
            '+' -> { old -> old + (operand.toLongOrNull() ?: old)}
            else -> throw IllegalArgumentException()
        }
        testDivisor = lines[3].substringAfter("by ").toInt()
        trueTarget = lines[4].substringAfter("monkey ").toInt()
        falseTarget = lines[5].substringAfter("monkey ").toInt()
    }

    inline fun inspect(allMonkeys: List<Monkey>, worryLevelFunction: (Long) -> Long) {
        while (itemWorryLevels.isNotEmpty()) {
            inspectTimes++
            val worryLevel = itemWorryLevels.removeFirst()
                .let(operation).let(worryLevelFunction)
            if (worryLevel % testDivisor == 0L) {
                allMonkeys[trueTarget].itemWorryLevels.add(worryLevel)
            } else {
                allMonkeys[falseTarget].itemWorryLevels.add(worryLevel)
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
        return monkeys.asSequence().map { it.inspectTimes }.sortedDescending().take(2).product()
    }

    fun part2(text: String): Long {
        val monkeys = text.splitToSequence("\n\n").map { Monkey(it) }.toList()
        val mod = monkeys.asSequence().map { it.testDivisor }.lcm()
        repeat(10000) {
            for (monkey in monkeys) {
                monkey.inspect(monkeys) { worryLevel -> worryLevel % mod }
            }
        }
        return monkeys.asSequence().map { it.inspectTimes }.sortedDescending().take(2).product()
    }

    val testText = readText(true)
    assertEquals(10605L, part1(testText))
    assertEquals(2713310158L, part2(testText))

    val text = readText()
    println(part1(text))
    println(part2(text))
}