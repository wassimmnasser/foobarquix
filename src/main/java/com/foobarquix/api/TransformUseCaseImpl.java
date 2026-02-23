package com.foobarquix.api;

import org.springframework.stereotype.Service;

import com.foobarquix.core.NumberTransformationEngine;

/**
 * author : Wassim MNASSER
 */
@Service
public class TransformUseCaseImpl implements TransformUseCase {

	private final NumberTransformationEngine engine;

	public TransformUseCaseImpl(NumberTransformationEngine engine) {
		this.engine = engine;
	}

	@Override
	public String transform(int value) {
		return engine.transform(value);
	}

}
