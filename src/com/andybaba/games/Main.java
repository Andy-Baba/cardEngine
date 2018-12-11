package com.andybaba.games;

import com.andybaba.games.card.Card;
import com.andybaba.games.card.Hand;

public class Main {
	public static void main(String[] argsss) {
		Hand hand = new Hand(5, Hand.Duplicates.Yes);
		Card card1 = new Card();
		Card card2 = new Card();
		hand.addCard(card2);
		hand.addCard(card2);
		hand.addCard(card2);
		hand.addCard(card2);
		hand.addCard(card2);

		System.out.println(hand);
		System.out.print(hand.hasDuplicates());
	}
}
