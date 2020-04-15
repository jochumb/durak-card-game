package nl.jochumborger.durak.domain.game

class GameService {

    fun setupGame(): Game {
        return Game() { Deck.createShuffledDeck() }
    }
}
