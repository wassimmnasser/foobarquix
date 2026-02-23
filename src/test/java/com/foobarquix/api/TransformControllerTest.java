package com.foobarquix.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class TransformControllerTest {

	private MockMvc mockMvc;

	@Mock
	private TransformUseCase useCase;

	@InjectMocks
	private TransformController controller;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
    void shouldReturn200AndBodyForValidValue() throws Exception {
        when(useCase.transform(15)).thenReturn("FOOBARBAR");

        mockMvc.perform(get("/v1/transform")
                        .param("value", "15"))
                .andExpect(status().isOk())
                .andExpect(content().string("FOOBARBAR"));

        verify(useCase).transform(15);
        verifyNoMoreInteractions(useCase);
    }

	@Test
	void shouldReturn400WhenValueMissing() throws Exception {
		mockMvc.perform(get("/v1/transform")).andExpect(status().isBadRequest());

		verifyNoInteractions(useCase);
	}

	@Test
	void shouldReturn400WhenValueNotANumber() throws Exception {
		mockMvc.perform(get("/v1/transform").param("value", "abc")).andExpect(status().isBadRequest());

		verifyNoInteractions(useCase);
	}

	@Test
	void shouldReturn400WhenValueOutOfRangeLow() throws Exception {
		mockMvc.perform(get("/v1/transform").param("value", "-1")).andExpect(status().isBadRequest());

		verifyNoInteractions(useCase);
	}

	@Test
	void shouldReturn400WhenValueOutOfRangeHigh() throws Exception {
		mockMvc.perform(get("/v1/transform").param("value", "101")).andExpect(status().isBadRequest());

		verifyNoInteractions(useCase);
	}
}