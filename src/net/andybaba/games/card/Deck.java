package net.andybaba.games.card;

/**
 * <h2>Class Deck</h2>
 * 
 * It keeps a standard deck of card when constructed without any parameters
 * using {@link Deck#Deck()}. However, if needed, {@link Deck#Deck(int)} can be
 * used to define a multiple deck for games like
 * <a href="https://en.wikipedia.org/wiki/Rummy">Rummy</a> or
 * <a href="https://en.wikipedia.org/wiki/Blackjack">BlackJack</a>
 * 
 * @version 1.0.2
 * @since Dec 10 2018
 * @author Andy
 * @see BaseHand
 */

public final class Deck extends BaseHand {

	/**
	 * A limitation to the actual number of decks that a {@link Deck} can hold.
	 * <b>The default value is 10</b>.
	 */
	public final static int MAX_DECKS = 10;
	private final static int MAX_CARD = 52;
	private final int deckNumber;

	public interface Shuffler {
		// TODO Shuffler for lamda expression for the deck to be used by the dealer
		public void shuffle();
	}

	/**
	 * Constructs a none standard deck where duplicates are allowed and number of
	 * actual decks can be determined by passing the deck number.
	 * 
	 * @param number Number of actual decks to be generated
	 * @throws IllegalArgumentException If number of deck value is illegal
	 *                                  (negative, zero or larger than
	 *                                  {@link Deck#MAX_DECKS}
	 */
	public Deck(final int number) throws IllegalArgumentException {
		super(number * Deck.MAX_CARD, Hand.Duplicates.Yes);
		if (number > Deck.MAX_DECKS)
			throw new IllegalArgumentException(
					"Allowed number of decks is " + Deck.MAX_DECKS + " but " + number + " is requested.");

		this.deckNumber = number;
		this.intitialize();
	}

	/**
	 * Constructs a <i>standard</i> deck of cards which is 52 {@link Card}s
	 * including {@link Card.Rank#Ace} to {@link Card.Rank#King} of each
	 * {@link Card.Suite}. There is no duplications in a standard {@link Deck}.
	 */
	public Deck() {
		super(Deck.MAX_CARD, Hand.Duplicates.No);
		this.deckNumber = 1;
		this.intitialize();
	}

	/**
	 * Gets the number of actual decks inside a {@link Deck}
	 * 
	 * @return The number of actual decks
	 */
	public int howMany() {
		return this.deckNumber;
	}

	public void shuffle(Shuffler shuffler) {
		
		// TODO Have to create shuffle methods
	}

	/**
	 * Initialize a deck with cards in order
	 */
	private void intitialize() {
		for (int i = 0; i < this.deckNumber; i++)
			for (Card.Suite suite : Card.Suite.values())
				for (Card.Rank rank : Card.Rank.values())
					super.add(new Card(rank, suite));

	}
}
