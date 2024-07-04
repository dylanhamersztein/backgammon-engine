package com.hamersztein.engine.model

import com.hamersztein.engine.model.Colour.DARK
import com.hamersztein.engine.model.Colour.LIGHT

class Board(private val size: Int = 24) {
    private val bar = Bar()
    private val spaces = Array(size) { Space() }

    fun setup() {
        arrayOf(5 to 5, 7 to 3, 12 to 5, (size - 1) to 2).forEach { (position, pieceCount) ->
            spaces[position].colour = DARK
            spaces[position].count = pieceCount

            spaces[size - position - 1].colour = LIGHT
            spaces[size - position - 1].count = pieceCount
        }
    }

    fun isMovePossible(colour: Colour, from: Int, to: Int) = getPlayerAwarePoints(colour, from, to).let { (_, to) ->
        spaces[to].canMoveHere()
    }

    fun movePiece(colour: Colour, from: Int, to: Int) {
        val (playerAwareFrom, playerAwareTo) = getPlayerAwarePoints(colour, from, to)

        doMove(colour, playerAwareFrom, playerAwareTo)
    }

    override fun toString() = StringBuilder().apply {
        append("-".repeat(19))
        append("\n")

        // top half from light perspective
        val upperRowRange = 0 until 5
        val upperColumnRange = 11 downTo 0
        val upperBarIndex = 5
        printSection(upperRowRange, upperColumnRange, upperBarIndex)

        printBar()

        // bottom half from light perspective
        val lowerRowRange = 4 downTo 0
        val lowerColumnRange = 12..<size
        val lowerBarIndex = 18
        printSection(lowerRowRange, lowerColumnRange, lowerBarIndex)

        append("-".repeat(19))
    }.toString()

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
        val playerAwareFrom = if (colour == DARK) from else size - from - 1
        val playerAwareTo = if (colour == DARK) to else size - to - 1

        return playerAwareFrom to playerAwareTo
    }

    private fun StringBuilder.printSection(rowRange: Iterable<Int>, columnRange: Iterable<Int>, barIndex: Int) {
        rowRange.forEach { rowNumber ->
            append("| ")
            columnRange.forEach { columnNumber ->
                if (columnNumber == barIndex) {
                    append(" | ")
                }

                if (spaces[columnNumber].colour != null && spaces[columnNumber].count > rowNumber) {
                    append(spaces[columnNumber].colour!!.name.first())
                } else {
                    append("o")
                }
            }
            append(" |")
            append("\n")
        }
    }

    private fun StringBuilder.printBar() {
        val numSurroundingHyphens = (19 - bar.numLight - bar.numDark - 1).div(2)

        append("-".repeat(numSurroundingHyphens))
        append("D".repeat(bar.numDark))
        append("-")
        append("L".repeat(bar.numLight))
        append("-".repeat(numSurroundingHyphens))
        append("\n")
    }
}

