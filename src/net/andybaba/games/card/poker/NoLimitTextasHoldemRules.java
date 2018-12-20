package net.andybaba.games.card.poker;

import net.andybaba.games.card.Hand;
import net.andybaba.games.card.Rules;

public final class NoLimitTextasHoldemRules extends Rules {
	


	public NoLimitTextasHoldemRules() {
		
		
		super.betAmountLimit = 0; // zero for no limit
		super.durationPerTurnLimit = null;
		super.bettingRoundsCount = 4; // before flop, flop, turn, river
		super.handSize = 5;			// 5 cards out of best 7 cards
		super.maxCommonCards = 5;	//3 on the flop, 1 on the turn & 1 on the river
		super.maxPlayers = 10;
		super.pocketCardSize = 2;	// 2
		super.canChangeCards = false; 
	}

	@Override
	public int calculateHandValue(Hand hand) {
		
		
		return 0;
	}

	@Override
	public void nameTheHand() {
		// TODO Auto-generated method stub
		
	}

}
