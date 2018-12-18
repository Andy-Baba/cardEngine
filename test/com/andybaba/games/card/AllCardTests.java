package com.andybaba.games.card;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

//import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.jupiter.api.extension.ExecutionCondition;;



@RunWith(Suite.class)
@SelectClasses({ BaseHandTest.class, DeckTest.class })
public class AllCardTests {
	// TODO hello Junit TestSuite compatibility with Jupiter
	


}
