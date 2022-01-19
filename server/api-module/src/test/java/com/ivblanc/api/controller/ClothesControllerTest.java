package com.ivblanc.api.controller;

import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ivblanc.api.dto.req.MakeClothesReqDTO;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class ClothesControllerTest {
	@Autowired
	MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Order(1)
	@Test
	void addClothes() throws Exception {
		String content = objectMapper.writeValueAsString(new MakeClothesReqDTO(1, "검정", "면", 100, 1, 1));
		mockMvc.perform(post("/api/clothes/add")
				.content(content)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().string("{\"output\":1,\"msg\":\"성공\",\"data\":{\"clothes_id\":1}}"))
			.andDo(print());

	}

	@Order(2)
	@Test
	void updateUrl() throws Exception {
		mockMvc.perform(put("/api/clothes/updateurl")
				.param("clothesId", "1")
				.param("url", "url"))
			.andExpect(status().isOk())
			.andExpect(content().string("{\"output\":1,\"msg\":\"성공\",\"data\":{\"clothes_id\":1}}"))
			.andDo(print());

	}

	@Order(3)
	@Test
	void findOrderByDate() throws Exception {
		mockMvc.perform(get("/api/clothes/createdate")
				.param("userId", "1"))
			.andExpect(status().isOk())
			.andDo(print());
	}

	@Order(4)
	@Test
	void deleteClothes() throws Exception {
		mockMvc.perform(delete("/api/clothes/deleteById")
				.param("clothesId", "1"))
			.andExpect(status().isOk())
			.andExpect(content().string("{\"output\":1,\"msg\":\"성공\",\"data\":{\"clothes_id\":1}}"))
			.andDo(print());
	}

	@Order(5)
	@Test
	void findOrderByLike() throws Exception {

		mockMvc.perform(get("/api/clothes/like")
				.param("userId", "1"))
			.andExpect(status().isOk())
			.andExpect(content().string("{\"output\":1,\"msg\":\"성공\",\"data\":[]}"))
			.andDo(print());
	}

	@Order(6)
	@Test
	void findClothesSeason() throws Exception {
		String content = objectMapper.writeValueAsString(new MakeClothesReqDTO(1, "검정", "면", 100, 1, 1));
		mockMvc.perform(post("/api/clothes/add")
				.content(content)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andDo(print());
		content = objectMapper.writeValueAsString(new MakeClothesReqDTO(1, "검정", "면", 100, 2, 1));
		mockMvc.perform(post("/api/clothes/add")
				.content(content)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andDo(print());
		content = objectMapper.writeValueAsString(new MakeClothesReqDTO(1, "검정", "면", 100, 1, 1));
		mockMvc.perform(post("/api/clothes/add")
				.content(content)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andDo(print());

		mockMvc.perform(get("/api/clothes/season")
				.param("season", "1")
				.param("userId", "1"))
			.andExpect(status().isOk())
			.andDo(print());
	}
	@Order(7)
	@Test
	void findNotWear() throws Exception{
		mockMvc.perform(get("/api/clothes/notweardays")
				.param("days", "1"))
			.andExpect(status().isOk())
			.andExpect(content().string("{\"output\":1,\"msg\":\"성공\",\"data\":[]}"))
			.andDo(print());
	}

}