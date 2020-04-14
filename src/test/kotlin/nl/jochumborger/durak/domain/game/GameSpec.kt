package nl.jochumborger.durak.domain.game

import org.assertj.core.api.Assertions.assertThat
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

private const val DECK_SIZE = 36

object GameSpec : Spek({

    describe("A Game of Durak") {

        context("setup two player game") {
            val players = setOf(Player(1), Player(2))
            val game = GameService().setupGame(players)

            it("has two players") {
                assertThat(game.players).isEqualTo(players)
            }

            it("all players have 6 cards") {
                game.players.forEach {
                    assertThat(game.getHandForPlayer(it).size).isEqualTo(Game.HAND_SIZE)
                    assertThat(game.getHandForPlayer(it).cards).hasOnlyElementsOfType(Card::class.java)
                }
            }

            it("has all remaining cards in the deck") {
                assertThat(game.getDeckSize()).isEqualTo(DECK_SIZE - players.size * Game.HAND_SIZE)
            }

            it("has a trump card with rank") {
                assertThat(game.getTrump().rank).isNotNull()
            }
        }
    }
})
