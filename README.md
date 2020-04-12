# Durak/Fool - Card Game
Durak is a Russian/Ukrainian card game. This implementation is the 1-vs-1, with siege rules, varient.

## The Game

### Round setup
* The **deck** consists of 36 cards. **Ranks**: `6, 7, 8, 9, 10, J, Q, K, A`, **suits**: `clubs (♣), diamonds (♦), hearts (♥), spades (♠)`.
* The deck is *shuffled*
* Each player is dealt **6** cards
* The bottom card of the deck is revealed and its *suit* defines the **trump** for the round

### Starting player
1. The winner of the previous round
2. OR: the player with the lowerst trump
3. OR: **random ??**

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
| `[8♣, 8♥]` | `false` |
| `[8♣, J♣]` | `false` |

#### Defend Examples
The *trump suit* is: *diamonds (♦)*
| Played cards | Attack | Selected | Actions |
| --- | --- | --- | --- |
| `[]` | `[7♣]` | `[]` | `[PASS]` |
| `[]` | `[7♣]` | `[6♣]` | `[PASS]` |
| `[]` | `[7♣]` | `[8♣]` | `[DEFEND, PASS]` |
| `[]` | `[7♣]` | `[A♣]` | `[DEFEND, PASS]` |
| `[]` | `[7♣]` | `[7♥]` | `[ATTACK, PASS]` |
| `[]` | `[7♣]` | `[6♦]` | `[DEFEND, PASS]` |
| `[]` | `[7♣]` | `[7♦]` | `[ATTACK, DEFEND, PASS]` |
| `[]` | `[7♣, 7♠]` | `[7♥]` | `[ATTACK, PASS]` |
| `[]` | `[7♣, 7♠]` | `[7♦]` | `[ATTACK, PASS]` |
| `[]` | `[7♣, 7♠]` | `[7♦, 7♥]` | **??** |
| `[]` | `[7♣, 7♠]` | `[7♦, 8♣]` | `[DEFEND, PASS]` |
| `[]` | `[7♣, 7♠, 7♦]` | `[7♥]` | `[ATTACK, PASS]` |
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
-> Defend possibility after pile on **??**

### End of turn
-> Successful defence vs failed defence
-> Pick cards to refill hand

### Round over
-> Winner
