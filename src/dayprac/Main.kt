import java.io.File

fun main() {
    val file = File("src/dayprac/day1.text").readLines()
    println(file)
    // Co host
    val result = file.map {line -> getNumbesr(line, StartFrom.BEGINNING) * 10 + getNumbesr(line.reversed(), StartFrom.ENDING)}.toList()
    println(result)
    println("First Result: ${result.sum()}")
    println()
    //my solution
    val result2 = file.map { regexWordNumber(it) }.toList()
    println(result2)
    println("Second Result: ${result2.sum()}")
    println()

    // co host 2 solution
    val result3 = file.map {row ->
        calibrateValue(
            row.mapIndexedNotNull{index, char ->
                if (char.isDigit()) char
                else row.possibleWordsAt(index).firstNotNullOfOrNull { candidate ->
                    wordss[candidate]
                }
            }.joinToString()
        )
    }.toList()
    println(result3)
    println("Third Result: ${result3.sum()}")
    println()
    println()
    println("Difference between first and second result: ${result.sum() - result2.sum()}")
    println("Difference between first and third result: ${result.sum() - result3.sum()}")
    println("Difference between second and third result: ${result2.sum() - result3.sum()}")
    println()
    println("Minus: result - result2")
    println(result- result2)
    println("Minus: result2 - result")
    println(result2 - result)
    println("Minus: result - result3")
    println(result - result3)
    println("Minus: result3 - result")
    println(result3 - result)
    println("Minus: result2 - result3")
    println(result2 - result3)
    println("Minus: result3 - result2")
    println(result3 - result2)

}

fun regexWordNumber(text: String): Int {
    val regex = Regex("one|two|three|four|five|six|seven|eight|nine|1|2|3|4|5|6|7|8|9")
    val list = regex.findAll(text).toList()
    val numbers = list.map { getNumberFromWord(it.value) }
    return if (numbers.size == 1) {
        "${numbers.first()}${numbers.first()}".toInt()
    } else {
        "${numbers.first()}${numbers.last()}".toInt()
    }
}

fun getNumberFromWord(text: String): Int {
    return when (text) {
        "one" -> 1
        "two" -> 2
        "three" -> 3
        "four" -> 4
        "five" -> 5
        "six" -> 6
        "seven" -> 7
        "eight" -> 8
        "1" -> 1
        "2" -> 2
        "3" -> 3
        "4" -> 4
        "5" -> 5
        "6" -> 6
        "7" -> 7
        "8" -> 8

        else -> 9
    }
}

//fun getNumber(text: String): Int {
//    val numberArray = text.filter { it.isDigit() }
//    return if (numberArray.length == 1) {
//        "${numberArray.toInt()}${numberArray.toInt()}".toInt()
//    } else {
//        "${numberArray.first()}${numberArray.last()}".toInt()
//    }
//}

//co host solution
val words = listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
enum class StartFrom { BEGINNING, ENDING}

private fun getNumbesr(line: String, startFrom: StartFrom): Int {
    val indices = when (startFrom) {
        StartFrom.BEGINNING -> line.indices
        StartFrom.ENDING -> line.lastIndex downTo 0
    }
    for (idx in indices) {
        line[idx].digitToIntOrNull()?.let {return it}
        for ((wordIndex, word) in words.withIndex()) {
            if (line.substring(idx).startsWith(word)) {
                return (wordIndex + 1)
            }
        }
    }
    error("No number found in $line")
}

//co host 2 solution
val wordss: Map<String, Int> = mapOf(
    "one" to 1,
    "two" to 2,
    "three" to 3,
    "four" to 4,
    "five" to 5,
    "six" to 6,
    "seven" to 7,
    "eight" to 8,
    "nine" to 9
)
fun calibrateValue(row: String): Int {
    val firstDigit = row.first {it.isDigit()}
    val lastDigit = row.last {it.isDigit()}
    return "$firstDigit$lastDigit".toInt()
}

private fun String.possibleWordsAt(startingAt: Int): List<String> =
    (3..5).map { len ->
        substring(startingAt, (startingAt + len).coerceAtMost(length))
    }