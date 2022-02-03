package com.ivblanc.api.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ivblanc.api.dto.req.HistoryReqDTO;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class HistoryControllerTest {
	@Autowired
	MockMvc mockMvc;

	String TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sInVzZXJQayI6IjEiLCJpYXQiOjE2NDM4Njg5NTIsImV4cCI6MTY0NjQ2MDk1Mn0.mM0LIiTylyEChjIJgTPIs83uiB-p2Vp7JvqfrRvyROc";

	@Autowired
	private ObjectMapper objectMapper;

	@Order(1)
	@Test
	void addHistory() throws Exception {
		String content = objectMapper.writeValueAsString(new HistoryReqDTO(BigDecimal.valueOf(0.0), BigDecimal.valueOf(0.0), "2022-02-03", "맑음", -5, 5, "test text", "subject", 1,  null));
		mockMvc.perform(post("/api/history/add")
				.header("X-AUTH-TOKEN", TOKEN)
				.content(content)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(print());
	}

	@Order(2)
	@Test
	void findAllHistory() throws Exception {
		mockMvc.perform(get("/api/history/find/all")
				.header("X-AUTH-TOKEN", TOKEN)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(print());
	}
}