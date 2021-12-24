package com.bakigoal.soccerstats.util

import com.bakigoal.soccerstats.domain.StandingTeam
import com.bakigoal.soccerstats.domain.Standings

private val PUNCTUATION = listOf(", ", "; ", ": ", " ")

/**
 * Truncate long text with a preference for word boundaries and without trailing punctuation.
 */
fun String.smartTruncate(length: Int): String {
    val words = split(" ")
    var added = 0
    var hasMore = false
    val builder = StringBuilder()
    for (word in words) {
        if (builder.length > length) {
            hasMore = true
            break
        }
        builder.append(word)
        builder.append(" ")
        added++
    }

    PUNCTUATION.map {
        if (builder.endsWith(it)) {
            builder.replace(builder.length - it.length, builder.length, "")
        }
    }

    if (hasMore) {
        builder.append("...")
    }
    return builder.toString()
}

fun Standings.populateColors() {
    val descriptionColors = getDescriptionColors(standings)
    standings.filter { it.description != null }.forEach {
        it.descriptionColor = descriptionColors[it.description!!]
    }
}

private fun getDescriptionColors(standings: List<StandingTeam>): Map<String, String> {
    val descList = standings.filter { it.description != null }
        .toSortedSet(compareBy { it.rank })
        .map { it.description }
        .toList()
    val map = mutableMapOf<String, String>()
    val colors = getColors()
    val colorsSize = colors.size
    var i = 0
    descList.forEach {
        if (map[it] == null) {
            map[it!!] = colors[i++ % colorsSize]
        }
    }

    return map
}

private fun getColors(): List<String> = listOf(
    "#ff033d67",
    "#ff0667ac",
    "#ff0990f0",
    "#ffec5a0d",
    "#ffa84009",
    "#ff652605",
)
