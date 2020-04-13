package nl.jochumborger.durak.domain

class Deck private constructor(private val cards: MutableList<Card>) {

    fun isEmpty() = cards.isEmpty()

    fun size() = cards.size

    fun pick(): Card? {
        return if (isEmpty()) null else cards.removeAt(0)
    }

    companion object {
        fun createShuffledDeck(): Deck {
            val cards: List<Card> = Suit.values().flatMap { suit ->
                Rank.values().map { rank -> Card(rank, suit) }
            }.shuffled()

            return Deck(cards.toMutableList())
        }
    }
}

data class Card(val rank: Rank, val suit: Suit)

enum class Rank(val value: Int) {
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    TEN(10),
    JACK(11),
    QUEEN(12),
    KING(13),
    ACE(14)
}

enum class Suit {
    HEARTS, CLUBS, DIAMONDS, SPADES
}
