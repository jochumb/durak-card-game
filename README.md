# Durak/Fool - Card Game
[![CircleCI](https://circleci.com/gh/jochumb/durak-card-game.svg?style=shield)](https://circleci.com/gh/jochumb/durak-card-game)

Durak is a Russian/Ukrainian card game. This implementation is the 1-vs-1, with siege rules, variant.

## The Game

### Start
- [X] The **deck** consists of 36 cards. **Ranks**: `6, 7, 8, 9, 10, J, Q, K, A`, **suits**: `clubs (♣), diamonds (♦), hearts (♥), spades (♠)`.
- [X] The deck is *shuffled*
- [X] Each player is dealt **6** cards
- [X] The bottom card of the deck is revealed and its *suit* defines the **trump** for the round

### End
* The deck is empty
* The round is finished
* At least one player has no cards in hand
* The winner is the player who is first without cards

### Round

#### Starting player
1. The winner of the previous round
2. OR: the player with the lowest trump
3. OR: **random ??**

#### Progress
* Players have one turn, clockwise
* The round ends when:
  * The attacker is done
  * 6 attacking cards have been defended
* If not all attacking cards are defended:
  * The attacker wins
  * The defender gets all the cards played in the round
  * The attacker refills the hand back to **6** cards from the deck
* If all attacking cards are defended:
  * The defender wins
  * All cards played in the round are discarded
  * Both players refill their hand back to **6** cards from the deck, this rounds starting player first

### Turn

#### First Attack Examples
| Attack | Valid |
| --- | --- |
| `[6♣]` | `true` |
| `[8♣]` | `true` |
| `[6♣, 8♣]` | `false` |
| `[8♣, 8♦]` | `true` |
| `[8♣, 8♦, 8♥]` | `true` |
| `[8♣, 8♦, 8♥, 8♠]` | `true` |

#### Next Attack Examples
Cards on the table are: `[8♦, J♦]`
| Attack | Valid |
| --- | --- |
| `[]` | `true` |
| `[6♣]` | `false` |
| `[8♣]` | `true` |
| `[J♣]` | `true` |
| `[8♥]` | `true` |
| `[8♠]` | `true` |
| `[8♣, 8♥]` | `true` |
| `[8♣, J♣]` | `true` |

#### Defend Examples
The *trump suit* is: *diamonds (♦)*
| Played cards | Attack | Selected | Actions |
| --- | --- | --- | --- |
| `[]` | `[7♣]` | `[]` | `[PASS]` |
| `[]` | `[7♣]` | `[6♣]` | `[PASS]` |
| `[]` | `[7♣]` | `[8♣]` | `[DEFEND, PASS]` |
| `[]` | `[7♣]` | `[A♣]` | `[DEFEND, PASS]` |
| `[]` | `[7♣]` | `[7♥]` | `[COUNTERATTACK, PASS]` |
| `[]` | `[7♣]` | `[6♦]` | `[DEFEND, PASS]` |
| `[]` | `[7♣]` | `[7♦]` | `[COUNTERATTACK, DEFEND, PASS]` |
| `[]` | `[7♣, 7♠]` | `[7♥]` | `[COUNTERATTACK, PASS]` |
| `[]` | `[7♣, 7♠]` | `[7♦]` | `[COUNTERATTACK, PASS]` |
| `[]` | `[7♣, 7♠]` | `[7♦, 7♥]` | `[COUNTERATTACK, PASS]` |
| `[]` | `[7♣, 7♠]` | `[7♦, 8♣]` | `[DEFEND, PASS]` |
| `[]` | `[7♣, 7♠, 7♦]` | `[7♥]` | `[COUNTERATTACK, PASS]` |
| `[]` | `[7♣, 7♠, 7♦, 7♥]` | `[8♣, 9♠, 8♦, 10♦]` | `[DEFEND, PASS]` |
| `[7♠, Q♠]` | `[7♣]` | `[]` | `[PASS]` |
| `[7♠, Q♠]` | `[7♣]` | `[6♣]` | `[PASS]` |
| `[7♠, Q♠]` | `[7♣]` | `[7♥]` | `[PASS]` |
| `[7♠, Q♠]` | `[7♣]` | `[8♣]` | `[DEFEND, PASS]` |
| `[7♠, Q♠]` | `[7♣]` | `[Q♣]` | `[DEFEND, PASS]` |
| `[7♠, Q♠]` | `[7♣]` | `[6♦]` | `[DEFEND, PASS]` |
| `[7♠, Q♠]` | `[7♣]` | `[7♦]` | `[DEFEND, PASS]` |
| `[7♠, Q♠]` | `[7♣]` | `[8♦]` | `[DEFEND, PASS]` |
| `[7♠, Q♠]` | `[7♣]` | `[7♦, 7♥]` | `[PASS]` |
| `[7♠, Q♠]` | `[7♣]` | `[8♣, Q♣]` | `[PASS]` |

#### Pile on attack
-> Examples related to adding addition cards by attacker after defender passes.