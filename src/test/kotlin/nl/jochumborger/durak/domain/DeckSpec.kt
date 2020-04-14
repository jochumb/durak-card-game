package nl.jochumborger.durak.domain

import org.assertj.core.api.Assertions.assertThat
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

private const val NUM_RANKS = 9 // 6 upto Ace
private const val NUM_SUITS = 4
private const val DECK_SIZE = NUM_RANKS * NUM_SUITS // 36

object DeckSpec : Spek({
    describe("A Deck of cards") {
        var deck: Deck = Deck.createShuffledDeck()

        beforeEachTest {
            deck = Deck.createShuffledDeck()
        }

        it("has 36 cards") {
            assertThat(deck.size()).isEqualTo(DECK_SIZE)
        }

        it("has 9 ranks upto Ace, and 4 suits") {
            val allCards = (1..DECK_SIZE).mapNotNull { deck.pick() }
            assertThat(allCards.groupBy { it.rank }.size).isEqualTo(NUM_RANKS)
            assertThat(allCards.maxBy { it.rank.value }!!.rank).isEqualTo(Card.Rank.ACE)
            assertThat(allCards.map { it.rank.value }.min()).isEqualTo(Card.Rank.ACE.value - NUM_RANKS + 1)
            assertThat(allCards.groupBy { it.suit }.size).isEqualTo(NUM_SUITS)
        }

        it("has no duplicate cards") {
            val allDistinctCards = (1..DECK_SIZE).mapNotNull { deck.pick() }.onEach { println(it) }.toSet()
            assertThat(allDistinctCards.size).isEqualTo(DECK_SIZE)
            assertThat(deck.isEmpty()).isTrue()
        }

        it("is not clustered by suit") {
            val chunks = (1..DECK_SIZE).mapNotNull { deck.pick() }.chunked(NUM_RANKS)
            chunks.forEach {
                assertThat(it.groupBy { c -> c.suit }.keys.size).isGreaterThan(1)
            }
        }

        it("is not clustered by rank") {
            val chunks = (1..DECK_SIZE).mapNotNull { deck.pick() }.chunked(NUM_SUITS)
            chunks.forEach {
                assertThat(it.groupBy { c -> c.rank }.keys.size).isGreaterThan(1)
            }
        }

        it("is not equal to another shuffled deck") {
            val deck2 = Deck.createShuffledDeck()
            val equalCards = (1..DECK_SIZE).filter { deck.pick() == deck2.pick() }.size
            assertThat(equalCards).isLessThan(DECK_SIZE)
        }
    }
})
