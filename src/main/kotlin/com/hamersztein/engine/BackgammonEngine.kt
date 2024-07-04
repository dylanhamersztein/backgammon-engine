package com.hamersztein.engine

import com.hamersztein.engine.model.Board
import com.hamersztein.engine.model.Colour

class BackgammonEngine(private val board: Board = Board()) {

    fun setup() {
        board.setup()
    }

    fun isMovePossible(colour: Colour, from: Int, to: Int): Boolean {
        return board.isMovePossible(colour, from, to)
    }

    fun movePiece(colour: Colour, from: Int, to: Int) {
        board.movePiece(colour, from, to)
    }

    override fun toString(): String {
        return board.toString()
    }

}