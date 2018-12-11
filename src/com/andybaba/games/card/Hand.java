package com.andybaba.games.card;

import java.util.ArrayList;

/**
 * <h1>Class Hand:</h1>
 * <p>
 * A basic class to represent a <b>hand of {@link Card}s</b>. Its essentially is
 * a wrapper for {@link ArrayList} Class.
 * 
 * @version 0.5.0
 * @since Dec 11 2018
 * @author Andy
 * @see java.util.ArrayList
 * @see Card
 */
public class Hand {

	/**
	 * <h3>Enum Duplicates:</h3>A <i>public</i> enum to show if a hand can accept
	 * duplicate {@link Card}s or not.
	 * 
	 * @author Andy
	 */
	public enum Duplicates {
		Yes(true), No(false);
		public final boolean value;

		private Duplicates(boolean value) {
			this.value = value;
		}
	}

	private final ArrayList<Card> hand;
	public static char delimiter;
	public final int maxCards;
	private final Duplicates acceptDuplicate;
	private int hasDuplicate;

	public Hand(int maxCards, Duplicates acceptDuplicate) {
		delimiter = '\n';
		hasDuplicate = 0;
		this.acceptDuplicate = acceptDuplicate;
		this.maxCards = maxCards;
		hand = new ArrayList<>(maxCards);
	}

	public Hand(int maxCards) {
		this(maxCards, Duplicates.No);
	}

	/**
	 * Adds a given card to the hand. It will check the <b>hasDuplicate</b> flag, if
	 * the flag is false, it will not accept duplicated <b>cards</b>
	 * 
	 * @param card The card to be added to the hand
	 * @throws ArrayIndexOutOfBoundsException If the hand is already full
	 * @throws ArrayStoreException            If the <b>hand</b> does not accept
	 *                                        duplicate <b>cards</b>
	 */
	public void addCard(Card card) throws ArrayIndexOutOfBoundsException, ArrayStoreException {
		if (this.contains(card)) {
			if (this.acceptDuplicate.value)
				this.hasDuplicate++;
			else
				throw new ArrayStoreException("The hand already contains " + card + "!");
		}
		if (this.hand.size() < this.maxCards)
			this.hand.add(card);
		else
			throw new ArrayIndexOutOfBoundsException("The hand can accpet only " + this.hand.size() + " cards.");
	}

	public boolean contains(Card card) {
		return this.hand.contains(card);
	}

	/**
	 * @return The String containing all the cards in the hand sep
	 */
	@Override
	public String toString() {
		String out = "";
		for (Card card : hand) {
			out += card.toString() + Hand.delimiter;
		}
		return out;
	}

	/**
	 * Specifies the number of duplicate <b>{@link Card}</b>s in a hand.
	 * @return 0 if there is no duplicates yet, or a count of existing duplicates <b>Cards</b>
	 */
	public int hasDuplicates() {
		return hasDuplicate;
	}
}
