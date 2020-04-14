package nl.jochumborger.durak.domain.game

class Game(val players: Set<Player>, deckProducer: () -> Deck) {
    private val deck: Deck = deckProducer.invoke()
    private val playerHands: Map<Player, Hand>
    private val trump: Card
    private lateinit var round: Round

    init {
        trump = deck.peekLast()!!
        playerHands = players.map {
            Pair(it, Hand(dealNumberOfCardsFromDeck(HAND_SIZE, deck)))
        }.toMap()
    }

    fun getHandForPlayer(player: Player): Hand {
        return playerHands.getOrDefault(player, Hand(emptySet()))
    }

    fun getDeckSize(): Int = deck.size

    fun getTrump(): Card.Trump {
        return if (deck.isEmpty()) Card.Trump(suit = trump.suit)
        else Card.Trump(suit = trump.suit, rank = trump.rank)
    }

    fun isFinished(): Boolean {
        return deck.isEmpty() && players.any { getHandForPlayer(it).size == 0 } && round.isFinished()
    }

    fun winner(): Player? {
        return if (isFinished()) round.winner else null
    }

    companion object {
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
    var winner: Player? = null
    fun isFinished() = finished

    fun finish(winner: Player) {
        finished = true
        this.winner = winner
    }
}
