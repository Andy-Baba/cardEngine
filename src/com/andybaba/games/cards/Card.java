package com.andybaba.games.cards;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * A base class for playing cards. It also contains two public <i>enums</i> for
 * <b>{@link Suite}</b> and <b>{@link Rank}</b> of the cards.
 * 
 * @version 0.5.0
 * @since Dec 10 2018
 * @author Andy
 *
 */

public class Card {

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

		private Suite(int value) {
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
		Ace(1), Duce(2), Trey(3), Four(4), Five(5), Six(6), Seven(7), Eight(8), Nine(9), Ten(10), Jack(11), Queen(12),
		King(13);

		public final int value;

		private Rank(int value) {
			this.value = value;
		}

		private static final List<Rank> VALUES = Collections.unmodifiableList(Arrays.asList(values()));

		/**
		 * Pseudo random a card rank with default seed
		 * 
		 * @return A random <b>{@link Rank}</b>
		 */
		public static Rank random() {
			Random random = new Random();
			return VALUES.get(random.nextInt(VALUES.size()));
		}
	}

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
	public Card(Rank rank, Suite suite) {
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

	public int getValue() {
		return this.value;
	}
}
