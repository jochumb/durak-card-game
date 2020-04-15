package nl.jochumborger.durak.domain.game

class Deck private constructor(private val cards: MutableList<Card>) {

    val size: Int
        get() = cards.size

    fun isEmpty() = cards.isEmpty()

    fun pick(): Card? {
        return if (isEmpty()) null else cards.removeAt(0)
    }

    fun peekLast(): Card? {
        return cards.lastOrNull()
    }

    companion object {
        fun createShuffledDeck(): Deck {
            val cards: List<Card> = Card.Suit.values().flatMap { suit ->
                Card.Rank.values().map { rank ->
                    Card(suit = suit, rank = rank)
                }
            }.shuffled()

            return Deck(cards.toMutableList())
        }
    }
}
