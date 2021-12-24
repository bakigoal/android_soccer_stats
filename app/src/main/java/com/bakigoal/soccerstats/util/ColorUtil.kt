package com.bakigoal.soccerstats.util

import com.bakigoal.soccerstats.domain.StandingTeam
import com.bakigoal.soccerstats.domain.Standings

fun Standings.populateColors() {
    val descriptionColors = getDescriptionColors(standings)
    standings.filter { it.description != null }.forEach {
        it.descriptionColor = descriptionColors[it.description!!]
    }
}

private fun getDescriptionColors(standings: List<StandingTeam>): Map<String, String> {
    val descList = standings.filter { it.description != null }
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
