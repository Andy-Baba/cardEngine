package net.andybaba.games.card;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * <h1>Class Hand:</h1>
 * <p>
 * A basic class to represent a <b>hand of {@link Card}s</b>. Its essentially a
 * wrapper for {@link ArrayList} Class to represent a hand of card.
 * 
 * @version 1.0.0
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
	 * Adds a given {@link Card} to the hand at a given <i>position</b>. It will
	 * check the <b>hasDuplicate</b> flag, if the flag is false, it will not accept
	 * duplicated <b>cards</b>. Since this method is using the
	 * {@link ArrayList#add(int, Object)} internally, it should be noticed that the
	 * cards will be moved a slot to the right, when a new card is added to the
	 * hand.
	 * 
	 * @param position The position of which the <i>card</i> to be inserted. Like
	 *                 the rest of the {@link Hand} class the position of a card in
	 *                 the hand starts from 1 (not zero).
	 * @param card     The card to be added to the hand
	 * @throws ArrayIndexOutOfBoundsException If the hand is already full OR the
	 *                                        given <i>position</i> is not valid
	 * @throws ArrayStoreException            If the <b>hand</b> does not accept
	 *                                        duplicate <b>cards</b>
	 */
	@Override
	public void add(final int position, final Card card) throws ArrayIndexOutOfBoundsException, ArrayStoreException {
		if (position < 1)
			throw new ArrayIndexOutOfBoundsException(
					"A hand starts from position 1, so " + position + " is not acceptable.");
		if (position > this.hand.size() + 1)
			throw new ArrayIndexOutOfBoundsException("Given position " + position
					+ " should be smaller current number of cards in the hand:" + this.count());
		if (this.isFull())
			throw new ArrayIndexOutOfBoundsException("The hand can accpet only " + this.maxSize() + " cards.");
		if (this.acceptDuplicate.value) {
			if (this.contains(card))
				this.hasDuplicate++;
		} else {
			if (this.hand.contains(card))
				throw new ArrayStoreException("The hand already contains " + card + "!");
		}
		this.hand.add(position - 1, card);
	}

	/**
	 * Adds a given {@link Card} to the end of the hand.
	 * 
	 * @param card The card to be added to the hand
	 * @throws ArrayIndexOutOfBoundsException If the hand is already full
	 * @throws ArrayStoreException            If the <b>hand</b> does not accept
	 *                                        duplicate <b>cards</b>
	 * @see {@link BaseHand#add(int, Card)}
	 */
	public void add(final Card card) throws ArrayIndexOutOfBoundsException, ArrayStoreException {
		this.add(this.hand.size() + 1, card);
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
		BaseHand actual = (BaseHand) obj;
		final int thisCount = this.count();
		if (thisCount != actual.count())
			throw new IndexOutOfBoundsException(
					"Size mismatch, This has " + thisCount + " cads, but obj has " + actual.count() + " cards.");
		boolean result = true;
		for (int i = 1; i <= thisCount && result; i++)
			result = this.show(i).equals(actual.show(i));

		return result;
	}

	/**
	 * It sorts the hand based on the {@link Card#getValue()} of the cards from
	 * lowest to lowest highest.
	 */
	@Override
	public void sort() {
		this.hand.sort(Card.BY_VALUE);
	}

	/**
	 * It checks the <i>hand</i> and specifies the repetition of each
	 * {@link Card.Rank} in the hand. Therefore, basically if the size of the
	 * returned {@linkplain HashMap hash map} is equal to the hand it means each
	 * rank happened only once in the hand.
	 * <p>
	 * Obviously if the number of <{@linkplain Card cards} in the hand is more than
	 * total number of ranks in a deck there will be repetitions of ranks in the
	 * hand.
	 * 
	 * @return A {@linkplain HashMap hash map} showing the ranks in a hand and their
	 *         number of occurrences in the hand
	 */
	public HashMap<Card.Rank, Integer> countSameRanks() {
		HashMap<Card.Rank, Integer> result = new HashMap<Card.Rank, Integer>();
		for (Card card : hand) {
			Integer count = result.get(card.rank);
			result.put(card.rank, count == null ? 1 : count + 1);
		}
		return result;
	}

	/**
	 * It checks the <i>hand</i> and specifies the repetition of each
	 * {@link Card.Suite} in the hand. Therefore, basically if the size of the
	 * returned {@linkplain HashMap hash map} is equal to the hand it means each
	 * rank happened only once in the hand.
	 * <p>
	 * Obviously if the number of <{@linkplain Card cards} in the hand is more than
	 * total number of suites in a deck there will be repetitions of ranks in the
	 * hand.
	 * 
	 * @return A {@linkplain HashMap hash map} showing the ranks in a hand and their
	 *         number of occurrences in the hand
	 */
	public HashMap<Card.Suite, Integer> countSameSuites() {
		HashMap<Card.Suite, Integer> result = new HashMap<Card.Suite, Integer>();
		for (Card card : hand) {
			Integer count = result.get(card.suite);
			result.put(card.suite, count == null ? 1 : count + 1);
		}
		return result;
	}

}
