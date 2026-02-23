package com.foobarquix.api;

import org.springframework.http.HttpStatus;
/**
author : Wassim MNASSER
*/

import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/v1")
public class TransformController {

	private final TransformUseCase useCase;

	public TransformController(TransformUseCase useCase) {
		this.useCase = useCase;
	}

	@GetMapping("/v1/transform")
	public String transform(@RequestParam("value") int value) {
		if (value < 0 || value > 100) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Value out of range");
		}
		return useCase.transform(value);
	}
}
