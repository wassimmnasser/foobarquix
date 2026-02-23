package com.foobarquix.batch;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import org.springframework.batch.infrastructure.item.Chunk;
import org.springframework.batch.infrastructure.item.ItemWriter;

/**
 * author : Wassim MNASSER
 */
public class ResultLineWriter implements ItemWriter<String> {

	private final Path output;

	public ResultLineWriter(Path output) {
		this.output = output;
		try {
			Files.deleteIfExists(output);
			Files.createDirectories(output.getParent() == null ? Path.of(".") : output.getParent());
			Files.createFile(output);
		} catch (IOException e) {
			throw new IllegalStateException("Cannot prepare output file", e);
		}
	}

	@Override
	public void write(Chunk<? extends String> items) throws Exception {
		try (BufferedWriter writer = Files.newBufferedWriter(output, StandardOpenOption.APPEND)) {
			for (String line : items) {
				writer.write(line);
				writer.newLine();
			}
		}
	}
}
