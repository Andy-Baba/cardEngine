package com.andybaba.games.card;

/**
 * Interface for hand, it should create a hand with a specific number of slots
 * for <b>{@code Card}s</b> to be added later
 * 
 * @version 1.0.0
 * @since Dec 13 2018
 * @author Andy
 */
public interface Hand extends Iterable<Card> {

	/**
	 * Randomize the hand with
	 */
	public void randomize();

	/**
	 * Adds a specific number of random <b>{@code Card}s</b> to the hand
	 * 
	 * @param count The number of cards to be randomize and added to a hand
	 * @throws ArrayIndexOutOfBoundsException If the <b>count</b> is more than
	 *                                        remaining slots in a hand
	 */
	public void randomize(int count) throws ArrayIndexOutOfBoundsException;

	/**
	 * Adds the given card to the hand
	 * 
	 * @param card the <b>Card</b> to be added to the hand
	 * @throws ArrayIndexOutOfBoundsException If the hand is does not have any empty
	 *                                        slots for the given card
	 */
	public void add(Card card) throws ArrayIndexOutOfBoundsException;

	/**
	 * Shows the <b>{@code Card}</b> at the given position (<i>index</i>).
	 * 
	 * @param index the position of the card to be shown
	 * @return The Card at the given <i>index</i>
	 * @throws ArrayIndexOutOfBoundsException If the <b>index</b> is pointing to and
	 *                                        empty slot or out of size
	 */
	public Card show(int index) throws ArrayIndexOutOfBoundsException;

	/**
	 * It 
	 * @param index
	 * @return
	 * @throws ArrayIndexOutOfBoundsException
	 */
	public Card remove(int index) throws ArrayIndexOutOfBoundsException;

	public boolean contains(Card card);

	public int count();

	public int maxSize();

	public int hasDuplicates();

}
