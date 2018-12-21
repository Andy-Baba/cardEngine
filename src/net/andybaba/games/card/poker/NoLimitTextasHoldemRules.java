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

	public enum HoldemHandName implements HandName {
		HighCard(0, 0), Pair(1, 0), TwoPair(2, 0), ThreeOfaKind(3, 0), Straight(4, 0), Flush(5, 0), FullHouse(6, 0),
		FourOfaKind(7, 0), StreightFlush(8, 0);

		private final int order;
		private int value;

		private HoldemHandName(final int order, final int value) {
			this.value = value;
			this.order = order;
		}

		public static HoldemHandName convertInt(final int order) throws ArrayIndexOutOfBoundsException {
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

	@Override
	public HandName calculateHandValue(final BaseHand hand) {
		HandName handName;
		final HashMap<Card.Rank, Integer> sameRanks = hand.countSameRanks();
		final HashMap<Card.Suite, Integer> sameSuites = hand.countSameSuites();
		if (sameRanks.size() == hand.count()) { // If true, means there is no rank duplications in the hand
			HandName temp = handName = HoldemHandName.HighCard; // Presumption is we have a high card
			hand.sort(); // To be able to easily evaluate the straits
			if (hand.show(hand.count()).rank.value - hand.show(1).rank.value == hand.count() - 1
					|| (hand.show(hand.count()).rank.value - hand.show(2).rank.value == hand.count() - 2
							&& hand.show(hand.count()).rank.value == Rank.values().length)) {
				temp = handName = HoldemHandName.Straight; // We keep to check for Straight Flushes
			}
			if (sameSuites.containsValue(hand.count())) { // If number of same suites are equal to number of --
				handName = HoldemHandName.Flush; // -- cards in the hand it means the hand is a Flush
				if (temp == HoldemHandName.Straight) // If its also Straight then its Straight Flush
					handName = HoldemHandName.StreightFlush;
			}
		} else if (sameRanks.size() == (hand.count() - 1)) {
			handName = HoldemHandName.Pair;
		} else if (sameRanks.size() == (hand.count() - 2)) {
			handName = HoldemHandName.TwoPair;
			if (sameRanks.containsValue(3))
				handName = HoldemHandName.ThreeOfaKind;
		} else {
			handName = HoldemHandName.FullHouse;
			if (sameRanks.containsValue(4))
				handName = HoldemHandName.FourOfaKind;
		}

		int handValue = handName.getOrder() * HOLDEM_HAND_MAXVALUE;
		for (Card card : hand) {
			handValue += card.rank.value;
		}
		handName.setValue(handValue);
		return handName;

	}

}
