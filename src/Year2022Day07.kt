internal class FileSystem {
    val root = Directory("/", null)
    val allDirectories = mutableListOf<Directory>(root)
    var currentDir = root

    fun cd(name: String) {
        currentDir = when (name) {
            "/" -> root
            ".." -> currentDir.parent ?: throw IllegalArgumentException()
            else -> currentDir.getSubDir(name)
        }
    }

    fun createDir(name: String) {
        currentDir.children.computeIfAbsent(name) {
            Directory(name, currentDir).apply { allDirectories.add(this) }
        }
    }

    fun createFile(name: String, size: Int) {
        currentDir.children.computeIfAbsent(name) {
            updateDirSize(size)
            File(name, currentDir, size)
        }
    }

    private fun updateDirSize(delta: Int) {
        var curr: Directory? = currentDir
        while (curr != null) {
            curr.size += delta
            curr = curr.parent
        }
    }

    sealed interface FileSystemEntry {
        val name: String
        val parent: Directory?
        val size: Int
    }

    data class Directory(
        override val name: String,
        override val parent: Directory?,
        override var size: Int = 0
    ): FileSystemEntry {
        val children = mutableMapOf<String, FileSystemEntry>()

        fun getSubDir(name: String): Directory =
            children.get(name) as? Directory ?: throw IllegalArgumentException()
    }

    data class File(
        override val name: String,
        override val parent: Directory?,
        override val size: Int
    ): FileSystemEntry {
    }
}

fun main() {
    fun parse(input: List<String>): FileSystem {
        val fs = FileSystem()
        for (line in input) {
            if (line == "$ ls") {
                continue
            }
            val subDir = line.substringAfter("$ cd ")
            if (subDir != line) {
                fs.cd(subDir)
                continue
            }
            val (typeOrSize, name) = line.split(" ")
            if (typeOrSize == "dir") {
                fs.createDir(name)
            } else {
                fs.createFile(name, typeOrSize.toInt())
            }
        }
        return fs
    }

    fun part1(input: List<String>): Int {
        return parse(input).allDirectories.filter { it.size <= 100000 }.sumOf { it.size }
    }

    fun part2(input: List<String>): Int {
        val fs = parse(input)
        val sizeToDelete = fs.root.size - (70000000 - 30000000)
        if (sizeToDelete <= 0) {
            return 0
        }
        return parse(input).allDirectories.filter { it.size >= sizeToDelete }.minOf { it.size }
    }

    val testLines = readLines(true)
    check(part1(testLines) == 95437)
    check(part2(testLines) == 24933642)

    val lines = readLines()
    println(part1(lines))
    println(part2(lines))
}
