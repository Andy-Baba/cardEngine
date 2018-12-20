package net.andybaba.games.card.poker;

import net.andybaba.games.card.BaseHand;
import net.andybaba.games.card.Card;

/**
 * In <b>Texas Holdem Poker</b> the suite of cards are not hand important in
 * ranking the hands, therefore the {@link BaseHand#sort()} is overridden to
 * make sure hands are sorted based on their {@linkplain Card.Rank ranks}.
 * 
 * @version 1.0.0
 * @since Dec 20 2018
 * @author Andy
 */
public class TexasHand extends BaseHand {

	public TexasHand(int maxCards) throws IllegalArgumentException {
		super(maxCards);
	}

	@Override
	public void sort() {
		this.hand.sort(Card.BY_RANK);
	}
}
