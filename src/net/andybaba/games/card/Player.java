package net.andybaba.games.card;

/**
 * <p>
 * A card player should be able to perform following operations:
 * 
 * @version 0.2.0
 * @since Dec 17 2018
 * @author andy
 */

public interface Player {

	/**
	 * Sets the rule of the game, the {@link Player} will play the game based on the
	 * rules
	 * 
	 * @param rule The rules regulating the games
	 */
	public void setRule(Rules rule);

	public String showHand();

	void checkHand();

	void think();

	void decide();

	void draw();

	void action();

}
