import java.util.IdentityHashMap
import kotlin.math.min
import kotlin.math.sign

fun main() {
    fun parse(line: String): List<Any> {
        val parentMap = IdentityHashMap<MutableList<Any>, MutableList<Any>?>()
        var currentList: MutableList<Any>? = null
        var index = 0
        while(index < line.length) {
            when (line[index]) {
                '[' -> {
                    val newList = mutableListOf<Any>()
                    parentMap[newList] = currentList
                    currentList?.add(newList )
                    currentList = newList
                }
                ']' -> {
                    currentList = parentMap[currentList]
                }
                in '0'..'9' -> {
                    val endIndex = line.indexOfFirst(index) { !it.isDigit() }
                    currentList?.add(line.substring(index, endIndex).toInt())
                    index = endIndex
                }
                ',' -> {}
                else -> throw IllegalArgumentException()
            }
            index++
        }
        return parentMap.firstNotNullOf { (key, value) -> if (value == null) key else null }
    }

    fun compare(o1: Any, o2: Any): Int {
        if (o1 is Int && o2 is Int) return o1.compareTo(o2)
        val l1 = o1 as? List<*> ?: listOf(o1)
        val l2 = o2 as? List<*> ?: listOf(o2)
        for (i in 0 until min(l1.size, l2.size)) {
            compare(l1[i]!!, l2[i]!!).let { if (it != 0) return it }
        }
        return (l1.size - l2.size).sign
    }

    fun part1(lines: List<String>): Int {
        var ans = 0
        for (i in lines.indices step 3) {
            val l1 = parse(lines[i])
            val l2 = parse(lines[i + 1])
            if (compare(l1, l2) <= 0) {
                ans += i / 3 + 1
            }
        }
        return ans
    }

    fun part2(lines: List<String>): Int {
        val l = lines.asSequence().filter { it.isNotBlank() }.map { parse(it) }.toMutableList()
        val l1 = parse("[[2]]")
        val l2 = parse("[[6]]")
        l.add(l1)
        l.add(l2)
        l.sortWith(::compare)
        return (l.indexOfFirst { it === l1 } + 1) * (l.indexOfFirst { it === l2 } + 1)
    }

    val testLines = readLines(true)
    assertEquals(13, part1(testLines))
    assertEquals(140, part2(testLines))

    val lines = readLines()
    println(part1(lines))
    println(part2(lines))
}