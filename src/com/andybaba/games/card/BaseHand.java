package com.andybaba.games.card;

import java.util.ArrayList;
import java.util.Iterator;

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
public class Hand implements Iterable<Card> {

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

	/**
	 * A delimiter character used to separate the card strings
	 */
	public static char DELIMITER;
	public final int MAX_CARDS;
	private final Duplicates acceptDuplicate;
	private int hasDuplicate;

	public Hand(int maxCards, Duplicates acceptDuplicate) throws IllegalArgumentException {
		DELIMITER = '\n';
		hasDuplicate = 0;
		if (maxCards <= 0) {
			throw new IllegalArgumentException("A hand cannot have " + maxCards + " cards!");
		}
		this.acceptDuplicate = acceptDuplicate;
		this.MAX_CARDS = maxCards;
		hand = new ArrayList<>(maxCards);
	}

	public Hand(int maxCards) throws IllegalArgumentException {
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
		if (this.hand.size() < this.MAX_CARDS)
			this.hand.add(card);
		else
			throw new ArrayIndexOutOfBoundsException("The hand can accpet only " + this.hand.size() + " cards.");
	}

	public boolean contains(Card card) {
		return this.hand.contains(card);
	}

	/**
	 * @return The String containing all the cards in the hand separated by
	 *         {@link DELIMITER}
	 */
	@Override
	public String toString() {
		String out = "";
		for (Card card : hand) {
			out += card.toString() + Hand.DELIMITER;
		}
		return out;
	}

	/**
	 * Specifies the number of duplicate <b>{@link Card}</b>s in a hand.
	 * 
	 * @return 0 if there is no duplicates yet, or a count of existing duplicates
	 *         <b>Cards</b>
	 */
	public int hasDuplicates() {
		return hasDuplicate;
	}

	/**
	 * Gets the current number of cards in the hand
	 * 
	 * @return Current number of cards in the hand
	 */
	public int cardsCount() {
		return this.hand.size();
	}

	/**
	 * Fills the <b>hand</b> with random <b>{@link Card}s</b>. If the hand is not
	 * empty already, it just does it for the remaining places in the hand with
	 * respect to the maximum size of the hand set when constructing the hand.
	 * <p>
	 * If <b>accept duplicates flag</b> of the <b>hand</b> is set to <i>yes</i>it
	 * will randomize with unique cards
	 * 
	 * @see Hand
	 */
	public void randomize() {
		int remainingCunt = this.MAX_CARDS - this.hand.size();

		for (int i = 0; i < remainingCunt;) {
			Card newCard = new Card();
			if (this.acceptDuplicate == Hand.Duplicates.No)
				if (hand.contains(newCard))
					continue;
			hand.add(newCard);
			i++;
		}
	}

	/**
	 * Returns the card at given <b>index</b>.
	 * 
	 * @param index
	 * @throws {@link IndexOutOfBoundsException} if the <b>index</b> is bigger than
	 *         current size of the hand
	 * @return
	 */
	public Card showCartAt(int index) throws IndexOutOfBoundsException {
		return hand.get(index);
	}

	@Override
	public Iterator<Card> iterator() {
		Iterator<Card> card = new Iterator<Card>() {

			private int currentIndex = 0;

			@Override
			public boolean hasNext() {
				return currentIndex < hand.size() && hand.get(currentIndex) != null;
			}

			@Override
			public Card next() {				
				return hand.get(currentIndex++);
			}

		};
		return card;
	}

}
