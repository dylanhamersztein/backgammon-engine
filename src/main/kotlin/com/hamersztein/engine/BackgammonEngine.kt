package com.hamersztein.engine

import com.hamersztein.engine.model.Board

class BackgammonEngine(private val board: Board = Board()) {

    fun setup() {
        board.setup()
    }

}