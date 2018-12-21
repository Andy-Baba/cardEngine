package net.andybaba.games.card.poker;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;

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
import net.andybaba.games.card.Rules.HandName;
import net.andybaba.games.card.poker.NoLimitTextasHoldemRules.HoldemHandName;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class NoLimitTextasHoldemRulesTest extends TestClass {

	public final boolean SHOW_HANDS = true;
	private boolean showHands;
	private NoLimitTextasHoldemRules noLimitTextasHoldemRules;
	private TexasHand hand;

	private int calulateExpectedHandValue(final BaseHand hand, final HoldemHandName expected) {
		int result = NoLimitTextasHoldemRules.HOLDEM_HAND_MAXVALUE * expected.getOrder();
		for (Card card : hand)
			result += card.rank.value;
		return result;
	}

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
		HashSet<Rank> ranks = new HashSet<Rank>();
		while (ranks.size() < hand.maxSize())
			ranks.add(Rank.random());
		for (Rank rank : ranks)
			hand.add(new Card(rank, Suite.random()));

		HandName actual = noLimitTextasHoldemRules.calculateHandValue(hand);
		HoldemHandName expected = HoldemHandName.HighCard;
		assertEquals(expected, actual);
		System.out.println(calulateExpectedHandValue(hand, expected));
		assertEquals(calulateExpectedHandValue(hand, expected), actual.getValue());
	}

	@Test
	@DisplayName("1.1 Pair")
	void testCalculateHandValuePair() {
		HashSet<Rank> ranks = new HashSet<Rank>();
		Rank pair = null;
		while (ranks.size() < hand.maxSize() - 1) {
			pair = Rank.random();
			ranks.add(pair);
		}

		hand.add(new Card(pair, Suite.Heart));
		for (Rank rank : ranks)
			hand.add(new Card(rank, Suite.Spade));

		HandName actual = noLimitTextasHoldemRules.calculateHandValue(hand);
		HoldemHandName expected = HoldemHandName.Pair;
		assertEquals(expected, actual);
		assertEquals(calulateExpectedHandValue(hand, expected), actual.getValue());
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

		HandName actual = noLimitTextasHoldemRules.calculateHandValue(hand);
		HoldemHandName expected = HoldemHandName.TwoPair;
		assertEquals(expected, actual);
		assertEquals(calulateExpectedHandValue(hand, expected), actual.getValue());
	}

	@Test
	@DisplayName("1.3 Three of Kind")
	void testCalculateHandValueThreeOfaKind() {
		HashSet<Rank> ranks = new HashSet<Rank>();
		Rank pair = null;
		while (ranks.size() < hand.maxSize() - 2) {
			pair = Rank.random();
			ranks.add(pair);
		}

		hand.add(new Card(pair, Suite.Club));
		for (Rank rank : ranks)
			hand.add(new Card(rank, Suite.Diamond));
		hand.add(new Card(pair, Suite.Spade));

		HandName actual = noLimitTextasHoldemRules.calculateHandValue(hand);
		HoldemHandName expected = HoldemHandName.ThreeOfaKind;
		assertEquals(expected, actual);
		assertEquals(calulateExpectedHandValue(hand, expected), actual.getValue());
	}

	@Test
	@DisplayName("1.4 streight")
	void testCalculateHandValueStreight() {
		hand.add(new Card(Rank.Nine, Suite.Club));
		hand.add(new Card(Rank.King, Suite.Club));
		hand.add(new Card(Rank.Jack, Suite.Heart));
		hand.add(new Card(Rank.Ten, Suite.Club));
		hand.add(new Card(Rank.Queen, Suite.Spade));

		HandName actual = noLimitTextasHoldemRules.calculateHandValue(hand);
		HoldemHandName expected = HoldemHandName.Straight;
		assertEquals(expected, actual);
		assertEquals(calulateExpectedHandValue(hand, expected), actual.getValue());
	}

	@Test
	@DisplayName("1.5 Ace low streight")
	void testCalculateHandValueAceLowStreight() {
		hand.add(new Card(Rank.Trey, Suite.Club));
		hand.add(new Card(Rank.Five, Suite.Club));
		hand.add(new Card(Rank.Deuce, Suite.Heart));
		hand.add(new Card(Rank.Ace, Suite.Club));
		hand.add(new Card(Rank.Four, Suite.Spade));

		HandName actual = noLimitTextasHoldemRules.calculateHandValue(hand);
		HoldemHandName expected = HoldemHandName.Straight;
		assertEquals(expected, actual);
		assertEquals(calulateExpectedHandValue(hand, expected), actual.getValue());
	}

	@Test
	@DisplayName("1.6 Ace hight streight (Royal Streight)")
	void testCalculateHandValueRoyalStreight() {
		hand.add(new Card(Rank.Ace, Suite.Club));
		hand.add(new Card(Rank.King, Suite.Club));
		hand.add(new Card(Rank.Jack, Suite.Heart));
		hand.add(new Card(Rank.Ten, Suite.Club));
		hand.add(new Card(Rank.Queen, Suite.Spade));

		HandName actual = noLimitTextasHoldemRules.calculateHandValue(hand);
		HoldemHandName expected = HoldemHandName.Straight;
		assertEquals(expected, actual);
		assertEquals(calulateExpectedHandValue(hand, expected), actual.getValue());
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
		HandName actual = noLimitTextasHoldemRules.calculateHandValue(hand);
		HoldemHandName expected = HoldemHandName.Flush;
		assertEquals(expected, actual);
		assertEquals(calulateExpectedHandValue(hand, expected), actual.getValue());
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

		HandName actual = noLimitTextasHoldemRules.calculateHandValue(hand);
		HoldemHandName expected = HoldemHandName.FullHouse;
		assertEquals(expected, actual);
		assertEquals(calulateExpectedHandValue(hand, expected), actual.getValue());
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

		HandName actual = noLimitTextasHoldemRules.calculateHandValue(hand);
		HoldemHandName expected = HoldemHandName.FourOfaKind;
		assertEquals(expected, actual);
		assertEquals(calulateExpectedHandValue(hand, expected), actual.getValue());
	}

	@Test
	@DisplayName("1.10 Streight Flush")
	void testCalculateHandValueStreightFlush() {
		Suite suite = Suite.random();

		hand.add(new Card(Rank.Nine, suite));
		hand.add(new Card(Rank.King, suite));
		hand.add(new Card(Rank.Jack, suite));
		hand.add(new Card(Rank.Ten, suite));
		hand.add(new Card(Rank.Queen, suite));

		HandName actual = noLimitTextasHoldemRules.calculateHandValue(hand);
		HoldemHandName expected = HoldemHandName.StreightFlush;
		assertEquals(expected, actual);
		assertEquals(calulateExpectedHandValue(hand, expected), actual.getValue());
	}

	@Test
	@DisplayName("1.11 Royal Streight Flush")
	void testCalculateHandValueRoyalStreightFlush() {
		Suite suite = Suite.random();

		hand.add(new Card(Rank.Ace, suite));
		hand.add(new Card(Rank.King, suite));
		hand.add(new Card(Rank.Jack, suite));
		hand.add(new Card(Rank.Ten, suite));
		hand.add(new Card(Rank.Queen, suite));

		HandName actual = noLimitTextasHoldemRules.calculateHandValue(hand);
		HoldemHandName expected = HoldemHandName.StreightFlush;
		assertEquals(expected, actual);
		assertEquals(calulateExpectedHandValue(hand, expected), actual.getValue());
	}

	@Test
	void testNoLimitTextasHoldemRules() {

		fail("Not yet implemented");
	}

}
