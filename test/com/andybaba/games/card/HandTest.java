package com.andybaba.games.card;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.time.Duration;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.andybaba.games.card.Card;
import com.andybaba.games.card.BaseHand;

import jdk.jfr.Name;

final class HandTest {

	public final int DEFAULT_HAND_SIZE = 10;
	public final Card MY_CARD = new Card(Card.Rank.Five, Card.Suite.Club); // set my card as 5 of Club
	public final Level HAND_TEST_LOG_LEVEL = Level.ALL;
	public final boolean SHOW_HANDS = true;
	private static final Logger logger = Logger.getLogger("HandTest.logger");;
	private static int testCounter = 0;
	public final Duration TIME_OUT = Duration.ofMillis(200L);

	@BeforeAll
	public static void beforeAllTests() {
		HandTest test = new HandTest();

		logger.info("Starting the Test of: " + BaseHand.class.getName());
		logger.setLevel(test.HAND_TEST_LOG_LEVEL);
		logger.info("Setting up the test with following configuration:" + "\n\tDEFAULT_HAND_SIZE: "
				+ test.DEFAULT_HAND_SIZE + "\n\tMY_CARD: " + test.MY_CARD + "\n\tLOG.LEVEL: " + test.HAND_TEST_LOG_LEVEL
				+ "\n\tGLOBAL_TIME_OUT: " + test.TIME_OUT.toMillis() + "\n\tSHOW_HANDS: " + test.SHOW_HANDS);
	}

	@AfterAll
	public static void afterAllTests() {
		logger.info("End of test, total of " + testCounter + " were done for class: " + BaseHand.class.getName());
	}

	@Test
	@DisplayName("2.0 Adding Card to a hand that accepts dublicate cards")
	public void testAddCardtoAccpetingduplicates() {
		logger.info("Starting test number " + ++testCounter);
		BaseHand hand = new BaseHand(this.DEFAULT_HAND_SIZE, BaseHand.Duplicates.Yes);
		Card card = new Card();
		for (int i = 0; i < this.DEFAULT_HAND_SIZE; i++)
			hand.add(card);
		logger.info(this.DEFAULT_HAND_SIZE + ", " + card + " is added to the hand");
		if (this.SHOW_HANDS)
			logger.info("Generated hand in this test:\n" + hand);
	}

	@Test
	@DisplayName("2.1 Adding a duplicated card to a hand when its not allowed")
	public void testAddCardDuplicated() {
		logger.info("Starting test number " + ++testCounter);
		BaseHand hand = new BaseHand(this.DEFAULT_HAND_SIZE, BaseHand.Duplicates.No);
		hand.randomize(this.DEFAULT_HAND_SIZE / 2 - 1);
		hand.add(MY_CARD);
		hand.randomize(this.DEFAULT_HAND_SIZE / 2 - 1);
		assertThrows(ArrayStoreException.class, () -> {
			hand.add(MY_CARD);
		});
		logger.info("Expected to throw: " + ArrayStoreException.class.getName());
		if (this.SHOW_HANDS)
			logger.info("Generated hand in this test:\n" + hand);
	}

	@Test
	@DisplayName("2.2 Adding a duplicated card to a hand when its not allowed Manualy!")
	public void testAddCardDuplicatedManually() {
		logger.info("Starting test number " + ++testCounter);
		BaseHand hand = new BaseHand(this.DEFAULT_HAND_SIZE); // It should by default not allow duplicated cards
		hand.add(MY_CARD);
		hand.add(new Card(Card.Rank.Ace, Card.Suite.Club));
		// hand.add(;

		assertThrows(ArrayStoreException.class, () -> {
			
		});
		logger.info("Expected to throw: " + ArrayStoreException.class.getName());
		if (this.SHOW_HANDS)
			logger.info("Generated hand in this test:\n" + hand);
	}

	@Test
	@DisplayName("3. Adding Card to a hand passing the max card value")
	void testAddCardPassingTheMaxCardValue() {
		logger.info("Starting test number " + ++testCounter);
		BaseHand hand = new BaseHand(this.DEFAULT_HAND_SIZE, BaseHand.Duplicates.Yes);
		hand.randomize();
		assertThrows(IndexOutOfBoundsException.class, () -> {
			hand.add(new Card());
		});
		logger.info("Expected to throw: " + IndexOutOfBoundsException.class.getName());
	}

	@Test
	@DisplayName("4. Roandomize with a number 0 or negative")
	public void testRandomizeIntNonePositive() {
		logger.info("Starting test number " + ++testCounter);
		BaseHand hand = new BaseHand(DEFAULT_HAND_SIZE);
		assertThrows(IllegalArgumentException.class, () -> {
			hand.randomize(0);
		});
		logger.info(hand.hasDuplicates() + ";" + hand.cardsCount());
	}

	@Test
	@DisplayName("5. Roandomize with a number bigger than remaining number of slots in the hand")
	void testRandomizeInt() {
		logger.info("Starting test number " + ++testCounter);
		BaseHand hand = new BaseHand(this.DEFAULT_HAND_SIZE);
		hand.randomize(this.DEFAULT_HAND_SIZE / 2);
		assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
			hand.randomize(this.DEFAULT_HAND_SIZE / 2 + 1);
		});
		logger.info(hand.hasDuplicates() + ";" + hand.cardsCount());
	}

	@Test
	@DisplayName("6. Can it randomize the hand in a given time")
	void testRandomize() throws InterruptedException {
		logger.info("Starting test number " + ++testCounter);
		BaseHand hand = new BaseHand(53, BaseHand.Duplicates.No);
		assertTimeout(TIME_OUT, () -> {
			hand.randomize();
		});
		logger.info(hand.hasDuplicates() + ";" + hand.cardsCount());
		if (this.SHOW_HANDS)
			logger.info("Generated hand in this test:\n" + hand);
	}

	@Test
	@DisplayName("Constructor with accpting duplicate and not accepting")
	void testHandIntDuplicates() {
		logger.info("Starting test number " + ++testCounter);
		new BaseHand(3, BaseHand.Duplicates.Yes);
		new BaseHand(100, BaseHand.Duplicates.No);
		logger.info("Done");
	}

	@Test
	@Name(value = "Constructor with negative value")
	void testHandNegative() {
		logger.info("Starting test number " + ++testCounter);
		assertThrows(IllegalArgumentException.class, () -> {
			new BaseHand(-1);
		});
		logger.info("Expected to throw: " + IllegalArgumentException.class.getName());
	}

	@Test
	@Name(value = "Test Constroctur with zero value")
	void testHandZero() {
		logger.info("Starting test number " + ++testCounter);
		assertThrows(IllegalArgumentException.class, () -> {
			new BaseHand(0);
		});
		logger.info("Expected to throw: " + IllegalArgumentException.class.getName());
	}

	@Test
	@DisplayName("Deos it show the current card when asked?")
	void testShowCardAt() {
		logger.info("Starting test number " + ++testCounter);
		BaseHand hand = new BaseHand(this.DEFAULT_HAND_SIZE);
		hand.randomize();
		logger.info("Done");
		fail("recheck");
	}

	@Test
	@DisplayName("Deos it show the current count of cards?")
	void testCardsCount() {
		logger.info("Starting test number " + ++testCounter);
		Random random = new Random();
		int handSize = random.nextInt(10) + this.DEFAULT_HAND_SIZE;
		BaseHand hand = new BaseHand(handSize);
		int expected = random.nextInt(this.DEFAULT_HAND_SIZE) + 2;

		for (int i = 0; i < expected; i++) {
			hand.add(new Card());
		}
		logger.info("Hand size is " + handSize + ", fill it with " + expected + " random cards");
		assertEquals(expected, hand.cardsCount());
		logger.info("Asserting, expected: " + expected + " actual:" + hand.cardsCount());
	}

	@Test
	@DisplayName("Make sure can find a given Card in the Hand")
	void testContains() {
		int testSize = 10;
		BaseHand hand = new BaseHand(testSize, BaseHand.Duplicates.Yes);
		for (int i = 0; i < testSize; i++) {
			hand.add(new Card());
		}

		fail("Not yet implemented");
	}

	@Test
	@DisplayName("Making sure if its returning string as expected")
	void testToString() {
		Card card = new Card(Card.Rank.Jack, Card.Suite.Heart);
		assertEquals("Jack of Heart", card.toString());
	}

	@Test
	void testHasDuplicates() {
		fail("Not yet implemented");
	}

}
