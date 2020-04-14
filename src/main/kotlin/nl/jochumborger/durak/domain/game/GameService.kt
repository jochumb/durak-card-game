package nl.jochumborger.durak.domain.game

class GameService {

    fun setupGame(players: Set<Player>): Game {
        return Game(players) { Deck.createShuffledDeck() }
    }
}
