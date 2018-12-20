package net.andybaba.games.card;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * <h2>Class Card</h2>
 * <p>
 * A base class for playing cards. It also contains two public <i>enums</i> for
 * <b>{@link Suite}</b> and <b>{@link Rank}</b> of the cards.
 * 
 * @version 1.0.0
 * @since Dec 10 2018
 * @author Andy
 * @see Comparable
 */
public class Card implements Comparable<Card> {

	/**
	 * A public <b>enum</b> to address the <b>Suite</b> of the cards. The value of
	 * the suite is <b>final</b> and its based on U.S. Standard e.g. Club, Diamond,
	 * Heart and Spade being the highest.
	 * 
	 * @author Andy
	 *
	 */
	public enum Suite {
		Club(1), Diamond(2), Heart(3), Spade(4);

		public final int value;

		private Suite(final int value) {
			this.value = value;
		}

		private static final List<Suite> VALUES = Collections.unmodifiableList(Arrays.asList(values()));

		/**
		 * Pseudo random a card suite with default seed
		 * 
		 * @return A random <b>{@link Suite}</b>
		 */
		public static Suite random() {
			Random random = new Random();
			return VALUES.get(random.nextInt(VALUES.size()));
		}
	}

	/**
	 * A public <b>enum</b> to address the <b>Rank</b> of the cards. The value of
	 * the rank is <b>final</b> and starts from Ace being the lowest to King.
	 * 
	 * @author Andy
	 *
	 */
	public enum Rank {
		Ace(1), Deuce(2), Trey(3), Four(4), Five(5), Six(6), Seven(7), Eight(8), Nine(9), Ten(10), Jack(11), Queen(12),
		King(13);

		public final int value;

		private Rank(final int value) {
			this.value = value;
		}

		private static final List<Rank> VALUES = Collections.unmodifiableList(Arrays.asList(values()));

		/**
		 * Pseudo random a card rank with default seed
		 * 
		 * @param bound The upper bound to randomize
		 * @return A random <b>{@link Rank}</b>
		 */
		public static Rank random(final Rank bound) {
			Random random = new Random();
			return VALUES.get(random.nextInt(bound.value));
		}

		/**
		 * Pseudo random a card rank with default seed
		 * 
		 * @return A random <b>{@link Rank}</b>
		 */
		public static Rank random() {
			return random(VALUES.get(VALUES.size() - 1));
		}
	}

	/**
	 * If you want to sort the hand by the value of the cards you can use this
	 * {@link Comparator}
	 */
	public final static Comparator<Card> BY_VALUE = new Comparator<Card>() {
		@Override
		public int compare(final Card card1, final Card card2) {
			return card1.value - card2.value;
		}
	};

	/**
	 * If you want to sort the hand by the value of the cards you can use this
	 * {@link Comparator}
	 */
	public final static Comparator<Card> BY_RANK = new Comparator<Card>() {
		@Override
		public int compare(final Card card1, final Card card2) {
			// let your comparator look up your car's color in the custom order
			return card1.rank.value - card2.rank.value;
		}
	};

	private final int value;
	public final Suite suite;
	public final Rank rank;

	/**
	 * Constructs a <b>Card</b> with a given <b>Suite</b> and a <b>Rank</b>.
	 * 
	 * @param rank  The Rank of the card
	 * @param suite The Suite of the card
	 * @see Card.Suite
	 * @see Card.Rank
	 */
	public Card(final Rank rank, final Suite suite) {
		this.rank = rank;
		this.suite = suite;
		this.value = (suite.value - 1) * 13 + rank.value;
	}

	/**
	 * Constructs a random <b>Card</b>.
	 * 
	 */
	public Card() {
		this(Rank.random(), Suite.random());
	}

	/**
	 * Gets the <b>value</b> of the card
	 * 
	 * @return value of the card
	 */
	public int getValue() {
		return this.value;
	}

	/**
	 * @return The text form of the card
	 */
	@Override
	public String toString() {
		return this.rank + " of " + this.suite;
	}

	@Override
	public int compareTo(final Card card) {
		int tmp = this.rank.value - card.rank.value;
		return tmp;
	}

	@Override
	public boolean equals(final Object card) {
		return this.value == ((Card) card).value;
	}
}
