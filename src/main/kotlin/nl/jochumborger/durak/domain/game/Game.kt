package nl.jochumborger.durak.domain.game

class Game(deckSupplier: () -> Deck) {
    private val deck: Deck = deckSupplier.invoke()
    private val trump: Card

    val players: List<Player>

    private lateinit var round: Round

    init {
        trump = deck.peekLast()!!
        players = (1..NUMBER_OF_PLAYERS).map {
            Player(it, Hand(dealNumberOfCardsFromDeck(HAND_SIZE, deck)))
        }
    }

    fun getDeckSize(): Int = deck.size

    fun getTrump(): Card.Trump {
        return if (deck.isEmpty()) Card.Trump(suit = trump.suit)
        else Card.Trump(suit = trump.suit, rank = trump.rank)
    }

    fun isFinished(): Boolean {
        return deck.isEmpty() && players.any { it.hand.size == 0 } && round.isFinished()
    }

    fun winner(): Player? {
        return if (isFinished()) players.firstOrNull { it.id == round.winner } else null
    }

    companion object {
        const val NUMBER_OF_PLAYERS = 2
        const val HAND_SIZE = 6

        private fun dealNumberOfCardsFromDeck(numberOfCards: Int, deck: Deck): Set<Card> {
            return (1..numberOfCards).mapNotNull { deck.pick() }.toSet()
        }
    }

    /*
     * Temporary round stuff, to implement the finishing logic
     */
    fun startRound() {
        round = Round(false)
    }

    fun getRound(): Round {
        return round
    }

    fun finishRound(winner: Player) {
        round.finish(winner)
    }
}

data class Round(private var finished: Boolean) {
    var winner: Int? = null
    fun isFinished() = finished

    fun finish(winner: Player) {
        finished = true
        this.winner = winner.id
    }
}
