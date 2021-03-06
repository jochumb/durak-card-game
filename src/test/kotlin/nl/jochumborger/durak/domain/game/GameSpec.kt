package nl.jochumborger.durak.domain.game

import nl.jochumborger.durak.domain.game.util.MockDeck
import org.assertj.core.api.Assertions.assertThat
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

private const val DECK_SIZE = 36

object GameSpec : Spek({

    describe("A Game of Durak") {

        context("setup two player game") {
            val game = GameService().setupGame()

            it("has two players") {
                assertThat(game.players.size).isEqualTo(Game.NUMBER_OF_PLAYERS)
            }

            it("all players have 6 cards") {
                game.players.forEach {
                    assertThat(it.hand.size).isEqualTo(Game.HAND_SIZE)
                    assertThat(it.hand.cards).hasOnlyElementsOfType(Card::class.java)
                }
            }

            it("has all remaining cards in the deck") {
                assertThat(game.getDeckSize()).isEqualTo(DECK_SIZE - Game.NUMBER_OF_PLAYERS * Game.HAND_SIZE)
            }

            it("has a trump card with rank") {
                assertThat(game.getTrump().rank).isNotNull()
            }
        }

        context("ends") {

            it("is not finished when the deck still has cards") {
                val game = GameService().setupGame()
                assertThat(game.getDeckSize()).isGreaterThan(0)
                assertThat(game.isFinished()).isFalse()
            }

            it("is not finished when the current round is ongoing") {
                val game = GameService().setupGame()
                game.startRound()
                assertThat(game.getRound().isFinished()).isFalse()
                assertThat(game.isFinished()).isFalse()
            }

            it("is not finished when both players still have cards") {
                val game = GameService().setupGame()
                assertThat(game.players.all { it.hand.size > 0 }).isTrue()
                assertThat(game.isFinished()).isFalse()
            }

            it("is finished when the deck is empty, the current round is finished and at least one player has no cards left") {
                val game = Game() { MockDeck.ofSize(1) }
                game.startRound()
                game.finishRound(game.players.first { it.id == 1 })
                assertThat(game.isFinished()).isTrue()
            }

            it("the winner is the winner of the last round") {
                val game = Game() { MockDeck.ofSize(1) }
                game.startRound()
                game.finishRound(game.players.first { it.id == 1 })
                assertThat(game.winner()?.id).isEqualTo(1)
            }
        }
    }
})
