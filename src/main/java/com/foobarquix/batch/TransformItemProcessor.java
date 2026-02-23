package com.foobarquix.batch;

import com.foobarquix.core.NumberTransformationEngine;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.batch.infrastructure.item.ItemProcessor;

/**
 * author : Wassim MNASSER
 */
public class TransformItemProcessor implements ItemProcessor<Integer, String> {

	private final NumberTransformationEngine engine;
	private final Map<Integer, String> cache = new ConcurrentHashMap<>();

	public TransformItemProcessor(NumberTransformationEngine engine) {
		this.engine = engine;
	}

	@Override
	public String process(Integer item) {
		return cache.computeIfAbsent(item, engine::transform);
	}
}