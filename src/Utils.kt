import java.io.File
import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path

fun readLines(isTest: Boolean = false, testSuffix: String ="_test") =
    getFile(isTest, testSuffix).readLines()

fun readText(isTest: Boolean = false, testSuffix: String ="_test") =
    getFile(isTest, testSuffix).readText()

internal fun getFile(isTest: Boolean = false,
                     testSuffix: String ="_test",
                     yearAndDay: Pair<String, String> = getYearAndDay()): File =
    yearAndDay.let { (year, day) ->
        Path("input", year, "Day$day${if (isTest) testSuffix else ""}.txt").toFile()
    }

internal fun CharSequence.indexAfter(string: String, startIndex: Int = 0, ignoreCase: Boolean = false): Int =
    indexOf(string, startIndex, ignoreCase).let { return if (it == -1) -1 else it + string.length }

inline fun CharSequence.indexOfFirst(startIndex: Int = 0, predicate: (Char) -> Boolean): Int {
    for (index in startIndex until  this.length) {
        if (predicate(this[index])) {
            return index
        }
    }
    return -1
}

fun getYearAndDay(): Pair<String, String> {
    val mainClassName = Thread.currentThread().stackTrace.last().className
    val yearStartIndex = mainClassName.indexAfter("Year")
    val yearEndIndex = mainClassName.indexOfFirst(yearStartIndex) { !it.isDigit() }
    val dayStartIndex = mainClassName.indexAfter("Day")
    val dayEndIndex = mainClassName.indexOfFirst(dayStartIndex) { !it.isDigit() }
    return mainClassName.substring(yearStartIndex, yearEndIndex) to
            mainClassName.substring(dayStartIndex, dayEndIndex)
}

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

fun main() {
    val yearAndDay = "2021" to "01"
    // generate files
    getFile(true, yearAndDay = yearAndDay).run { this.parentFile.mkdirs(); this.createNewFile() }
    getFile(yearAndDay = yearAndDay).run { this.parentFile.mkdirs(); this.createNewFile() }
    File("src", "Year${yearAndDay.first}Day${yearAndDay.second}.kt").writeText("""
        fun main() {
            fun part1(lines: List<String>): Int {
                return 0
            }

            fun part2(lines: List<String>): Int {
                return 0
            }

            val testLines = readLines(true)
            check(part1(testLines) == 0)
            check(part2(testLines) == 0)

            val lines = readLines()
            println(part1(lines))
            println(part2(lines))
        }
    """.trimIndent())
}
