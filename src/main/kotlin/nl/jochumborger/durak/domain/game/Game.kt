package nl.jochumborger.durak.domain.game

class Game(val players: Set<Player>, deckProducer: () -> Deck) {
    private val deck: Deck = deckProducer.invoke()
    private val playerHands: Map<Player, Hand>
    private val trump: Card

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

    companion object {
        const val HAND_SIZE = 6

        private fun dealNumberOfCardsFromDeck(numberOfCards: Int, deck: Deck): Set<Card> {
            return (1..numberOfCards).mapNotNull { deck.pick() }.toSet()
        }
    }
}
