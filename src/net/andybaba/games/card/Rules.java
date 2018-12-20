package net.andybaba.games.card;

import java.time.Duration;
import java.util.HashMap;

import com.sun.jdi.ObjectCollectedException;

/**
 * It contains the general structure for the rules of a card game, therefore
 * each card game that wishes to use <b>CEL</b> has to extend this as a <i>final
 * class</i>. <b>Notice that each class extending <i>Rules</i>, will inherit its
 * <i>SingleTon</i> feature as well, therefore it can be instantiated only once
 * and used globally throughout the code</b>. If a second attempt is made to
 * instantiate this class, an {@link ObjectCollectedException} will be thrown by
 * its constructor.
 * <p>
 * This class also contains the essential general parameters necessary for
 * regulating any card game. All the parameters can hold only none negative
 * values. For more details, make sure to check the list of parameters of Rule
 * class.
 * 
 * @version 0.9.0
 * @since Dec 20 2018
 * @author Andy
 */
public abstract class Rules {

	protected int handSize;
	protected int maxPlayers;
	protected int bettingRoundsCount;
	protected int maxCommonCards;
	protected int pocketCardSize;
	protected long betAmountLimit;
	protected boolean canChangeCards;
	protected Duration durationPerTurnLimit;

	/**
	 * Since this is a <i>abstract class</i> a normal SingleTon design cannot be
	 * applied so a classLock flag is considered to implement the SingleTon
	 * approach.
	 */
	private static boolean classLock = false;

	/**
	 * It applies the <b>SingleTon</b> design pattern on any class that extends
	 * {@link Rules}
	 * 
	 * @throws ObjectCollectedException If the instantiated more than once
	 */
	protected Rules() throws ObjectCollectedException {
		if (classLock)
			throw new ObjectCollectedException("Already instantiated! This class can be instantiated only once...");
		classLock = true;
	}

	public class HandName {
		// TODO decide if want to give name
		protected int value;

		private HandName(final int value) {
			this.value = value;
		}
	}

	/**
	 * Any rule should define the value of each hand in the game.
	 * 
	 * @param hand
	 * @return The value of the hand
	 */
	public abstract int calculateHandValue(Hand hand);

	public abstract void nameTheHand();

	protected final HashMap<Card.Rank, Integer> countSameRanks(Hand hand) {
		// TODO decide about where this function should be I think may be base hand or
		// hand would be a better place
		HashMap<Card.Rank, Integer> result = new HashMap<Card.Rank, Integer>();
		for (Card card : hand) {
			Integer count = result.get(card.rank);
			result.put(card.rank, count == null ? 1 : count++);
		}
		return result;
	}

	/**
	 * TODO decide about where this function should be I think may be base hand or
	 * hand would be a better place Specifys the
	 * 
	 * @param hand
	 * @return
	 */
	protected final HashMap<Card.Suite, Integer> countSameSuites(Hand hand) {
		// TODO decide about where this function should be I think may be base hand or
		// hand would be a better place
		HashMap<Card.Suite, Integer> result = new HashMap<Card.Suite, Integer>();
		for (Card card : hand) {
			Integer count = result.get(card.suite);
			result.put(card.suite, count == null ? 1 : count++);
		}
		return result;
	}

	/**
	 * Each card game has a hand with a predefined number of cards. <b>Zero means
	 * there is no limitation</b>.
	 * 
	 * @return The maximum cards for each hand
	 */
	public int getHandSize() {
		return handSize;
	}

	/**
	 * Each card game has a predefined limitation for the players that can
	 * participate that game.
	 * 
	 * @return The maximum players that can participate a game.
	 */
	public int getMaxPlayers() {
		return maxPlayers;
	}

	/**
	 * Each card game has a predefined number of betting rounds. <b>Zero means there
	 * is no betting in the game</b>.
	 * 
	 * @return The number of betting rounds allowed in each round of a game.
	 */
	public int getBettingRoundsCount() {
		return bettingRoundsCount;
	}

	/**
	 * Many card games has some common card(s) also known as board card(s) which are
	 * shared to all the players of the game. <b>Zero means there is no common
	 * cards</b>.
	 * 
	 * @return The maximum number of common cards
	 */
	public int getMaxCommonCards() {
		return maxCommonCards;
	}

	/**
	 * Each card game has a predefined limitation for the amount which can be bet in
	 * any betting turn. <b>Zero means there is no limitation</b>.
	 * 
	 * @return The limit of betting amount in each betting round
	 */
	public long getBetAmountLimit() {
		return betAmountLimit;
	}

	/**
	 * Normally each game has a predefined duration that each player can decide in
	 * each turn. <b>Null means there is no limitation</b>.
	 * 
	 * @return
	 */
	public Duration getDurationPerTurnLimit() {
		return durationPerTurnLimit;
	}

}
