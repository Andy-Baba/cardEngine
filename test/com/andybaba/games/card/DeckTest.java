package com.andybaba.games.card;

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

import com.andybaba.TestClass;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DeckTest extends TestClass {
//class DeckTest extends BaseHandTest {

	public final boolean SHOW_HANDS = true;
	private boolean showHands;
	private Deck deck;

	@BeforeAll
	@Override
	public void beforeAllTests() {

	}

	@AfterAll
	@Override
	public void afterAllTests() {
		logger.info("End of test, total of " + testCounter + " were done for class: " + DeckTest.class.getName());

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
			if (this.deck == null)
				tempString += "NULL!!";
			else if (this.deck.isEmpty())
				tempString += "EMPTY";
			else
				tempString += deck.count() + " cards out of " + deck.maxSize() + this.SEPARATOR + deck;
		}
		logger.info(tempString);
	}

	@Test
	@DisplayName("1.0 Generating a standard deck")
	void testDeck() {
		deck = new Deck();

		String expected = "";
		for (Card.Suite suite : Card.Suite.values())
			for (Card.Rank rank : Card.Rank.values())
				expected += new Card(rank, suite) + BaseHand.SEPARATOR;

		assertEquals(expected, deck.toString());
	}

	@ParameterizedTest
	@DisplayName("1.1 Generating a mutli deck without exception")
	@ValueSource(ints = { 2, 5 })
	void testDeckInt(final int testValue) {
		showHands = false;
		deck = new Deck(testValue);

		String expected = "";
		for (int i = 0; i < testValue; i++)
			for (Card.Suite suite : Card.Suite.values())
				for (Card.Rank rank : Card.Rank.values())
					expected += new Card(rank, suite) + BaseHand.SEPARATOR;

		assertEquals(expected, deck.toString());
	}

	@ParameterizedTest
	@DisplayName("1.2 Generating a mutli deck with exception")
	@ValueSource(ints = { -2, 0, 11 })
	void testDeckIntExceptions(final int testValue) {
		assertThrows(IllegalArgumentException.class, () -> {
			deck = new Deck(testValue);
		});
	}

	@ParameterizedTest
	@DisplayName("1.2 Generating a mutli deck with exception")
	@ValueSource(ints = { 1, 5, 10 })
	void testHowMany(int testValue) {
		deck = new Deck(testValue);
		assertEquals(testValue, deck.howMany());
	}
}
