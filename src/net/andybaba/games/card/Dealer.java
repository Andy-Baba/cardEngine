package net.andybaba.games.card;

/**
 * The Dealer is meant to be the <i>runner</i> of any game. The dealer should
 * know the {@link Rules} of the game, and manages the players, deck and stages
 * of the game based on the game rules.
 * 
 * @author Andy
 * @version 0.6.0
 * @since Dec 21 2018
 *
 */

public interface Dealer {

	void knowTheRules(final Rules rules);

	public interface State {
		public int getValue();
	}

	public interface Shuffler {
		public int shuffle();
	}

	public void generateDeck();

	public void shuffle(Shuffler shuffler);

	public void startTheRound();

	public void nextTurn();

	public void endTheRound();
}
