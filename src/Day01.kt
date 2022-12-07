import java.util.PriorityQueue

fun main() {
    fun part1(input: List<String>): Int {
        var maxSum = 0
        var currentSum = 0
        for (line in input) {
            if (line.isBlank()) {
                maxSum = maxSum.coerceAtLeast(currentSum)
                currentSum = 0
            } else {
                currentSum += line.toInt()
            }
        }
        return maxSum
    }

    fun part2(input: List<String>): Int {
        val priorityQueue = PriorityQueue<Int>()
        var currentSum = 0
        for (line in input) {
            if (line.isBlank()) {
                priorityQueue.offer(currentSum)
                if (priorityQueue.size > 3) {
                    priorityQueue.poll()
                }
                currentSum = 0
            } else {
                currentSum += line.toInt()
            }
        }
        return priorityQueue.sum()
    }

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
