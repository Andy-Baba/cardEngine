package net.andybaba.games.card;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import net.andybaba.TestClass;
import net.andybaba.games.card.BaseHand;
import net.andybaba.games.card.Card;
import net.andybaba.games.card.Deck;
import net.andybaba.games.card.Hand;
import net.andybaba.games.card.Card.Rank;
import net.andybaba.games.card.Card.Suite;
import net.andybaba.games.card.Hand.Duplicates;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaseHandTest extends TestClass {

	public final int DEFAULT_HAND_SIZE = 10;
	public final Card MY_CARD = new Card(Card.Rank.Five, Card.Suite.Club); // set my card as 5 of Club

	public final boolean SHOW_HANDS = false;
	private BaseHand hand = new BaseHand(1);
	private boolean showHands = SHOW_HANDS;

	@BeforeAll
	@Override
	public void beforeAllTests() {
		logger.setLevel(TEST_LOG_LEVEL);
		logger.info("Starting the Test of: " + Deck.class.getName());
		BaseHandTest test = new BaseHandTest();
		logger.info("Test Configuration:\n\t\tDEFAULT HAND SIZE=" + test.DEFAULT_HAND_SIZE + "\n\t\tMY_CARD="
				+ test.MY_CARD + "\n\t\tSHOW HANDS=" + test.SHOW_HANDS);
	}

	@AfterAll
	public void afterAllTests() {
		logger.info("End of test, total of " + testCounter + " were done for class: " + BaseHand.class.getName());
	}

	@BeforeEach
	void beforeEachTest(final TestInfo testInfo) {
		logger.info("++++++++++Test " + ++testCounter + ": " + testInfo.getDisplayName());
		this.afterTestString = "Done";
		this.showHands = this.SHOW_HANDS;
	}

	@AfterEach
	void afterEachTest(final TestInfo testInfo) {
		logger.info(this.afterTestString);
		BaseHand.SEPARATOR = this.SEPARATOR;
		String tempString = "----------Test " + testInfo.getTestMethod() + "\n\tThe hand has ";
		if (this.showHands) {
			if (this.hand.isEmpty())
				tempString += "EMPTY";
			else
				tempString += hand.count() + " cards out of " + hand.maxSize() + this.SEPARATOR + hand;
		}
		logger.info(tempString);
	}

	@Test
	@DisplayName("1.0 Adding Card to a hand that accepts dublicate cards")
	void testAddtoAccpetingduplicates() {
		hand = new BaseHand(this.DEFAULT_HAND_SIZE, BaseHand.Duplicates.Yes);
		Card card = new Card();
		for (int i = 0; i < this.DEFAULT_HAND_SIZE * 2 / 3; i++)
			hand.add(card);
	}

	@Test
	@DisplayName("1.1 Adding a duplicated card to a hand when its not allowed")
	void testAddDuplicated() {
		hand = new BaseHand(this.DEFAULT_HAND_SIZE, BaseHand.Duplicates.No);
		hand.randomize(this.DEFAULT_HAND_SIZE * 2 / 3);
		Card card = hand.show(hand.count() / 2);
		assertThrows(ArrayStoreException.class, () -> {
			hand.add(card);
		});
		this.afterTestString = "Expected to throw: " + ArrayStoreException.class.getName();
	}

	@Test
	@DisplayName("1.2 Adding a duplicated card to a hand when its not allowed Manualy!")
	void testAddDuplicatedManually() {
		hand = new BaseHand(this.DEFAULT_HAND_SIZE); // It should by default not allow duplicated cards

		hand.add(new Card(Card.Rank.Ace, Card.Suite.Spade));
		hand.add(new Card(Card.Rank.Ace, Card.Suite.Club));
		hand.add(new Card(Card.Rank.Ace, Card.Suite.Heart));
		hand.add(this.MY_CARD);
		hand.add(new Card(Card.Rank.Trey, Card.Suite.Diamond));

		assertThrows(ArrayStoreException.class, () -> {
			hand.add(this.MY_CARD);
		});
		this.afterTestString = "Expected to throw: " + ArrayStoreException.class.getName();
	}

	@Test
	@DisplayName("1.3 Adding Card to a hand passing the max card value")
	void testAddPassingTheMaxCardValue() {
		hand = new BaseHand(this.DEFAULT_HAND_SIZE, BaseHand.Duplicates.Yes);
		hand.randomize();
		assertThrows(IndexOutOfBoundsException.class, () -> {
			hand.add(new Card());
		});
		this.afterTestString = "Expected to throw: " + IndexOutOfBoundsException.class.getName();
	}

	@Test
	@DisplayName("1.4 Adding a card to the end of the hand")
	void testAddToPosition() {
		hand = new BaseHand(this.DEFAULT_HAND_SIZE);
		Card card1 = new Card(Card.Rank.Five, Card.Suite.Heart);
		Card card2 = new Card(Card.Rank.Jack, Card.Suite.Diamond);
		Card card3 = new Card(Card.Rank.Queen, Card.Suite.Heart);

		hand.add(card1);
		hand.add(card2);
		hand.add(1, card3);

		String expected = card3 + this.SEPARATOR + card1 + this.SEPARATOR + card2 + this.SEPARATOR;
		assertEquals(expected, hand.toString());
	}

	@Test
	@DisplayName("1.5 Adding a card to a position larger than the current size of the hand")
	void testAddToPositionException() {
		hand = new BaseHand(this.DEFAULT_HAND_SIZE);
		hand.randomize(5);

		assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
			hand.add(7, new Card());
			;
		});
	}

	@Test
	@DisplayName("2.0 Roandomize with a number 0 or negative")
	void testRandomizeIntNonePositive() {
		hand = new BaseHand(DEFAULT_HAND_SIZE);
		int testValue = -1 * this.random.nextInt(10000);
		assertThrows(IllegalArgumentException.class, () -> {
			hand.randomize(testValue);
		});
		logger.info("Done with a value: " + testValue);
	}

	@Test
	@DisplayName("2.1 Roandomize with a number bigger than remaining number of slots in the hand")
	void testRandomizeIntOutOfCapacity() {
		hand = new BaseHand(this.DEFAULT_HAND_SIZE);
		hand.randomize(this.DEFAULT_HAND_SIZE / 2);
		assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
			hand.randomize(this.DEFAULT_HAND_SIZE / 2 + 1);
		});
		this.afterTestString = hand.hasDuplicates() + ";" + hand.count();
	}

	@Test
	@DisplayName("2.2 Can it randomize the hand in a given time")
	void testRandomizeOnTime() throws InterruptedException {
		hand = new BaseHand(1000, BaseHand.Duplicates.Yes);
		assertTimeout(TIME_OUT, () -> {
			hand.randomize();
		});
		this.showHands = false;
	}

	@Test
	@DisplayName("2.3 Randomizing an already full hand")
	void testRandomizeFullHand() {
		hand = new BaseHand(this.DEFAULT_HAND_SIZE);
		hand.randomize();
		assertThrows(UnsupportedOperationException.class, () -> {
			hand.randomize();
		});
	}

	@Test
	@DisplayName("2.4 Randomize without duplication")
	void testRandomizeNoDuplication(TestInfo testInfo) throws InterruptedException {
		fail("When 53 cards, duplicated not allowd, goes infinite loop");
		hand = new BaseHand(53);
		assertThrows(ArrayStoreException.class, () -> {
			hand.randomize();
		});
		this.afterTestString = hand.hasDuplicates() + ";" + hand.count();
	}

	@Test
	@DisplayName("3.0 Constructor with accpting duplicate and not accepting")
	void testHandIntDuplicates() {
		logger.info("Starting test number " + ++testCounter);
		int testValue = this.random.nextInt(10000) + 1; // randomize with a positive none zero number
		new BaseHand(testValue, BaseHand.Duplicates.No);
		logger.info("Done with value of: " + testValue);
	}

	@ParameterizedTest
	@DisplayName("3.1 Constructor with negative value or zero")
	@ValueSource(ints = { -10, -23, 0, -14213 })
	void testHandNegativeOrZero(final int testValue) {
		assertThrows(IllegalArgumentException.class, () -> {
			hand = new BaseHand(testValue);
		});
		this.afterTestString = "Done with value: " + testValue;
	}

	@Test
	@DisplayName("4.0 Show the card at a given index")
	void testShow() {
		hand = new BaseHand(this.DEFAULT_HAND_SIZE, Duplicates.Yes);
		int testValue = random.nextInt(this.DEFAULT_HAND_SIZE / 2) + 2;
		hand.randomize(testValue);
		Card actual = new Card();
		hand.add(actual);
		assertEquals(hand.show(testValue + 1), actual);
		this.afterTestString = "The card at postion " + testValue + " is: " + actual;
	}

	@Test
	@DisplayName("5.0 Showing the current count of cards")
	void testCount() {
		int handSize = this.random.nextInt(this.DEFAULT_HAND_SIZE) + this.DEFAULT_HAND_SIZE;
		hand = new BaseHand(handSize);
		int expected = this.random.nextInt(this.DEFAULT_HAND_SIZE / 2) + 2;
		hand.randomize(expected);
		logger.info("Hand size is " + handSize + ", fill it with " + expected + " random cards");
		assertEquals(expected, hand.count());
		this.afterTestString = "Asserting, expected: " + expected + " actual:" + hand.count();
	}

	@Test
	@DisplayName("6.0 Getting maximum number of cards the hand can hold")
	void testMaxSize() {
		hand = new BaseHand(this.DEFAULT_HAND_SIZE);
		assertEquals(this.DEFAULT_HAND_SIZE, hand.maxSize());
	}

	@Test
	@DisplayName("7.0 Finding a given Card in the Hand")
	void testContains() {
		hand = new BaseHand(this.DEFAULT_HAND_SIZE, BaseHand.Duplicates.Yes);
		int testValue = random.nextInt(this.DEFAULT_HAND_SIZE / 2) + 2;
		hand.randomize(testValue);
		hand.add(this.MY_CARD);
		hand.randomize();
		assertTrue(hand.contains(this.MY_CARD));
		afterTestString = "Found the card: " + this.MY_CARD;
	}

	@Test
	@DisplayName("7.1 Finding a given Card in the Hand where it deos not exist")
	void testNotContains() {
		hand = new BaseHand(this.DEFAULT_HAND_SIZE, BaseHand.Duplicates.Yes);
		hand.add(new Card(Card.Rank.Ace, Card.Suite.Spade));
		hand.add(new Card(Card.Rank.Ace, Card.Suite.Club));
		hand.add(new Card(Card.Rank.Ace, Card.Suite.Spade));
		assertFalse(hand.contains(this.MY_CARD));
		afterTestString = "Card was not found: " + this.MY_CARD;
	}

	@Test
	@DisplayName("8.0 Making sure if its returning string as expected")
	void testToString() {
		hand = new BaseHand(2, Hand.Duplicates.Yes);
		Card card1 = new Card();
		Card card2 = new Card();
		BaseHand.SEPARATOR = ";"; // Change the default delimiter to ;
		hand.add(card1);
		hand.add(card2);

		String expected = card1 + BaseHand.SEPARATOR + card2 + BaseHand.SEPARATOR;
		assertEquals(expected, hand.toString());
		BaseHand.SEPARATOR = System.lineSeparator(); // Change the default delimiter back to new line
	}

	@Test
	@DisplayName("9.0 Removing a given card from the hand.")
	void testRemove() {

		hand = new BaseHand(this.DEFAULT_HAND_SIZE); // It should by default not allow duplicated cards

		hand.add(new Card(Card.Rank.Ace, Card.Suite.Spade));
		hand.add(new Card(Card.Rank.Ace, Card.Suite.Club));
		hand.add(new Card(Card.Rank.Ace, Card.Suite.Heart));
		hand.add(this.MY_CARD);
		hand.add(new Card(Card.Rank.Trey, Card.Suite.Diamond));

		assertEquals(this.MY_CARD, hand.remove(4));
		this.afterTestString = "Expected to throw: " + ArrayStoreException.class.getName();
	}

	@Test
	@DisplayName("10.0 Checks hand is empty")
	void testIsEmpty() {
		this.hand = new BaseHand(1);
		assertEquals(true, hand.isEmpty());
	}

	@Test
	@DisplayName("10.1 Checks hand is full")
	void testIsFull() {
		this.hand = new BaseHand(this.DEFAULT_HAND_SIZE);
		hand.randomize();
		assertEquals(true, hand.isFull());
	}

	@ParameterizedTest
	@DisplayName("11.0 Check the empty slots")
	@ValueSource(ints = { 0, 3, 10 })
	void testEmptySlots(final int testValue) {
		this.hand = new BaseHand(this.DEFAULT_HAND_SIZE);
		if (testValue > 0)
			hand.randomize(testValue);
		assertEquals(hand.remainingSlots(), this.DEFAULT_HAND_SIZE - testValue);
	}

	@Test
	@DisplayName("12.0 Counting the number of duplicates in the hand")
	void testHasDuplicates() {
		hand = new BaseHand(this.DEFAULT_HAND_SIZE, Duplicates.Yes); // It should by default not allow duplicated cards

		hand.add(new Card(Card.Rank.Ace, Card.Suite.Spade)); // Duplicates are 0
		hand.add(new Card(Card.Rank.Ace, Card.Suite.Club)); // Duplicates are 0
		hand.add(new Card(Card.Rank.Ace, Card.Suite.Spade)); // Duplicates are 1
		hand.add(this.MY_CARD); // Duplicates are 1
		hand.add(new Card(Card.Rank.Trey, Card.Suite.Diamond)); // Duplicates are 1
		hand.add(new Card(Card.Rank.Ace, Card.Suite.Spade)); // Duplicates are 2
		hand.add(this.MY_CARD); // Duplicates are 3
		hand.add(new Card(Card.Rank.Ace, Card.Suite.Club)); // Duplicates are 4
		assertEquals(4, hand.hasDuplicates());
	}

	@Test
	@DisplayName("13.0 Iterate through the hand")
	void testIterator() {
		hand = new BaseHand(this.DEFAULT_HAND_SIZE);
		hand.randomize(this.DEFAULT_HAND_SIZE / 2);
		String expected = "";
		for (Card card : hand)
			expected += card + BaseHand.SEPARATOR;
		assertEquals(expected, hand.toString());
	}

	@Test
	@DisplayName("14.0 Comparing two different sized hands")
	void testEquals() {
		hand = new BaseHand(this.DEFAULT_HAND_SIZE);
		hand.add(new Card(Rank.Seven, Suite.Diamond));
		hand.add(new Card(Rank.King, Suite.Heart));

		BaseHand hand2 = new BaseHand(this.DEFAULT_HAND_SIZE);
		hand2.add(new Card(Rank.Seven, Suite.Diamond));
		hand2.add(new Card(Rank.King, Suite.Heart));
		hand2.add(new Card(Rank.Deuce, Suite.Club));

		assertThrows(IndexOutOfBoundsException.class, () -> {
			hand.equals(hand2);
		});
	}

	@Test
	@DisplayName("15.0 Sort the hand")
	void testSort() {
		hand = new BaseHand(this.DEFAULT_HAND_SIZE);
		hand.add(new Card(Rank.Seven, Suite.Diamond));
		hand.add(new Card(Rank.King, Suite.Heart));
		hand.add(new Card(Rank.Seven, Suite.Spade));
		hand.add(new Card(Rank.Deuce, Suite.Club));
		hand.add(new Card(Rank.Seven, Suite.Club));
		hand.sort();

		BaseHand expected = new BaseHand(this.DEFAULT_HAND_SIZE);
		expected.add(new Card(Rank.Deuce, Suite.Club));
		expected.add(new Card(Rank.Seven, Suite.Club));
		expected.add(new Card(Rank.Seven, Suite.Diamond));
		expected.add(new Card(Rank.King, Suite.Heart));
		expected.add(new Card(Rank.Seven, Suite.Spade));
		assertEquals(expected, hand);
	}

}
