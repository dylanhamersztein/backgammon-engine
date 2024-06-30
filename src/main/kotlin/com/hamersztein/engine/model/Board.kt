package com.hamersztein.engine.model

import com.hamersztein.engine.model.Colour.DARK
import com.hamersztein.engine.model.Colour.LIGHT

class Board(private val size: Int = 24) {
    private val bar = Bar()
    private val spaces = Array(size) { Space() }

    fun setup() {
        arrayOf(5 to 5, 7 to 3, 12 to 5, 23 to 2).forEach { (position, pieceCount) ->
            spaces[position].colour = LIGHT
            spaces[position].count = pieceCount

            spaces[size - position - 1].colour = DARK
            spaces[size - position - 1].count = pieceCount
        }
    }

    fun movePiece(colour: Colour, from: Int, to: Int) {
        val (playerAwareFrom, playerAwareTo) = getPlayerAwarePoints(colour, from, to)

        doMove(colour, playerAwareFrom, playerAwareTo)
    }

    private fun doMove(
        colour: Colour,
        from: Int,
        to: Int
    ) {
        if (from == size + 1) {
            bar.removePiece(colour)
        } else {
            spaces[from].count--
            if (spaces[from].count == 0) {
                spaces[from].colour = null
            }
        }

        if (spaces[to].colour != null && spaces[to].colour != colour) {
            bar.addPiece(spaces[to].colour!!)
            spaces[to].colour = colour
        } else {
            spaces[to].count++
            spaces[to].colour = colour
        }
    }

    private fun getPlayerAwarePoints(
        colour: Colour,
        from: Int,
        to: Int
    ): Pair<Int, Int> {
        val playerAwareFrom = if (colour == LIGHT) from else size - from - 1
        val playerAwareTo = if (colour == LIGHT) to else size - to - 1

        return playerAwareFrom to playerAwareTo
    }
}