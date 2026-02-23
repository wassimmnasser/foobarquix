package com.foobarquix.batch;

import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;

/**
 * author : Wassim MNASSER
 */
public class FoobarSkipPolicy implements SkipPolicy {
	@Override
	public boolean shouldSkip(Throwable t, long skipCount) throws SkipLimitExceededException {
		if (t instanceof NumberFormatException && "NOT_A_NUMBER".equals(t.getMessage()))
			return true;
		if (t instanceof IllegalArgumentException && "EMPTY_LINE".equals(t.getMessage()))
			return true;
		if (t instanceof IllegalArgumentException && "OUT_OF_RANGE".equals(t.getMessage()))
			return true;
		return false;
	}
}
