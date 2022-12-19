import java.io.File
import java.lang.IllegalStateException
import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.math.absoluteValue
import kotlin.math.pow

internal fun readLines(isTest: Boolean = false, testSuffix: String ="_test") =
    getFile(isTest, testSuffix).readLines()

internal fun readText(isTest: Boolean = false, testSuffix: String ="_test") =
    getFile(isTest, testSuffix).readText()

private fun getFile(isTest: Boolean = false,
                     testSuffix: String ="_test",
                     yearAndDay: Pair<String, String> = getYearAndDay()): File =
    yearAndDay.let { (year, day) ->
        Path("input", year, "Day$day${if (isTest) testSuffix else ""}.txt").toFile()
    }

fun CharSequence.indexAfter(string: String, startIndex: Int = 0, ignoreCase: Boolean = false): Int =
    indexOf(string, startIndex, ignoreCase).let { return if (it == -1) -1 else it + string.length }

fun CharSequence.indexOfFirst(startIndex: Int = 0, predicate: (Char) -> Boolean): Int {
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

fun assertEquals(expected: Any, actual: Any) {
    if (expected != actual) {
        throw IllegalStateException("expected: $expected, given: $actual")
    }
}

data class Point(
    var x: Int,
    var y: Int
)

inline val <E> List<List<E>>.rowSize get() = size
internal val <E> List<List<E>>.columnSize get() = first().size

fun Iterable<Int>.digitsToInt(radix: Int) = reduce { acc, digit -> acc * radix + digit }

fun Int.pow(n: Int): Int = this.toDouble().pow(n).toInt()

/**
 * Euclid's algorithm for finding the greatest common divisor of a and b.
 */
fun gcd(a: Int, b: Int): Int = if (b == 0) a.absoluteValue else gcd(b, a % b)
fun gcd(f: Int, vararg n: Int): Int = n.fold(f, ::gcd)
fun gcd(a: Long, b: Long): Long = if (b == 0L) a.absoluteValue else gcd(b, a % b)
fun gcd(f: Long, vararg n: Long): Long = n.fold(f, ::gcd)
fun Iterable<Int>.gcd(): Int = reduce(::gcd)
fun Iterable<Long>.gcd(): Long = reduce(::gcd)
@JvmName("gcdForInt")
fun Sequence<Int>.gcd(): Long = map { it.toLong() }.reduce(::gcd)
fun Sequence<Long>.gcd(): Long = reduce(::gcd)

/**
 * Find the least common multiple of a and b using the gcd of a and b.
 */
fun lcm(a: Int, b: Int) = (a * b) / gcd(a, b)
fun lcm(a: Long, b: Long) = (a * b) / gcd(a, b)
fun lcm(f: Int, vararg n: Int): Long = n.map { it.toLong() }.fold(f.toLong(), ::lcm)
fun lcm(f: Long, vararg n: Long): Long = n.fold(f, ::lcm)
@JvmName("lcmForInt")
fun Iterable<Int>.lcm(): Long = map { it.toLong() }.reduce(::lcm)
fun Iterable<Long>.lcm(): Long = reduce(::lcm)
@JvmName("lcmForInt")
fun Sequence<Int>.lcm(): Long = map { it.toLong() }.reduce(::lcm)
fun Sequence<Long>.lcm(): Long = reduce(::lcm)

fun Sequence<Int>.product(): Int = reduce { acc, i -> acc * i }
fun Sequence<Long>.product(): Long = reduce { acc, i -> acc * i }


fun main() {
    val yearAndDay = "2022" to "15"
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
            assertEquals(0, part1(testLines))
            assertEquals(0, part2(testLines))

            val lines = readLines()
            println(part1(lines))
            println(part2(lines))
        }
    """.trimIndent())
}
