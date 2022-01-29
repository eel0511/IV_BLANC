package com.ivblanc.api.controller;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class StyleControllerTest {
//	@Autowired
//	MockMvc mockMvc;
//
//	@Autowired
//	private ObjectMapper objectMapper;
//	@BeforeEach
//	void addclothes()throws Exception{
//		String content = objectMapper.writeValueAsString(new MakeClothesReqDTO(1, "검정", "면", 100, 1, 1));
//		mockMvc.perform(post("/api/clothes/add")
//				.content(content)
//				.contentType(MediaType.APPLICATION_JSON)
//				.accept(MediaType.APPLICATION_JSON))
//			.andExpect(status().isOk())
//			.andExpect(jsonPath("output").value("1"))
//			.andDo(print());
//	}
//	@BeforeEach
//	void addStyle() throws Exception {
//
//		List<MakeStyleDetailReqDTO> makeStyleDetailReqDTOS = new ArrayList<>();
//		makeStyleDetailReqDTOS.add(new MakeStyleDetailReqDTO(1));
//		String content = objectMapper.writeValueAsString(makeStyleDetailReqDTOS);
//		mockMvc.perform(post("/api/style/add")
//				.content(content)
//				.param("madeby", "1")
//				.param("userId", "1")
//				.contentType(MediaType.APPLICATION_JSON)
//				.accept(MediaType.APPLICATION_JSON))
//			.andExpect(status().isOk())
//			.andExpect(jsonPath("output").value("1"))
//			.andDo(print());
//	}
//
//	void findStyle() throws Exception {
//		mockMvc.perform(get("/api/style/finduserstyle")
//				.param("userId", "1"))
//			.andExpect(status().isOk())
//			.andExpect(jsonPath("output").value("1"))
//			.andDo(print());
//	}
//
//	@Test
//	void addFavorite() throws Exception {
//		mockMvc.perform(get("/api/style/addfavorite")
//				.param("styleId", "1"))
//			.andExpect(jsonPath("output").value("1"))
//			.andDo(print());
//	}
//
//	@Test
//	void deleteStyle() throws Exception {
//		mockMvc.perform(delete("/api/style/delete")
//				.param("styleId", "1"))
//			.andExpect(status().isOk())
//			.andExpect(jsonPath("output").value("1"))
//			.andDo(print());
//	}

}