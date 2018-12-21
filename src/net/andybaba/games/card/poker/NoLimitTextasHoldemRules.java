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

	public enum HandName {
		HighCard(0), Pair(1), TwoPair(2), ThreeOfaKind(3), Streight(4), Flush(5), FullHouse(6), FourOfaKind(7),
		StreightFlush(8);
		public int value;

		private HandName(final int value) {
			this.value = value;
		}

		public static HandName convertInt(final int value) throws ArrayIndexOutOfBoundsException {
			return values()[value];
		}
	}

	private HandName handName;

	@Override
	public int calculateHandValue(final BaseHand hand) {
		final HashMap<Card.Rank, Integer> sameRanks = hand.countSameRanks();
		final HashMap<Card.Suite, Integer> sameSuites = hand.countSameSuites();
		if (sameRanks.size() == hand.count()) {
			HandName temp = this.handName = HandName.HighCard;
			hand.sort();
			if (hand.show(hand.count()).rank.value - hand.show(1).rank.value == hand.count() - 1
					|| (hand.show(hand.count()).rank.value - hand.show(2).rank.value == hand.count() - 2
							&& hand.show(hand.count()).rank.value == Rank.values().length)) {
				temp = this.handName = HandName.Streight;
			}
			if (sameSuites.containsValue(hand.count())) {
				this.handName = HandName.Flush;
				if (temp == HandName.Streight)
					this.handName = HandName.StreightFlush;
			}
		} else if (sameRanks.size() == (hand.count() - 1)) {
			this.handName = HandName.Pair;
		} else if (sameRanks.size() == (hand.count() - 2)) {
			this.handName = HandName.TwoPair;
			if (sameRanks.containsValue(3))
				this.handName = HandName.ThreeOfaKind;
		} else {
			this.handName = HandName.FullHouse;
			if (sameRanks.containsValue(4))
				this.handName = HandName.FourOfaKind;
		}
	
		int handValue = this.handName.value;
		for (Card card : hand)
			handValue += card.rank.value;
		return this.handName.value;
		//return handName;
	}

	@Override
	public void nameTheHand() {
		// TODO Auto-generated method stub

	}

	public HandName getHandName() {
		return this.handName;
	}
}
