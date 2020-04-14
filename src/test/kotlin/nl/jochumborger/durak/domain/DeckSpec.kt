package nl.jochumborger.durak.domain

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isGreaterThan
import strikt.assertions.isLessThan
import strikt.assertions.isTrue

object DeckSpec : Spek({
    describe("A Deck of cards") {
        var deck: Deck = Deck.createShuffledDeck()

        beforeEachTest {
            deck = Deck.createShuffledDeck()
        }

        it("has 36 cards") {
            expectThat(deck.size()).isEqualTo(36)
        }

        it("has 9 ranks, 6 upto Ace (14), and all 4 suits") {
            val allCards = (1..36).mapNotNull { deck.pick() }
            expectThat(allCards.map { it.rank.value }.min()).isEqualTo(6)
            expectThat(allCards.maxBy { it.rank.value }!!.rank).isEqualTo(Card.Rank.ACE)
            expectThat(allCards.groupBy { it.rank }.size).isEqualTo(9)
            expectThat(allCards.groupBy { it.suit }.size).isEqualTo(4)
        }

        it("has no duplicate cards") {
            val allDistinctCards = (1..36).mapNotNull { deck.pick() }.onEach { println(it) }.toSet()
            expectThat(allDistinctCards.size).isEqualTo(36)
            expectThat(deck.isEmpty()).isTrue()
        }

        it("is not clustered by suit") {
            val chunks = (1..36).mapNotNull { deck.pick() }.chunked(9)
            chunks.forEach {
                expectThat(it.groupBy { c -> c.suit }.keys.size).isGreaterThan(1)
            }
        }

        it("is not clustered by rank") {
            val chunks = (1..36).mapNotNull { deck.pick() }.chunked(4)
            chunks.forEach {
                expectThat(it.groupBy { c -> c.rank }.keys.size).isGreaterThan(1)
            }
        }

        it("is not equal to another shuffled deck") {
            val deck2 = Deck.createShuffledDeck()
            val equalCards = (1..36).filter { deck.pick() == deck2.pick() }.size
            expectThat(equalCards).isLessThan(36)
        }
    }
})
