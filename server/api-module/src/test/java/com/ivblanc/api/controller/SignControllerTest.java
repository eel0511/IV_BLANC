package com.ivblanc.api.controller;


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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class SignControllerTest {
	@Autowired
	MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Order(1)
	@Test
	void joinDuplicateEmail() throws Exception {
		mockMvc.perform(post("/api/sign/signup")
				.param("email", "a@a.com")
				.param("social", "0")
				.param("password", "456")
				.param("password_chk", "456")
				.param("name", "ssafy")
				.param("phone", "010-0000-0000")
				.param("age", "22")
				.param("gender", "1"))
			.andExpect(status().isOk())
			.andExpect(content().string("{\"output\":0,\"msg\":\"중복된 이메일의 회원이 존재합니다.\"}"))
			.andDo(print());
	}

	@Order(2)
	@Test
	void joinCheckEmailForm() throws Exception {
		mockMvc.perform(post("/api/sign/signup")
				.param("email", "aa.com")
				.param("social", "0")
				.param("password", "456")
				.param("password_chk", "456")
				.param("name", "ssafy")
				.param("phone", "010-0000-0000")
				.param("age", "22")
				.param("gender", "1"))
			.andExpect(status().isOk())
			.andExpect(content().string("{\"output\":0,\"msg\":\"이메일 형식을 확인해주세요.\"}"))
			.andDo(print());
	}

	@Order(3)
	@Test
	void joinCheckPwForm() throws Exception {
		mockMvc.perform(post("/api/sign/signup")
				.param("email", "a1@a.com")
				.param("social", "0")
				.param("password", "456")
				.param("password_chk", "456")
				.param("name", "ssafy")
				.param("phone", "010-0000-0000")
				.param("age", "22")
				.param("gender", "1"))
			.andExpect(status().isOk())
			.andExpect(content().string("{\"output\":0,\"msg\":\"비밀번호는 영문,숫자,특수문자 중 2가지 이상을 포함하며 8자리 이상, 14자리 이하입니다\"}"))
			.andDo(print());
	}

	@Order(4)
	@Test
	void joinCheckPwMatch() throws Exception {
		mockMvc.perform(post("/api/sign/signup")
				.param("email", "a1@a.com")
				.param("social", "0")
				.param("password", "1234567!")
				.param("password_chk", "1234567!!")
				.param("name", "ssafy")
				.param("phone", "010-0000-0000")
				.param("age", "22")
				.param("gender", "1"))
			.andExpect(status().isOk())
			.andExpect(content().string("{\"output\":0,\"msg\":\"비밀번호를 확인해주세요.\"}"))
			.andDo(print());
	}

	@Order(5)
	@Test
	void joinCheckPhoneNum() throws Exception {
		mockMvc.perform(post("/api/sign/signup")
				.param("email", "a1@a.com")
				.param("social", "0")
				.param("password", "1234567!")
				.param("password_chk", "1234567!")
				.param("name", "ssafy")
				.param("phone", "010-0000-0000")
				.param("age", "22")
				.param("gender", "1"))
			.andExpect(status().isOk())
			.andExpect(content().string("{\"output\":0,\"msg\":\"전화번호는 -를 빼고 입력해주세요.\"}"))
			.andDo(print());
	}

	@Order(6)
	@Test
	void joinCheckPhoneForm() throws Exception {
		mockMvc.perform(post("/api/sign/signup")
				.param("email", "a1@a.com")
				.param("social", "0")
				.param("password", "1234567!")
				.param("password_chk", "1234567!")
				.param("name", "ssafy")
				.param("phone", "010000000")
				.param("age", "22")
				.param("gender", "1"))
			.andExpect(status().isOk())
			.andExpect(content().string("{\"output\":0,\"msg\":\"전화번호 형식을 확인해주세요\"}"))
			.andDo(print());
	}

	@Order(7)
	@Test
	void checkJoinSuccess() throws Exception {
		mockMvc.perform(post("/api/sign/signup")
				.param("email", "a1@a.com")
				.param("social", "0")
				.param("password", "1234567!")
				.param("password_chk", "1234567!")
				.param("name", "ssafy")
				.param("phone", "01000000000")
				.param("age", "22")
				.param("gender", "1"))
			.andExpect(status().isOk())
			.andExpect(content().string("{\"output\":1,\"msg\":\"성공\"}"))
			.andDo(print());
	}

	@Order(8)
	@Test
	void checkLoginEmailForm() throws Exception {
		mockMvc.perform(get("/api/sign/login")
				.param("email", "a1a.com")
				.param("social", "0")
				.param("pw", "1234567!")
				.param("token_fcm", ""))
			.andExpect(status().isOk())
			.andExpect(content().string("{\"output\":0,\"msg\":\"이메일 형식을 확인해주세요.\"}"))
			.andDo(print());
	}

	@Order(9)
	@Test
	void checkLoginEmailNotExist() throws Exception {
		mockMvc.perform(get("/api/sign/login")
				.param("email", "a2@a.com")
				.param("social", "0")
				.param("pw", "1234567!")
				.param("token_fcm", ""))
			.andExpect(status().isOk())
			.andExpect(content().string("{\"output\":0,\"msg\":\"존재하지 않는 회원정보입니다.\"}"))
			.andDo(print());
	}

	@Order(10)
	@Test
	void checkLoginEmailNoMatchPw() throws Exception {
		mockMvc.perform(get("/api/sign/login")
				.param("email", "a1@a.com")
				.param("social", "0")
				.param("pw", "1234567!!")
				.param("token_fcm", ""))
			.andExpect(status().isOk())
			.andExpect(content().string("{\"output\":0,\"msg\":\"비밀번호가 일치하지 않습니다.\"}"))
			.andDo(print());
	}

	@Order(11)
	@Test
	void checkLoginSuccess() throws Exception {
		mockMvc.perform(get("/api/sign/login")
				.param("email", "a1@a.com")
				.param("social", "0")
				.param("pw", "1234567!")
				.param("token_fcm", ""))
			.andExpect(status().isOk())
			.andExpect(content().string("{\"output\":1,\"msg\":\"성공\"}"))
			.andDo(print());
	}


}
