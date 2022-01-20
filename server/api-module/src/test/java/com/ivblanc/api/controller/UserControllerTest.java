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
import com.ivblanc.api.dto.req.SignOutReqDTO;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class UserControllerTest {
	@Autowired
	MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Order(1)
	@Test
	void updatePw1() throws Exception {
		mockMvc.perform(put("/api/user/update/pw")
				.param("uid", "a@a.com")
				.param("pw", "456")
				.param("pw_new", "456")
				.param("pw_check", "456"))
			.andExpect(status().isOk())
			.andExpect(content().string("{\"output\":0,\"msg\":\"비밀번호가 일치하지 않습니다.\"}"))
			.andDo(print());
	}

	@Order(2)
	@Test
	void updatePw2() throws Exception {
		mockMvc.perform(put("/api/user/update/pw")
				.param("uid", "a@a.com")
				.param("pw", "123")
				.param("pw_new", "456")
				.param("pw_check", "456"))
			.andExpect(status().isOk())
			.andExpect(content().string("{\"output\":1,\"msg\":\"비밀번호 변경 성공.\"}"))
			.andDo(print());
	}

	@Order(3)
	@Test
	void updatePersonal() throws Exception {
		mockMvc.perform(put("/api/user/update/personal")
				.param("uid", "a@a.com")
				.param("pw", "456")
				.param("age", "23")
				.param("gender", "2")
				.param("phone", "010-0000-0000"))
			.andExpect(status().isOk())
			.andExpect(content().string("{\"output\":1,\"msg\":\"개인정보 변경 성공.\"}"))
			.andDo(print());
	}

	@Order(4)
	@Test
	void userSignOut() throws Exception {
		mockMvc.perform(delete("/api/user/signOut")
				.param("uid", "a@a.com")
				.param("pw", "456"))
			.andExpect(status().isOk())
			.andExpect(content().string("{\"output\":1,\"msg\":\"탈퇴 성공\"}"))
			.andDo(print());
	}

}
