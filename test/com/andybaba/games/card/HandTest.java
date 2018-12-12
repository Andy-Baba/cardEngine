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
import com.andybaba.games.card.Hand;

import jdk.jfr.Name;

final class HandTest {

	public static int DEFAULT_HAND_SIZE = 10;
	public static Card myCard = new Card(Card.Rank.Five, Card.Suite.Club); // set my card as 5 of Club
	public static final Level HAND_TEST_LOG_LEVEL = Level.ALL;
	public static final boolean SHOW_HANDS = true;
	private static Logger logger = Logger.getLogger("HandTest.logger");;
	private static int testCounter = 0;
	public static final Duration timeout = Duration.ofMillis(200L);

	@BeforeAll
	public static void beforeAllTests() {
		logger.info("Starting the Test of: " + Hand.class.getName());
		logger.setLevel(HAND_TEST_LOG_LEVEL);

		logger.info("Setting up the test with following configuration:" + "\n\tDEFAULT_HAND_SIZE: "
				+ HandTest.DEFAULT_HAND_SIZE + "\n\tmyCard: " + HandTest.myCard + "\n\tLOG.LEVEL: "
				+ HandTest.HAND_TEST_LOG_LEVEL);
	}

	@AfterAll
	public static void afterAllTests() {
		logger.info("End of test, total of " + testCounter + " were done for class: " + Hand.class.getName());
	}

	@Test
	@DisplayName("Adding Card to a hand that accepts dublicate cards")
	void testAddCardtoAccpetingduplicates() {
		logger.info("Starting test number " + ++testCounter);
		Hand hand = new Hand(HandTest.DEFAULT_HAND_SIZE, Hand.Duplicates.Yes);
		Card card = new Card();
		for(int i = 0;i<HandTest.DEFAULT_HAND_SIZE;i++)
			hand.addCard(card);
		logger.info(HandTest.DEFAULT_HAND_SIZE + ", " + card + " is added to the hand");
	}

	@Test
	@DisplayName("Constructor with accpting duplicate and not accepting")
	void testHandIntDuplicates() {
		logger.info("Starting test number " + ++testCounter);
		new Hand(3, Hand.Duplicates.Yes);
		new Hand(100, Hand.Duplicates.No);
		logger.info("Done");
	}

	@Test
	@Name(value = "Constructor with negative value")
	void testHandNegative() {
		logger.info("Starting test number " + ++testCounter);
		assertThrows(IllegalArgumentException.class, () -> {
			new Hand(-1);
		});
		logger.info("Expected to throw: " + IllegalArgumentException.class.getName());
	}

	@Test
	@Name(value = "Test Constroctur with zero value")
	void testHandZero() {
		logger.info("Starting test number " + ++testCounter);
		assertThrows(IllegalArgumentException.class, () -> {
			new Hand(0);
		});
		logger.info("Expected to throw: " + IllegalArgumentException.class.getName());
	}

	@Test
	@DisplayName("Adding Card to a hand passing the max card value")
	void testAddCardPassingTheMaxCardValue() {
		logger.info("Starting test number " + ++testCounter);
		Hand hand = new Hand(3, Hand.Duplicates.Yes);
		hand.randomize();
		assertThrows(IndexOutOfBoundsException.class, () -> {
			hand.addCard(new Card());
		});
		logger.info("Expected to throw: " + IndexOutOfBoundsException.class.getName());
	}

	@Test
	@DisplayName("Adding a duplicated card to a hand when its not allowed")
	void testAddCardDuplicated() {
		logger.info("Starting test number " + ++testCounter);
		Hand hand = new Hand(3, Hand.Duplicates.No);
		hand.addCard(myCard);
		hand.addCard(new Card());
		assertThrows(ArrayStoreException.class, () -> {
			hand.addCard(myCard);
		});

		logger.info("Expected to throw: " + ArrayStoreException.class.getName());

	}

	@Test
	@DisplayName("Can it randomize the hand in a given time")
	void testRandomize() throws InterruptedException {
		logger.info("Starting test number " + ++testCounter);
		Hand hand = new Hand(53, Hand.Duplicates.No);
		assertTimeout(timeout, () -> {
			hand.randomize();
		});

		for (Card card : hand) {
			System.out.println(card.getValue() + "");
		}
		logger.info(hand.hasDuplicates() + ";" + hand.cardsCount());
		fail("ridi");
	}

	@Test
	@DisplayName("Deos it show the current card when asked?")
	void testShowCardAt() {
		logger.info("Starting test number " + ++testCounter);
		Hand hand = new Hand(this.DEFAULT_HAND_SIZE);
		hand.randomize();
		logger.info("Done");

	}

	@Test
	@DisplayName("Deos it show the current count of cards?")
	void testCardsCount() {
		logger.info("Starting test number " + ++testCounter);
		Random random = new Random();
		int handSize = random.nextInt(10) + HandTest.DEFAULT_HAND_SIZE;
		Hand hand = new Hand(handSize);
		int expected = random.nextInt(HandTest.DEFAULT_HAND_SIZE) + 2;

		for (int i = 0; i < expected; i++) {
			hand.addCard(new Card());
		}
		logger.info("Hand size is " + handSize + ", fill it with " + expected + " random cards");
		assertEquals(expected, hand.cardsCount());
		logger.info("Asserting, expected: " + expected + " actual:" + hand.cardsCount());
	}

	@Test
	@DisplayName("Make sure can find a given Card in the Hand")
	void testContains() {
		int testSize = 10;
		Hand hand = new Hand(testSize, Hand.Duplicates.Yes);
		for (int i = 0; i < testSize; i++) {
			hand.addCard(new Card());
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
