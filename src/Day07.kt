class TrieNode(
    val name: String,
    val parent: TrieNode?,
    var size: Int = 0,
    private val files: MutableMap<String, Int> = mutableMapOf(),
    private val dirs: MutableMap<String, TrieNode> = mutableMapOf(),
) {
    fun addFile(name: String, size: Int) {
        if (files.put(name, size) == null) {
            updateSize(size)
        }
    }

    private fun updateSize(delta: Int) {
        size += delta
        parent?.updateSize(delta)
    }

    fun getDir(name: String): TrieNode? {
        if (name == "..") {
            return parent
        }
        return dirs.computeIfAbsent(name) { TrieNode(it, this) }
    }
}

fun main() {
    fun construct(input: List<String>): Collection<TrieNode> {
        val allNodes = mutableSetOf<TrieNode>()
        val root = TrieNode("", null)
        var curr: TrieNode? = null
        for (line in input) {
            if (line == "$ ls") {
                continue
            }
            val subDir = line.substringAfter("$ cd ")
            if (subDir != line) {
                curr = if (subDir == "/") root else curr?.getDir(subDir)
                curr?.let { allNodes.add(it) }
                continue
            }
            val (typeOrSize, name) = line.split(" ")
            if (typeOrSize != "dir") {
                curr?.addFile(name, typeOrSize.toInt())
            }
        }
        return allNodes
    }

    fun part1(input: List<String>): Int {
        return construct(input).filter { it.size <= 100000 }.sumOf { it.size }
    }

    fun part2(input: List<String>): Int {
        val allNodes = construct(input)
        val sizeToDelete = allNodes.first().size - 40000000
        if (sizeToDelete <= 0) {
            return 0
        }
        return allNodes.filter { it.size >= sizeToDelete }.minOf { it.size }
    }

    val testInput = readInput("Day07_test")
    check(part1(testInput) == 95437)
    check(part2(testInput) == 24933642)

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}
