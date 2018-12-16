package com.andybaba.games.card;

/**
 * @version 0.5.0
 * @author Andy
 * @since Dec 16 2018
 */

public class Deck extends BaseHand {

	private final static int MAX_CARD = 52;

	public Deck(final int maxCards) throws IllegalArgumentException {
		super(maxCards, Hand.Duplicates.Yes);
	}

	/**
	 * Constructs a <i>standard</i> deck of cards
	 */
	public Deck() {
		super(MAX_CARD, Hand.Duplicates.No);
	}

}
