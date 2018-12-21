package net.andybaba.games.card.poker;

import net.andybaba.games.card.BaseHand;
import net.andybaba.games.card.Player;
import net.andybaba.games.card.Rules;

public abstract class PokerPlayer implements Player {

	protected BaseHand hand;
	protected Rules rules;

	public PokerPlayer(final Rules rules) {
		this.setRule(rules);
	}

	@Override
	public String showHand() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void checkHand() {
		// TODO Auto-generated method stub

	}

	@Override
	public void think() {
		// TODO Auto-generated method stub

	}

	@Override
	public void decide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub

	}

	@Override
	public void action() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setRule(Rules rule) {
		// TODO Auto-generated method stub

	}

}
