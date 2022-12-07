import java.util.Stack

fun main() {
    fun handle(input: List<String>, handler: (Array<Stack<Char>>, Int, Int, Int) -> Unit): String {
        val borderLine = input.withIndex().first { (_, line) -> line.isBlank() }.index
        val stacks = Array<Stack<Char>>(input[borderLine - 1].count(Char::isDigit) + 1) { Stack() }
        for ((colIndex, value) in input[borderLine - 1].withIndex()) {
            if (value.isDigit()) {
                val id = value.digitToInt()
                for (rowIndex in borderLine - 1 downTo 0) {
                    input[rowIndex][colIndex]
                        .takeIf { it.isUpperCase() }
                        ?.run(stacks[id]::push)
                }
            }
        }
        val re = Regex("move (\\d+) from (\\d+) to (\\d+)")
        for (i in borderLine + 1 until input.size) {
            val result = re.find(input[i])!!
            val (num, from, to) = result.groupValues
                .withIndex().filter { it.index > 0 }.map { it.value.toInt() }
            handler(stacks, num, from, to)
        }
        return stacks.filter { it.isNotEmpty() }.map { it.peek() }.joinToString("")
    }

    fun part1(input: List<String>): String {
        return handle(input) { stacks, num, from, to ->
            for (i in 1..num) {
                stacks[to].push(stacks[from].pop())
            }
        }
    }

    fun part2(input: List<String>): String {
        val tempStack = Stack<Char>()
        return handle(input) { stacks, num, from, to ->
            for (i in 1..num) {
                tempStack.push(stacks[from].pop())
            }
            while (tempStack.isNotEmpty()) {
                stacks[to].push(tempStack.pop())
            }
        }
    }

    val testInput = readInput("Day05_test")
    check(part1(testInput) == "CMZ")
    check(part2(testInput) == "MCD")

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}