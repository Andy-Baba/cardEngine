package com.andybaba.games.card;

import java.util.ArrayList;

public class Hand {

	private final ArrayList<Card> hand;

	public final int maxCards;

	public Hand(int maxCards) {
		this.maxCards = maxCards;
		hand = new ArrayList<>(maxCards);
	}

	/**
	 * Adds a given card to the hand
	 * @param card The card to be added to the hand
	 * @throws ArrayIndexOutOfBoundsException If the hand is already full
	 */
	public void addCard(Card card) throws ArrayIndexOutOfBoundsException {
		if (this.hand.size() < 5)
			this.hand.add(card);
		else
			throw new ArrayIndexOutOfBoundsException("The hand can accpet only " + this.hand.size() + " cards.");
	}

	
	
}
