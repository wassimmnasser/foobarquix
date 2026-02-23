package com.foobarquix.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * author : Wassim MNASSER
 */
public class FoobarQuixEngineTest {

	private final NumberTransformationEngine engine = new FoobarQuixEngine();

	@Test
	void shouldReturnNumberWhenNoRuleMatches() {
		assertEquals("1", engine.transform(1));
		assertEquals("2", engine.transform(2));
		assertEquals("8", engine.transform(8));
	}

	@Test
	void shouldApplyDivisibleRulesWithPriority() {
		assertEquals("FOO", engine.transform(9));      
	    assertEquals("BAR", engine.transform(10));     
	    assertEquals("FOOBAR", engine.transform(60));
	}

	@Test
	void shouldApplyContainsRulesLeftToRight() {
		assertEquals("FOOFOO", engine.transform(3)); 
		assertEquals("BARBAR", engine.transform(5)); 
		assertEquals("QUIX", engine.transform(7)); 
		assertEquals("FOOBAR", engine.transform(51)); 
		assertEquals("BARFOO", engine.transform(53)); 
		assertEquals("FOOFOOFOO", engine.transform(33)); 
	}

	@Test
	void shouldMatchKataExamples() {
		assertEquals("1", engine.transform(1));
		assertEquals("FOOFOO", engine.transform(3));
		assertEquals("BARBAR", engine.transform(5));
		assertEquals("QUIX", engine.transform(7));
		assertEquals("FOO", engine.transform(9));
		assertEquals("FOOBAR", engine.transform(51));
		assertEquals("BARFOO", engine.transform(53));
		assertEquals("FOOFOOFOO", engine.transform(33));
		assertEquals("FOOBARBAR", engine.transform(15));
	}

}
