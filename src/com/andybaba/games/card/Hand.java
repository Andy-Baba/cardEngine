package com.andybaba.games.card;

/**
 * Interface for hand, it is expected that the class implementing this interface
 * should create a hand with a specific number of slots for <b>{@code Card}s</b>
 * to be added later
 * 
 * @version 1.0.0
 * @since Dec 13 2018
 * @author Andy
 */
public interface Hand extends Iterable<Card> {

	/**
	 * <h3>Enum Duplicates:</h3>A <i>public</i> enum to show if a hand can accept
	 * duplicate {@link Card}s or not.
	 * 
	 * @author Andy
	 */
	public enum Duplicates {
		Yes(true), No(false);
		public final boolean value;

		private Duplicates(final boolean value) {
			this.value = value;
		}
	}

	/**
	 * Fill the hand with randomized cards
	 * 
	 * @throws UnsupportedOperationException If the hand is already full
	 */
	public void randomize() throws UnsupportedOperationException;

	/**
	 * Adds a specific number of random <b>{@link Card}s</b> to the hand
	 * 
	 * @param count The number of cards to be randomize and added to a hand
	 * @throws ArrayIndexOutOfBoundsException If the <b>count</b> is more than
	 *                                        remaining slots in a hand
	 * @throws UnsupportedOperationException  If the hand is already full
	 * @throws IllegalArgumentException       If the <i>count</i> is a negative
	 *                                        value
	 */
	public void randomize(int count)
			throws ArrayIndexOutOfBoundsException, IllegalArgumentException, UnsupportedOperationException;

	/**
	 * Adds the given card to the hand
	 * 
	 * @param card the <b>{@link Card}</b> to be added to the hand
	 * @throws ArrayIndexOutOfBoundsException If the hand does not have any empty
	 *                                        slots for the given card
	 */
	public void add(final Card card) throws ArrayIndexOutOfBoundsException;

	/**
	 * Shows the <b>{@link Card}</b> at the given <i>position</i>.
	 * 
	 * @param position The position of the card to be shown, <b>the first card is at
	 *                 position 1</b>
	 * @return The Card at the given <i>index</i>
	 * @throws ArrayIndexOutOfBoundsException If the <b>index</b> is pointing to and
	 *                                        empty slot or out of size
	 */
	public Card show(final int postition) throws ArrayIndexOutOfBoundsException;

	/**
	 * Removes the <b>{@link Card}</b> at the given <i>position</i>.
	 * 
	 * @param position The position of the card to be removed, <b>the first card is
	 *                 at position 1</b>
	 * @return If its successful it returns the <b>{@link Card}</b> which is removed
	 * @throws ArrayIndexOutOfBoundsException
	 */
	public Card remove(final int position) throws ArrayIndexOutOfBoundsException;

	/**
	 * Checks if the hand contains a given <b>{@link Card}</b>. It returns
	 * <b>true</b> at its first occurrence
	 * 
	 * @param card The <i>Card</i> to look for
	 * 
	 * @return <b>true</b> if it finds the card in the hand
	 */
	public boolean contains(final Card card);

	/**
	 * The current count of <b>{@link Card}s</b> in the hand.
	 * 
	 * @return The count of cards in the hand
	 */
	public int count();

	/**
	 * The maximum count of <b>{@link Card}s</b> a hand can hold.
	 * 
	 * @return Maximum number of cards a hand can hold
	 */
	public int maxSize();

	/**
	 * If a hand has no duplicates <b>{@link Card}s</b> it returns <b>0</b>,
	 * otherwise it will return the number of duplicate occurrences in the hand
	 * 
	 * @return Number of duplicate occurrences in the hand
	 */
	public int hasDuplicates();

	/**
	 * Checks if the hand is empty or not. For a hand to be empty it should contain
	 * zero cards. This can only happen if the hand is just generated or all of its
	 * cards are removed.
	 * 
	 * @return <i>True</i> if the hand is empty and <i>False</i> if its not
	 */
	public boolean isEmpty();

	/**
	 * Checks if the hand is full or not. For a hand to be full it should contain
	 * the maximum <b>{@linke Card}s</b> it can take.
	 * 
	 * @return <i>True</i> if the hand is full and <i>False</i> if its not
	 */
	public boolean isFull();

	/**
	 * The count of remaining empty slots in the hand. Basically the hand can accept
	 * this many <b>{@link Card}s</b> to be added to it.
	 * 
	 * @return Count of empty slots in the hand
	 */
	public int remainingSlots();
}