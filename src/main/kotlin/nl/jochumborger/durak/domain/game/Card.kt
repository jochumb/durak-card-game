package nl.jochumborger.durak.domain.game

data class Card(val suit: Suit, val rank: Rank) {
    data class Trump(val suit: Suit, val rank: Rank? = null)

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
}
