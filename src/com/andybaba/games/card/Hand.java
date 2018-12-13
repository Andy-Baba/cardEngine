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

		private Duplicates(boolean value) {
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
	public void add(Card card) throws ArrayIndexOutOfBoundsException;

	/**
	 * Shows the <b>{@link Card}</b> at the given position (<i>index</i>).
	 * 
	 * @param index the position of the card to be shown
	 * @return The Card at the given <i>index</i>
	 * @throws ArrayIndexOutOfBoundsException If the <b>index</b> is pointing to and
	 *                                        empty slot or out of size
	 */
	public Card show(int index) throws ArrayIndexOutOfBoundsException;

	/**
	 * It removes a <b>{@link Card}</b>at a given position
	 * 
	 * @param index
	 * @return
	 * @throws ArrayIndexOutOfBoundsException
	 */
	public Card remove(int index) throws ArrayIndexOutOfBoundsException;

	/**
	 * Checks if the hand contains a given <b>{@link Card}</b>. It returns
	 * <b>true</b> at its first occurrence
	 * 
	 * @param card The <i>Card</i> to look for
	 * 
	 * @return <b>true</b> if it finds the card in the hand
	 */
	public boolean contains(Card card);

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

}
