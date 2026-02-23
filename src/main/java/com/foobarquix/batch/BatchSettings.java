package com.foobarquix.batch;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * author : Wassim MNASSER
 */
@ConfigurationProperties(prefix = "batch")
public class BatchSettings {
	private String in;
	private String out;

	public String getIn() {
		return in;
	}

	public void setIn(String in) {
		this.in = in;
	}

	public String getOut() {
		return out;
	}

	public void setOut(String out) {
		this.out = out;
	}
}