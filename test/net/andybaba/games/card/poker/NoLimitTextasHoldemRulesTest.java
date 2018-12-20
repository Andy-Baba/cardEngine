package net.andybaba.games.card.poker;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestInstance;

import net.andybaba.TestClass;
import net.andybaba.games.card.BaseHand;
import net.andybaba.games.card.Card;
import net.andybaba.games.card.Card.Rank;
import net.andybaba.games.card.Card.Suite;
import net.andybaba.games.card.poker.NoLimitTextasHoldemRules.HandName;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class NoLimitTextasHoldemRulesTest extends TestClass {

	public final boolean SHOW_HANDS = true;
	private boolean showHands;
	private NoLimitTextasHoldemRules noLimitTextasHoldemRules;
	private TexasHand hand;

	@Override
	@BeforeAll
	public void beforeAllTests() {
		// TODO Auto-generated method stub
		noLimitTextasHoldemRules = new NoLimitTextasHoldemRules();

	}

	@Override
	public void afterAllTests() {
		// TODO Auto-generated method stub

	}

	@BeforeEach
	public void beforeEachTest(final TestInfo testInfo) {
		logger.info("++++++++++Test " + ++testCounter + ": " + testInfo.getDisplayName());
		hand = new TexasHand(noLimitTextasHoldemRules.getHandSize());
		this.afterTestString = "Done";
		this.showHands = this.SHOW_HANDS;
	}

	@AfterEach
	void afterEachTest(final TestInfo testInfo) {
		logger.info(this.afterTestString);
		BaseHand.SEPARATOR = this.SEPARATOR;
		String tempString = "----------Test " + testInfo.getTestMethod() + "\n\tThe hand has ";
		if (this.showHands) {
			if (this.hand.isEmpty())
				tempString += "EMPTY";
			else
				tempString += hand.count() + " cards out of " + hand.maxSize() + this.SEPARATOR + hand;
		}
		logger.info(tempString);
	}

	@Test
	@DisplayName("1.0 High card")
	void testCalculateHandValueHighCard() {
		for (int i = 0; i < hand.maxSize(); i++) {
			try {
				hand.add(new Card(Rank.random(), Suite.random()));
			} catch (ArrayStoreException e) {
				continue;
			}
		}

		assertEquals(HandName.HighCard.value, noLimitTextasHoldemRules.calculateHandValue(hand));
	}

	@Test
	@DisplayName("1.1 Pair")
	void testCalculateHandValuePair() {
		Card.Rank pair = Card.Rank.random();

		hand.add(new Card(pair, Suite.Club));
		hand.add(new Card(Rank.Five, Suite.Club));
		hand.add(new Card(pair, Suite.Heart));
		hand.add(new Card(Rank.Deuce, Suite.Club));
		hand.add(new Card(Rank.King, Suite.Spade));

		assertEquals(HandName.Pair.value, noLimitTextasHoldemRules.calculateHandValue(hand));
	}

	@Test
	@DisplayName("1.2 Two pair")
	void testCalculateHandValueTwoPair() {
		Card.Rank kicker = Card.Rank.random();
		Card.Rank pair1 = Card.Rank.random();
		Card.Rank pair2 = pair1;

		while (pair1 == pair2)
			pair2 = Card.Rank.random();

		hand.add(new Card(pair1, Suite.Club));
		hand.add(new Card(pair2, Suite.Club));
		hand.add(new Card(pair1, Suite.Heart));
		hand.add(new Card(kicker, Suite.Diamond));
		hand.add(new Card(pair2, Suite.Spade));

		assertEquals(HandName.TwoPair.value, noLimitTextasHoldemRules.calculateHandValue(hand));
	}

	@Test
	@DisplayName("1.3 Three of Kind")
	void testCalculateHandValueThreeOfaKind() {
		hand.add(new Card(Rank.Ace, Suite.Club));
		hand.add(new Card(Rank.King, Suite.Club));
		hand.add(new Card(Rank.Ace, Suite.Heart));
		hand.add(new Card(Rank.Deuce, Suite.Club));
		hand.add(new Card(Rank.Ace, Suite.Spade));

		assertEquals(HandName.ThreeOfaKind.value, noLimitTextasHoldemRules.calculateHandValue(hand));
	}

	@Test
	@DisplayName("1.4 streight")
	void testCalculateHandValueStreight() {
		hand.add(new Card(Rank.Nine, Suite.Club));
		hand.add(new Card(Rank.King, Suite.Club));
		hand.add(new Card(Rank.Jack, Suite.Heart));
		hand.add(new Card(Rank.Ten, Suite.Club));
		hand.add(new Card(Rank.Queen, Suite.Spade));

		assertEquals(HandName.Streight.value, noLimitTextasHoldemRules.calculateHandValue(hand));
	}

	@Test
	@DisplayName("1.5 Ace low streight")
	void testCalculateHandValueAceLowStreight() {
		hand.add(new Card(Rank.Trey, Suite.Club));
		hand.add(new Card(Rank.Five, Suite.Club));
		hand.add(new Card(Rank.Deuce, Suite.Heart));
		hand.add(new Card(Rank.Ace, Suite.Club));
		hand.add(new Card(Rank.Four, Suite.Spade));

		assertEquals(HandName.Streight.value, noLimitTextasHoldemRules.calculateHandValue(hand));
	}

	@Test
	@DisplayName("1.6 Ace hight streight (Royal Streight)")
	void testCalculateHandValueRoyalStreight() {
		hand.add(new Card(Rank.Ace, Suite.Club));
		hand.add(new Card(Rank.King, Suite.Club));
		hand.add(new Card(Rank.Jack, Suite.Heart));
		hand.add(new Card(Rank.Ten, Suite.Club));
		hand.add(new Card(Rank.Queen, Suite.Spade));

		assertEquals(HandName.Streight.value, noLimitTextasHoldemRules.calculateHandValue(hand));
	}

	@Test
	@DisplayName("1.7 Flush")
	void testCalculateHandValueFlush() {
		Suite suite = Suite.random();
		for (int i = 0; i < hand.maxSize(); i++) {
			try {
				hand.add(new Card(Rank.random(), suite));
			} catch (ArrayStoreException e) {
				continue;
			}
		}
		assertEquals(HandName.Flush.value, noLimitTextasHoldemRules.calculateHandValue(hand));
	}

	@Test
	@DisplayName("1.8 Full House")
	void testCalculateHandValueFullHouse() {
		Card.Rank rank1 = Card.Rank.random();
		Card.Rank rank2 = rank1;
		while (rank1 == rank2)
			rank2 = Card.Rank.random();

		hand.add(new Card(rank1, Suite.Club));
		hand.add(new Card(rank2, Suite.Club));
		hand.add(new Card(rank2, Suite.Heart));
		hand.add(new Card(rank1, Suite.Diamond));
		hand.add(new Card(rank1, Suite.Spade));
		assertEquals(HandName.FullHouse.value, noLimitTextasHoldemRules.calculateHandValue(hand));
	}

	@Test
	@DisplayName("1.9 Four of a kind")
	void testCalculateHandValueFourOfaKind() {
		Card.Rank rank1 = Card.Rank.random();
		Card.Rank rank2 = rank1;
		while (rank1 == rank2)
			rank2 = Card.Rank.random();

		hand.add(new Card(rank1, Suite.Club));
		hand.add(new Card(rank1, Suite.Heart));
		hand.add(new Card(rank2, Suite.Heart));
		hand.add(new Card(rank1, Suite.Diamond));
		hand.add(new Card(rank1, Suite.Spade));
		assertEquals(HandName.FourOfaKind.value, noLimitTextasHoldemRules.calculateHandValue(hand));
	}

	@Test
	@DisplayName("1.10 Streight Flush")
	void testCalculateHandValueStreightFlush() {
		Card.Rank rank1 = Card.Rank.random();
		Card.Rank rank2 = rank1;
		while (rank1 == rank2)
			rank2 = Card.Rank.random();

		hand.add(new Card(rank1, Suite.Club));
		hand.add(new Card(rank1, Suite.Heart));
		hand.add(new Card(rank2, Suite.Heart));
		hand.add(new Card(rank1, Suite.Diamond));
		hand.add(new Card(rank1, Suite.Spade));
		assertEquals(HandName.StreightFlush.value, noLimitTextasHoldemRules.calculateHandValue(hand));
	}

	@Test
	void testNoLimitTextasHoldemRules()  {
		System.out.print(HandName.convertInt(-1));
		fail("Not yet implemented");
	}

}
