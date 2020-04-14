package nl.jochumborger.durak.domain

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isGreaterThan
import strikt.assertions.isLessThan
import strikt.assertions.isTrue

private const val NUM_RANKS = 9 // 6 upto Ace
private const val NUM_SUITS = 4
private const val DECK_SIZE = NUM_RANKS * NUM_SUITS

object DeckSpec : Spek({
    describe("A Deck of cards") {
        var deck: Deck = Deck.createShuffledDeck()

        beforeEachTest {
            deck = Deck.createShuffledDeck()
        }

        it("has $DECK_SIZE cards") {
            expectThat(deck.size()).isEqualTo(DECK_SIZE)
        }

        it("has $NUM_RANKS ranks upto Ace, and $NUM_SUITS suits") {
            val allCards = (1..DECK_SIZE).mapNotNull { deck.pick() }
            expectThat(allCards.groupBy { it.rank }.size).isEqualTo(NUM_RANKS)
            expectThat(allCards.maxBy { it.rank.value }!!.rank).isEqualTo(Card.Rank.ACE)
            expectThat(allCards.map { it.rank.value }.min()).isEqualTo(Card.Rank.ACE.value - NUM_RANKS + 1)
            expectThat(allCards.groupBy { it.suit }.size).isEqualTo(NUM_SUITS)
        }

        it("has no duplicate cards") {
            val allDistinctCards = (1..DECK_SIZE).mapNotNull { deck.pick() }.onEach { println(it) }.toSet()
            expectThat(allDistinctCards.size).isEqualTo(DECK_SIZE)
            expectThat(deck.isEmpty()).isTrue()
        }

        it("is not clustered by suit") {
            val chunks = (1..DECK_SIZE).mapNotNull { deck.pick() }.chunked(NUM_RANKS)
            chunks.forEach {
                expectThat(it.groupBy { c -> c.suit }.keys.size).isGreaterThan(1)
            }
        }

        it("is not clustered by rank") {
            val chunks = (1..DECK_SIZE).mapNotNull { deck.pick() }.chunked(NUM_SUITS)
            chunks.forEach {
                expectThat(it.groupBy { c -> c.rank }.keys.size).isGreaterThan(1)
            }
        }

        it("is not equal to another shuffled deck") {
            val deck2 = Deck.createShuffledDeck()
            val equalCards = (1..DECK_SIZE).filter { deck.pick() == deck2.pick() }.size
            expectThat(equalCards).isLessThan(DECK_SIZE)
        }
    }
})
