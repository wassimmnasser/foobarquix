package com.foobarquix.core;

/**
 * author : Wassim MNASSER
 */
public class FoobarQuixEngine implements NumberTransformationEngine {

	@Override
	public String transform(int value) {

		StringBuilder result = new StringBuilder();
		String valueStr = String.valueOf(value);

		if (value % 3 == 0) {
			result.append("FOO");
		}
		if (value % 5 == 0) {
			result.append("BAR");
		}

		for (char c : valueStr.toCharArray()) {
			if (c == '3') {
				result.append("FOO");
			} else if (c == '5') {
				result.append("BAR");
			} else if (c == '7') {
				result.append("QUIX");
			}
		}

		return result.isEmpty() ? valueStr : result.toString();
	}
}