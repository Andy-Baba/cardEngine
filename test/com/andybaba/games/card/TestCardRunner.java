package com.andybaba.games.card;


import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;


public class TestCardRunner {
	
	public static void main(String[] args) {
		JUnitCore mycore = new JUnitCore();
		Result result = mycore.run(DeckTest.class);

		for (Failure failure : result.getFailures()) {
			System.out.println(failure.toString());
		}	

		System.out.println(result.wasSuccessful());
	}
}