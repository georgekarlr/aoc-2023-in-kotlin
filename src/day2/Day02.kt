package day2

import println
import readInput
import kotlin.math.max

fun main() {

    fun part1(input: List<String>): String {
        val colorWI =  input.map { line ->
            group(line)
        }.let { s-> s.map { it.first to it.second.map { convertListToColorValue(it) } } }.map { it.first to it.second.filter { it.red >= 13 || it.green >= 14 || it.blue >= 15 } }
        val second = colorWI.map { it }.filter { it.second.isEmpty() }
        return second.sumOf { it.first.toInt() }.toString()
    }

    fun part2(input: List<String>): String {
        val colorWI =  input.map { line ->
            group(line)
        }.map {it.second}

        return colorWI.joinToString("\n")
    }

    fun solvess(input: List<List<String>>) = input.map { lines ->
        lines.sumOf { line ->
            val max = mutableMapOf("red" to 0, "green" to 0, "blue" to 0)
            line.substringAfter(":").split(";").forEach { set ->
                set.split(", ").map { cube ->
                    val color = cube.substringAfter(" ")
                    max[color] = max(max[color]!!, cube.substringBefore(" ").toInt())
                }

            }
            max.values.reduce { acc, i -> acc * i }
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day2","day02_test")

    val input = readInput("day2","day02")
    part1(input).println()
    part2(input).println()
    val games = input.map { it.split(':', ';').drop(1).map { it.toReveal() } }}

fun group(line: String): Pair<String, List<List<String>>> {
    val insideLine = line.split(":")
    val first = insideLine[0].split(" ")[1]
    val second = insideLine[1].split(";").map { it.split(",").map { sd-> sd.trim() } }
    return first to second
}

fun convertListToColorValue(cubeStrings: List<String>): ColorValue  {
    val list = cubeStrings.map { it.split(" ") }
    var currentColor = ColorValue(0,0,0)
    for (i in list) {
        when (i[1]) {
            "red" -> currentColor = currentColor.copy(red = currentColor.red + i[0].toInt())
            "green" -> currentColor = currentColor.copy(green = currentColor.green + i[0].toInt())
            "blue" -> currentColor = currentColor.copy(blue = currentColor.blue + i[0].toInt())
        }
    }
    return currentColor
}data class ColorValue(val red: Int, val blue: Int, val green: Int)


data class Reveal(val red: Int, val green: Int, val blue: Int)

fun String.toReveal(): Reveal {
    val map = trim().split(", ").map { it.split(' ') }.associate { it[1] to it[0].toInt() }
    return Reveal(map["red"] ?: 0, map["green"] ?: 0, map["blue"] ?: 0)
}

