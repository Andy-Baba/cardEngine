package com.andybaba.games.card;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BaseHandTest {

	public final int DEFAULT_HAND_SIZE = 10;
	public final Card MY_CARD = new Card(Card.Rank.Five, Card.Suite.Club); // set my card as 5 of Club
	public final Level HAND_TEST_LOG_LEVEL = Level.ALL;
	public final boolean SHOW_HANDS = true;
	private static final Logger logger = Logger.getLogger("HandTest.logger");;
	private static int testCounter = 0;
	public final Duration TIME_OUT = Duration.ofMillis(200L);

	private Random random = new Random();

	@BeforeAll
	public static void beforeAllTests() {
		BaseHandTest test = new BaseHandTest();

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
	@DisplayName("1.0 Adding Card to a hand that accepts dublicate cards")
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
	@DisplayName("1.1 Adding a duplicated card to a hand when its not allowed")
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
	@DisplayName("1.2 Adding a duplicated card to a hand when its not allowed Manualy!")
	public void testAddCardDuplicatedManually() {
		logger.info("Starting test number " + ++testCounter);
		BaseHand hand = new BaseHand(this.DEFAULT_HAND_SIZE); // It should by default not allow duplicated cards

		hand.add(new Card(Card.Rank.Ace, Card.Suite.Spade));
		hand.add(new Card(Card.Rank.Ace, Card.Suite.Club));
		hand.add(new Card(Card.Rank.Ace, Card.Suite.Spade));
		hand.add(this.MY_CARD);
		hand.add(new Card(Card.Rank.Trey, Card.Suite.Diamond));

		assertThrows(ArrayStoreException.class, () -> {
			hand.add(this.MY_CARD);
		});
		logger.info("Expected to throw: " + ArrayStoreException.class.getName());
		if (this.SHOW_HANDS)
			logger.info("Generated hand in this test:\n" + hand);
	}

	@Test
	@DisplayName("1.3 Adding Card to a hand passing the max card value")
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
	@DisplayName("2.0 Roandomize with a number 0 or negative")
	public void testRandomizeIntNonePositive() {
		logger.info("Starting test number " + ++testCounter);
		BaseHand hand = new BaseHand(DEFAULT_HAND_SIZE);
		int testValue = -1 * this.random.nextInt(10000);
		assertThrows(IllegalArgumentException.class, () -> {
			hand.randomize(testValue);
		});
		logger.info("Done with a value: " + testValue);
	}

	@Test
	@DisplayName("2.1 Roandomize with a number bigger than remaining number of slots in the hand")
	void testRandomizeIntOutOfCapacity() {
		logger.info("Starting test number " + ++testCounter);
		BaseHand hand = new BaseHand(this.DEFAULT_HAND_SIZE);
		hand.randomize(this.DEFAULT_HAND_SIZE / 2);
		assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
			hand.randomize(this.DEFAULT_HAND_SIZE / 2 + 1);
		});
		logger.info(hand.hasDuplicates() + ";" + hand.cardsCount());
	}

	@Test
	@DisplayName("2.2 Can it randomize the hand in a given time")
	void testRandomizeOnTime() throws InterruptedException {
		logger.info("Starting test number " + ++testCounter);
		BaseHand hand = new BaseHand(100, BaseHand.Duplicates.Yes);
		assertTimeout(TIME_OUT, () -> {
			hand.randomize();
		});
		logger.info(hand.hasDuplicates() + ";" + hand.cardsCount());
		if (this.SHOW_HANDS)
			logger.info("Generated hand in this test:\n" + hand);
	}

	@Test
	@DisplayName("2.3 Randomizing an already full hand")
	void testRandomizeFullHand() {
		logger.info("Starting test number " + ++testCounter);
		BaseHand hand = new BaseHand(this.DEFAULT_HAND_SIZE);
		hand.randomize();
		assertThrows(UnsupportedOperationException.class, () -> {
			hand.randomize();
		});
	}

	@Test
	@DisplayName("2.4 Randomize without duplication")
	void testRandomizeNoDuplication() throws InterruptedException {
		logger.info("Starting test number " + ++testCounter);
		BaseHand hand = new BaseHand(100, BaseHand.Duplicates.No);
		assertThrows(ArrayStoreException.class, () -> {
			hand.randomize();
		});
		logger.info(hand.hasDuplicates() + ";" + hand.cardsCount());
		if (this.SHOW_HANDS)
			logger.info("Generated hand in this test:\n" + hand);
	}

	@Test
	@DisplayName("3.0 Constructor with accpting duplicate and not accepting")
	void testHandIntDuplicates() {
		logger.info("Starting test number " + ++testCounter);
		int testValue = this.random.nextInt(10000) + 1; // randomize with a positive none zero number
		new BaseHand(testValue, BaseHand.Duplicates.No);
		logger.info("Done with value of: " + testValue);
	}

	@Test
	@DisplayName(value = "3.1 Constructor with negative value")
	void testHandNegativeOrZero() {
		logger.info("Starting test number " + ++testCounter);
		int testValue = -1 * random.nextInt(10000); // can be zero or a negative value
		assertThrows(IllegalArgumentException.class, () -> {
			new BaseHand(testValue);
		});
		logger.info("Done with value: " + testValue);
	}

	@Test
	@DisplayName("4.0 Show the card at a given index")
	void testShow() {
		logger.info("Starting test number " + ++testCounter);
		BaseHand hand = new BaseHand(this.DEFAULT_HAND_SIZE);
		int testValue = random.nextInt(this.DEFAULT_HAND_SIZE / 2) + 2;
		hand.randomize(testValue);
		Card actual = new Card();
		hand.add(actual);
		assertEquals(hand.show(testValue), actual);
		logger.info("The card at postion " + testValue + " is: " + actual);
		if (this.SHOW_HANDS)
			logger.info("Generated hand in this test:\n" + hand);
	}

	@Test
	@DisplayName("5.0 Showing the current count of cards")
	void testCount() {
		logger.info("Starting test number " + ++testCounter);
		int handSize = this.random.nextInt(this.DEFAULT_HAND_SIZE) + this.DEFAULT_HAND_SIZE;
		BaseHand hand = new BaseHand(handSize);
		int expected = this.random.nextInt(this.DEFAULT_HAND_SIZE) + 2;
		hand.randomize(expected);
		logger.info("Hand size is " + handSize + ", fill it with " + expected + " random cards");
		assertEquals(expected, hand.cardsCount());
		logger.info("Asserting, expected: " + expected + " actual:" + hand.cardsCount());
	}

	@Test
	@DisplayName("6.0 Getting maximum number of cards the hand can hold")
	void testMaxSize() {
		logger.info("Starting test number " + ++testCounter);
		BaseHand hand = new BaseHand(this.DEFAULT_HAND_SIZE);
		assertEquals(this.DEFAULT_HAND_SIZE, hand.maxSize());
	}

	@Test
	@DisplayName("7.0 Finding a given Card in the Hand")
	void testContains() {
		logger.info("Starting test number " + ++testCounter);
		BaseHand hand = new BaseHand(this.DEFAULT_HAND_SIZE, BaseHand.Duplicates.Yes);
		int testValue = random.nextInt(this.DEFAULT_HAND_SIZE / 2) + 2;
		hand.randomize(testValue);
		hand.add(this.MY_CARD);
		hand.randomize();
		assertTrue(hand.contains(this.MY_CARD));
		logger.info("Found the card: " + this.MY_CARD);
		if (this.SHOW_HANDS)
			logger.info("Generated hand in this test:\n" + hand);
	}

	@Test
	@DisplayName("8.0 Making sure if its returning string as expected")
	void testToString() {
		logger.info("Starting test number " + ++testCounter);
		BaseHand hand = new BaseHand(2, Hand.Duplicates.Yes);
		Card card1 = new Card();
		Card card2 = new Card();
		BaseHand.DELIMIER = ";"; // Change the default delimiter to ;
		hand.add(card1);
		hand.add(card2);

		String expected = card1 + BaseHand.DELIMIER + card2 + BaseHand.DELIMIER;
		assertEquals(expected, hand.toString());
		BaseHand.DELIMIER = System.lineSeparator(); // Change the default delimiter back to new line
	}

	@Test
	void testHasDuplicates() {
		fail("Not yet implemented");
	}

	@Test
	void testIterator() {
		fail("Not yet implemented");
	}

	@Test
	void testRemove() {
		fail("Not yet implemented");
	}

}
