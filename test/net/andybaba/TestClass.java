package net.andybaba;

import java.time.Duration;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * An abstract class including some common necessary data and methods for JUnit5
 * Test classes. If you would like you may extend it to your actual TestClasses
 * 
 * @version 1.0.1
 * @since Dec 16 2018
 * @author Andy
 *
 */
public abstract class TestClass {
	protected final Level TEST_LOG_LEVEL = Level.ALL;
	protected static final Logger logger = Logger.getLogger("Test.logger");
	protected static int testCounter = 0;
	protected String afterTestString;
	protected final String SEPARATOR;
	protected Random random;

	/**
	 * Keeping the global time out for each test
	 */
	protected final Duration TIME_OUT;

	public TestClass() {
		this(Duration.ofMillis(150L), "\n\t\t");
	}

	public TestClass(Duration timeOut, String logLineSeparator) {
		this.SEPARATOR = logLineSeparator;
		this.TIME_OUT = timeOut;
		random = new Random();
		TestClass.logger.info("Common settings are:\n\t\tLOG LINE SEPARATOR=" + this.SEPARATOR
				+ "\n\t\tGLOBAL TIMEOUT=" + this.TIME_OUT + "\n\t\tTEST LOG LEVEL=" + this.TEST_LOG_LEVEL);
	}

	public abstract void beforeAllTests();

	public abstract void afterAllTests();

}
