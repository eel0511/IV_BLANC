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
import com.ivblanc.api.dto.req.MakeHistoryReqDTO;
import com.ivblanc.api.dto.req.UpdateHistoryReqDTO;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class HistoryControllerTest {
//	@Autowired
//	MockMvc mockMvc;
//
//	String TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sInVzZXJQayI6IjEiLCJpYXQiOjE2NDM4Njg5NTIsImV4cCI6MTY0NjQ2MDk1Mn0.mM0LIiTylyEChjIJgTPIs83uiB-p2Vp7JvqfrRvyROc";
//
//	@Autowired
//	private ObjectMapper objectMapper;
//
//	@Order(1)
//	@Test
//	void addHistory() throws Exception {
//		String content = objectMapper.writeValueAsString(new MakeHistoryReqDTO(BigDecimal.valueOf(0.0), BigDecimal.valueOf(0.0), "2022-02-03", "맑음", -5, 5, "test text", "subject", 1,  null));
//		mockMvc.perform(post("/api/history/add")
//				.header("X-AUTH-TOKEN", TOKEN)
//				.content(content)
//				.contentType(MediaType.APPLICATION_JSON)
//				.accept(MediaType.APPLICATION_JSON))
//			.andExpect(status().isOk())
//			.andDo(print());
//	}
//
//	@Order(2)
//	@Test
//	void findAllHistory() throws Exception {
//		mockMvc.perform(get("/api/history/find/all")
//				.header("X-AUTH-TOKEN", TOKEN)
//				.contentType(MediaType.APPLICATION_JSON)
//				.accept(MediaType.APPLICATION_JSON))
//			.andExpect(status().isOk())
//			.andDo(print());
//	}
//
//	@Order(3)
//	@Test
//	void findHistoryThisMonth() throws Exception {
//		mockMvc.perform(get("/api/history/find/thisMonth")
//				.header("X-AUTH-TOKEN", TOKEN)
//				.contentType(MediaType.APPLICATION_JSON)
//				.accept(MediaType.APPLICATION_JSON))
//			.andExpect(status().isOk())
//			.andDo(print());
//	}
//
//	@Order(4)
//	@Test
//	void findHistorySelectedMonth() throws Exception {
//		mockMvc.perform(get("/api/history/find/month")
//				.header("X-AUTH-TOKEN", TOKEN)
//				.param("year", "2022")
//				.param("month", "3")
//				.contentType(MediaType.APPLICATION_JSON)
//				.accept(MediaType.APPLICATION_JSON))
//			.andExpect(status().isOk())
//			.andDo(print());
//	}
//
//	@Order(5)
//	@Test
//	void findHistoryByDate() throws Exception {
//		mockMvc.perform(get("/api/history/find/date")
//				.header("X-AUTH-TOKEN", TOKEN)
//				.param("date", "2022-02-04")
//				.contentType(MediaType.APPLICATION_JSON)
//				.accept(MediaType.APPLICATION_JSON))
//			.andExpect(status().isOk())
//			.andDo(print());
//	}
//
//	@Order(6)
//	@Test
//	void findHistoryByWeather() throws Exception {
//		mockMvc.perform(get("/api/history/find/weather")
//				.header("X-AUTH-TOKEN", TOKEN)
//				.param("weather", "맑음")
//				.contentType(MediaType.APPLICATION_JSON)
//				.accept(MediaType.APPLICATION_JSON))
//			.andExpect(status().isOk())
//			.andDo(print());
//	}
//
//	@Order(7)
//	@Test
//	void findHistoryByTemperature() throws Exception {
//		mockMvc.perform(get("/api/history/find/temperature")
//				.header("X-AUTH-TOKEN", TOKEN)
//				.param("최저기온", "-7")
//				.param("최고기온", "2")
//				.contentType(MediaType.APPLICATION_JSON)
//				.accept(MediaType.APPLICATION_JSON))
//			.andExpect(status().isOk())
//			.andDo(print());
//	}
//
//	@Order(8)
//	@Test
//	void findHistoryBySubject() throws Exception {
//		mockMvc.perform(get("/api/history/find/subject")
//				.header("X-AUTH-TOKEN", TOKEN)
//				.param("subject", "subject")
//				.contentType(MediaType.APPLICATION_JSON)
//				.accept(MediaType.APPLICATION_JSON))
//			.andExpect(status().isOk())
//			.andDo(print());
//	}
//
//	@Order(9)
//	@Test
//	void updateHistory() throws Exception {
//		UpdateHistoryReqDTO dto = new UpdateHistoryReqDTO(BigDecimal.valueOf(1.11), BigDecimal.valueOf(2.22), "2022-02-01", "흐림", -1, 3, "text", "subject2", 1);
//		mockMvc.perform(put("/api/history/update")
//				.header("X-AUTH-TOKEN", TOKEN)
//				.param("historyId", "4")
//				.contentType(MediaType.APPLICATION_JSON)
//				.accept(MediaType.APPLICATION_JSON))
//			.andExpect(status().isOk())
//			.andDo(print());
//	}
//
//	@Order(10)
//	@Test
//	void deleteHistory() throws Exception {
//		mockMvc.perform(delete("/api/history/delete")
//				.param("historyId", "3")
//				.contentType(MediaType.APPLICATION_JSON)
//				.accept(MediaType.APPLICATION_JSON))
//			.andExpect(status().isOk())
//			.andDo(print());
//	}
//
//	@Order(11)
//	@Test
//	void deleteHistoryPhoto() throws Exception {
//		mockMvc.perform(delete("/api/history/photo/delete")
//				.param("photoId", "4")
//				.contentType(MediaType.APPLICATION_JSON)
//				.accept(MediaType.APPLICATION_JSON))
//			.andExpect(status().isOk())
//			.andDo(print());
//	}
}