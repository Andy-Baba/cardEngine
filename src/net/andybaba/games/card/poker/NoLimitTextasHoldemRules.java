package net.andybaba.games.card.poker;

import java.util.HashMap;

import net.andybaba.games.card.BaseHand;
import net.andybaba.games.card.Rules;
import net.andybaba.games.card.Card;
import net.andybaba.games.card.Card.Rank;

/**
 * No Limit Texas Holdem Poker is the most wildly played variant of poker all
 * around the world.
 * 
 * @version 0.7.0
 * @since Dec 20 2018
 * @author andy
 * @see {@link Rules}
 *
 */
public final class NoLimitTextasHoldemRules extends Rules {

	public final static int HOLDEM_HAND_MAXVALUE = 69;

	public NoLimitTextasHoldemRules() {

		super.betAmountLimit = 0; // zero for no limit
		super.durationPerTurnLimit = null;
		super.bettingRoundsCount = 4; // before flop, flop, turn, river
		super.handSize = 5; // 5 cards out of best 7 cards
		super.maxCommonCards = 5; // 3 on the flop, 1 on the turn & 1 on the river
		super.maxPlayers = 10;
		super.pocketCardSize = 2; // 2
		super.canChangeCards = false;
	}

	public enum HoldemHandValue implements HandValue {
		HighCard(0, 0), Pair(1, 0), TwoPair(2, 0), ThreeOfaKind(3, 0), Straight(4, 0), Flush(5, 0), FullHouse(6, 0),
		FourOfaKind(7, 0), StreightFlush(8, 0);

		private final int order;
		private int value;

		private HoldemHandValue(final int order, final int value) {
			this.value = value;
			this.order = order;
		}

		public static HoldemHandValue convertInt(final int order) throws ArrayIndexOutOfBoundsException {
			return values()[order];
		}

		@Override
		public int getValue() {
			return this.value;
		}

		@Override
		public int getOrder() {
			return this.order;
		}

		@Override
		public void setValue(int value) {
			this.value = value;
		}
	}

	public enum HoldemAction implements Action {
		Fold(0), Check(1), Call(2), Raise(3);
		private final int value;

		private HoldemAction(final int value) {
			this.value = value;
		}

		@Override
		public int getValue() {
			return this.value;
		}
	}

	@Override
	public HandValue calculateHandValue(final BaseHand hand) {
		HandValue handName;
		final HashMap<Card.Rank, Integer> sameRanks = hand.countSameRanks();
		final HashMap<Card.Suite, Integer> sameSuites = hand.countSameSuites();
		if (sameRanks.size() == hand.count()) { // If true, means there is no rank duplications in the hand
			HandValue temp = handName = HoldemHandValue.HighCard; // Presumption is we have a high card
			hand.sort(); // To be able to easily evaluate the straits
			if (hand.show(hand.count()).rank.value - hand.show(1).rank.value == hand.count() - 1 // TODO this if have to
																									// be revised after
																									// value of ace is
																									// increased from
																									// one to 14
					|| (hand.show(hand.count()).rank.value - hand.show(2).rank.value == hand.count() - 2
							&& hand.show(hand.count()).rank.value == Rank.values().length)) {
				temp = handName = HoldemHandValue.Straight; // We keep to check for Straight Flushes
			}
			if (sameSuites.containsValue(hand.count())) { // If number of same suites are equal to number of --
				handName = HoldemHandValue.Flush; // -- cards in the hand it means the hand is a Flush
				if (temp == HoldemHandValue.Straight) // If its also Straight then its Straight Flush
					handName = HoldemHandValue.StreightFlush;
			}
		} else if (sameRanks.size() == (hand.count() - 1)) {
			handName = HoldemHandValue.Pair;
		} else if (sameRanks.size() == (hand.count() - 2)) {
			handName = HoldemHandValue.TwoPair;
			if (sameRanks.containsValue(3))
				handName = HoldemHandValue.ThreeOfaKind;
		} else {
			handName = HoldemHandValue.FullHouse;
			if (sameRanks.containsValue(4))
				handName = HoldemHandValue.FourOfaKind;
		}

		int handValue = handName.getOrder() * HOLDEM_HAND_MAXVALUE;
		// TODO make sure that it can calculate the value of hands as you expect
		// SPECIALLY STRAIGHTS!!
		for (Card card : hand) {
			handValue += card.rank.value;
		}
		handName.setValue(handValue);
		return handName;
	}

	@Override
	public Action decide() {
		// TODO Auto-generated method stub
		return null;
	}

}
