package net.andybaba.games.card;

/**
 * A card player should be able to perform a ge
 * 
 * @version 0.2.0
 * @since Dec 17 2018
 * @author andy
 */

public interface Player {


	public String showHand() ;

	void checkHand();

	void think();

	void decide();

	void draw();

	void action();

}
