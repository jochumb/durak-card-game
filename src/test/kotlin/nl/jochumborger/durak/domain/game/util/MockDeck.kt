package nl.jochumborger.durak.domain.game.util

import nl.jochumborger.durak.domain.game.Deck

object MockDeck {
    fun ofSize(size: Int): Deck {
        val deck = Deck.createShuffledDeck()
        (1..(deck.size - size)).forEach { deck.pick() }
        return deck
    }
}
