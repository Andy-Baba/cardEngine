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
	protected final ArrayList<Card> hand;

	/**
	 * A delimiter string used to separate the card strings
	 * <p>
	 * <b>Default:</b> {@link System#lineSeparator() }
	 */
	public static String SEPARATOR = System.lineSeparator();

	/**
	 * The maximum number of {@link Card}s a hand can keep. It is a final value and
	 * initialize at the construction of the hand
	 * 
	 * @see BaseHand#BaseHand(int)
	 */
	private final int MAX_CARDS;
	private final Hand.Duplicates acceptDuplicate;

	/**
	 * The count of duplicated occurrences in a hand
	 */
	private int hasDuplicate;

	/**
	 * Constructs a hand with a given {@link } and if it can accept duplicated
	 * <b>{@code Card}s</b>
	 * 
	 * @param maxCards        Maximum cards a hand can keep
	 * @param acceptDuplicate If the hand accepts duplicated <b>Cards</b> or not
	 * @throws IllegalArgumentException If the <b>maxCards</b> is none positive
	 *                                  value.
	 * @See {@link Hand.Duplicates}
	 */
	public BaseHand(int maxCards, Duplicates acceptDuplicate) throws IllegalArgumentException {
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
	public BaseHand(final int maxCards) throws IllegalArgumentException {
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
	public void add(final Card card) throws ArrayIndexOutOfBoundsException, ArrayStoreException {

		if (this.isFull())
			throw new ArrayIndexOutOfBoundsException("The hand can accpet only " + this.maxSize() + " cards.");
		if (this.acceptDuplicate.value) {
			if (this.contains(card))
				this.hasDuplicate++;
		} else {
			if (this.hand.contains(card))
				throw new ArrayStoreException("The hand already contains " + card + "!");
		}
		this.hand.add(card);
	}

	@Override
	public void randomize(final int count)
			throws ArrayIndexOutOfBoundsException, IllegalArgumentException, UnsupportedOperationException {
		if (count < 0)
			throw new IllegalArgumentException("The hand cannot generate " + count + " number of cards");
		if (this.isFull())
			throw new UnsupportedOperationException("The hand is already full");
		if (count > this.remainingSlots())
			throw new ArrayIndexOutOfBoundsException(
					"This hand does not have enough space to genreate " + count + " Cards.");
		for (int i = 0; i < count;) {
			try {
				this.add(new Card());
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
	public boolean contains(final Card card) {
		return this.hand.contains(card);
	}

	/**
	 * @return The String containing all the cards in the hand separated by
	 *         {@link BaseHand#DELIMIER}
	 */
	@Override
	public String toString() {
		String out = "";
		for (Card card : hand) {
			out += card.toString() + BaseHand.SEPARATOR;
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

	@Override
	public int count() {
		return this.hand.size();
	}

	@Override
	public Card show(final int position) throws IndexOutOfBoundsException {
		return hand.get(position - 1);
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
	public Card remove(final int position) throws ArrayIndexOutOfBoundsException {
		return this.hand.remove(position - 1);
	}

	@Override
	public int maxSize() {
		return this.MAX_CARDS;
	}

	@Override
	public boolean isFull() {
		return this.hand.size() == this.MAX_CARDS;
	}

	@Override
	public boolean isEmpty() {
		return this.hand.isEmpty();
	}

	@Override
	public int remainingSlots() {
		return this.MAX_CARDS - this.hand.size();
	}

	/**
	 * Compares hands together. The hands are being compared should have the same
	 * amount of cards in them (The size can be different). It compares each card
	 * with its corresponding card in the other hand therefore you should sort hands
	 * before comparing them by this method.
	 * 
	 * @throws IndexOutOfBoundsException When the count of hands are being compared
	 *                                   is not equal
	 */
	@Override
	public boolean equals(Object obj) throws IndexOutOfBoundsException {
		BaseHand hand = (BaseHand) obj;
		final int thisCount = this.count();
		if (thisCount != hand.count())
			throw new IndexOutOfBoundsException(
					"Size mismatch, This has " + thisCount + " cads, but obj has " + hand.count() + " cards.");
		boolean result = true;
		for (int i = 1; i < -thisCount && result; i++)
			result = this.show(i) == hand.show(i);

		return result;
	}

	/**
	 * It sorts the hand based on the {@link Card#getValue()} of the cards.
	 */
	@Override
	public void sort() {
		this.hand.sort(Card.BY_VALUE);
	}
}
