package nl.jochumborger.durak.domain

class Deck private constructor(private val cards: MutableList<Card>) {

    fun isEmpty() = cards.isEmpty()

    fun size() = cards.size

    fun pick(): Card? {
        return if (isEmpty()) null else cards.removeAt(0)
    }

    companion object {
        fun createShuffledDeck(): Deck {
            val cards: List<Card> = Card.Suit.values().flatMap { suit ->
                Card.Rank.values().map { rank -> Card(rank, suit) }
            }.shuffled()

            return Deck(cards.toMutableList())
        }
    }
}
