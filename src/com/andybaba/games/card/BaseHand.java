package com.andybaba.games.card;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * <h1>Class Hand:</h1>
 * <p>
 * A basic class to represent a <b>hand of {@link Card}s</b>. Its essentially a
 * wrapper for {@link ArrayList} Class to represent a hand of card.
 * 
 * @version 0.8.0
 * @since Dec 11 2018
 * @author Andy
 * @see java.util.ArrayList
 * @see Card
 * @see Hand
 */

public class BaseHand implements Hand {

	/**
	 * The container of the cards
	 */
	private final ArrayList<Card> hand;

	/**
	 * A delimiter string used to separate the card strings
	 * <p>
	 * <b>Default:</b> system.lineSperator()
	 */
	public static String DELIMIER;
	private final int MAX_CARDS;
	private final Hand.Duplicates acceptDuplicate;
	private int hasDuplicate;

	/**
	 * Constructs a hand with a given <i>maximum number of cards</i> and if it can
	 * accept duplicated <b>{@code Card}s or not</b>
	 * 
	 * @param maxCards        Maximum cards a hand can keep
	 * @param acceptDuplicate If the hand accepts duplicated <b>Cards</b> or not
	 * @throws IllegalArgumentException If the <b>maxCards</b> is none positive
	 *                                  value.
	 */
	public BaseHand(int maxCards, Duplicates acceptDuplicate) throws IllegalArgumentException {
		DELIMIER = System.lineSeparator();
		hasDuplicate = 0;
		if (maxCards <= 0) {
			throw new IllegalArgumentException("A hand cannot have " + maxCards + " cards!");
		}
		this.acceptDuplicate = acceptDuplicate;
		this.MAX_CARDS = maxCards;
		hand = new ArrayList<Card>(maxCards);
	}

	/**
	 * Constructs a hand with a give <i>maximum number of cards</i>
	 * <p>
	 * A hand by default does not accept duplicated <b>{@link Card}s</b>.
	 * 
	 * @param maxCards Maximum cards a hand can keep
	 * @throws IllegalArgumentException If the <b>maxCards</b> is none positive
	 *                                  value.
	 */
	public BaseHand(int maxCards) throws IllegalArgumentException {
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
	@Override
	public void add(Card card) throws ArrayIndexOutOfBoundsException, ArrayStoreException {

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

	@Override
	public void randomize(int count)
			throws ArrayIndexOutOfBoundsException, IllegalArgumentException, UnsupportedOperationException {
		if (count < 0)
			throw new IllegalArgumentException("The hand cannot generate " + count + " number of cards");
		int remainingCount = this.MAX_CARDS - this.hand.size();
		if (remainingCount == 0)
			throw new UnsupportedOperationException("The hand is already full");
		if (count > remainingCount)
			throw new ArrayIndexOutOfBoundsException(
					"This hand does not have enough space to genreate " + count + " Cards.");
		for (int i = 0; i < count;) {
			try {
				hand.add(new Card());
				i++;
			} catch (ArrayStoreException exception) {
				continue;
			}
		}
	}

	/**
	 * Fills the <b>hand</b> with random <b>{@link Card}s</b>. If the hand is not
	 * empty already, it just does it for the remaining places in the hand with
	 * respect to the maximum size of the hand set when constructing the hand.
	 * <p>
	 * If <b>accept duplicates flag</b> of the <b>hand</b> is set to <i>yes</i>it
	 * will randomize with unique cards
	 * 
	 * @see BaseHand
	 */
	public void randomize() throws UnsupportedOperationException {
		this.randomize(this.MAX_CARDS - this.hand.size());
	}

	@Override
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
			out += card.toString() + BaseHand.DELIMIER;
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
	 * Returns the card at given <b>index</b>.
	 * 
	 * @param index
	 * @throws {@link IndexOutOfBoundsException} if the <b>index</b> is bigger than
	 *         current size of the hand
	 * @return
	 */
	@Override
	public Card show(int index) throws IndexOutOfBoundsException {
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

	@Override
	public Card remove(int index) throws ArrayIndexOutOfBoundsException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int maxSize() {
		return this.MAX_CARDS;
	}

}
