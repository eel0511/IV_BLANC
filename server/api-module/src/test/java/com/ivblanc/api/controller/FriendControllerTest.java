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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ivblanc.api.config.security.JwtTokenProvider;
import com.ivblanc.api.dto.req.MakeFriendReqDTO;
import com.ivblanc.api.dto.res.FriendResDTO;
import com.ivblanc.api.service.FriendService;
import com.ivblanc.api.service.common.ResponseService;
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class FriendControllerTest {
	@Autowired
	MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Order(1)
	@Test
	void addFriend() throws Exception {
		String content = objectMapper.writeValueAsString(new MakeFriendReqDTO("a", "b"));
		mockMvc.perform(post("/api/friend/save")
				.content(content)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().string("{\"output\":1,\"msg\":\"성공\",\"data\":{\"friend_name\":\"b\"}}"))
			.andDo(print());
	}

	@Order(2)
	@Test
	void findAllNotAcceptFriend() throws Exception {
		mockMvc.perform(get("/api/friend/isnotaccept")
				.param("applicant","a"))
			.andExpect(status().isOk())
			.andExpect(content().string("{\"output\":1,\"msg\":\"성공\",\"data\":[{\"friend_name\":\"b\"}]}"))
			.andDo(print());
	}

	@Order(3)
	@Test
	void acceptFriend() throws Exception {
		String content = objectMapper.writeValueAsString(new MakeFriendReqDTO("a", "b"));
		mockMvc.perform(post("/api/friend/isaccept")
				.content(content)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().string("{\"output\":1,\"msg\":\"성공\",\"data\":{\"friend_name\":\"b\"}}"))
			.andDo(print());
	}

	@Order(4)
	@Test
	void deleteFriend() throws Exception {
		mockMvc.perform(delete("/api/friend/delete")
				.param("applicant","a")
				.param("friend_name","b"))
			.andExpect(status().isOk())
			.andExpect(content().string("{\"output\":1,\"msg\":\"성공\",\"data\":{\"friend_name\":\"b\"}}"))
			.andDo(print());

	}

	@Order(5)
	@Test
	void findAllFriend() throws Exception {
		mockMvc.perform(get("/api/friend/all")
			.param("applicant","a"))
			.andExpect(status().isOk())
			.andExpect(content().string("{\"output\":1,\"msg\":\"성공\",\"data\":[]}"))
			.andDo(print());

	}

	@Test
	void findAllAcceptFriend() {
	}

	@Test
	void cancelFriend() {
	}


}