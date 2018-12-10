package com.andybaba.games.cards;

public class Card {
	public enum Suite {
		Club(1), Diamond(2), Heart(3), Spade(2);

		public final int suite;

		private Suite(int suite) {
			this.suite = suite;
		}
	}
	public enum Rank{
		Ace(1), Duce(2),Trey(3),Four(4), Five(5),Six(6),Seven(7),Eight(8),Nine(9),Ten(10),
		Jack(11),Queen(12),King(13);
		
		public final int rank;
		private Rank(int rank) {
			this.rank = rank;
		}
		
	}

}
