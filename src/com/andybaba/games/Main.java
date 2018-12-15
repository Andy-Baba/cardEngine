package com.andybaba.games;

import com.andybaba.games.card.Card;
import com.andybaba.games.card.BaseHand;

public class Main {
	public static void main(String[] argsss) {
		BaseHand hand = new BaseHand(5, BaseHand.Duplicates.Yes);
		Card card1 = new Card();
		Card card2 = new Card();
		hand.add(card2);

		System.out.println(hand);
		System.out.print(hand.hasDuplicates());
	}
}
