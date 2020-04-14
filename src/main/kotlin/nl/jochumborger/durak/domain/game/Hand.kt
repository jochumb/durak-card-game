package nl.jochumborger.durak.domain.game

class Hand(initialCards: Set<Card>) {
    private val _cards: MutableSet<Card> = initialCards.toMutableSet()

    val cards: Set<Card>
        get() = _cards.toSet()

    val size: Int
        get() = _cards.size
}
