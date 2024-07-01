package com.hamersztein

import com.hamersztein.engine.BackgammonEngine
import com.hamersztein.engine.model.Colour

fun main(args: Array<String>) {
    val engine = BackgammonEngine()
    engine.setup()

    engine.movePiece(Colour.LIGHT, 5, 3)

    engine.movePiece(Colour.DARK, 23, 20)

    engine.movePiece(Colour.LIGHT, 5, 3)

    // TODO - which index is the bar?
    // engine.movePiece(Colour.DARK, 24, 20)

    println()
    println()

    println(engine.toString())
}
