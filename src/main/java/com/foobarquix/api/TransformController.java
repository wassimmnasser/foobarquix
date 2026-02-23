package com.foobarquix.api;

/**
author : Wassim MNASSER
*/
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
public class TransformController {

	private final TransformUseCase useCase;

	public TransformController(TransformUseCase useCase) {
		this.useCase = useCase;
	}

	@GetMapping("/transform")
	public ResponseEntity<String> transform(@RequestParam("value") Integer value) {
		if (value == null) {
			throw new IllegalArgumentException("value is required");
		}
		if (value < 0 || value > 100) {
			throw new IllegalArgumentException("value must be between 0 and 100");
		}
		return ResponseEntity.ok(useCase.transform(value));
	}
}
