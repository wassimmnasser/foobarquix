package com.foobarquix.batch;



import org.springframework.batch.core.repository.persistence.ExecutionContext;
import org.springframework.batch.infrastructure.item.ItemStreamReader;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/**
author : Wassim MNASSER
*/
public class FileNumberLineReader implements ItemStreamReader<Integer> {

    private final Resource input;
    private BufferedReader reader;

    public FileNumberLineReader(Resource input) {
        this.input = input;
    }

    public void open(ExecutionContext executionContext) {
        try {
            this.reader = new BufferedReader(new InputStreamReader(input.getInputStream()));
        } catch (IOException e) {
            throw new IllegalStateException("Cannot open input file", e);
        }
    }

    @Override
    public Integer read() throws Exception {
        String line = reader.readLine();
        if (line == null) return null;

        line = line.trim();
        if (line.isEmpty()) {
            throw new IllegalArgumentException("EMPTY_LINE");
        }

        try {
            int value = Integer.parseInt(line);
            if (value < 0 || value > 100) {
                throw new IllegalArgumentException("OUT_OF_RANGE");
            }
            return value;
        } catch (NumberFormatException e) {
            throw new NumberFormatException("NOT_A_NUMBER");
        }
    }

    @Override
    public void close() {
        try {
            if (reader != null) reader.close();
        } catch (IOException e) {
            throw new IllegalStateException("Cannot close reader", e);
        }
    }
}
