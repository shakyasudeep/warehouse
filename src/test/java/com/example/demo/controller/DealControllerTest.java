/**
 * 
 */
package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.demo.entity.Deal;
import com.example.demo.exceptions.DealValidationException;
import com.example.demo.exceptions.DuplicateDealException;
import com.example.demo.service.DealService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(DealController.class)
public class DealControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private DealService dealService;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired 
	Jackson2ObjectMapperBuilder objectMapperBuilder;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
		DealController dealController = new DealController(dealService);
		mockMvc = MockMvcBuilders.standaloneSetup(dealController).build();
		//	objectMapper = objectMapperBuilder.build();


		/*JavaTimeModule javaTimeModule = new JavaTimeModule();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
		javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(formatter));
		javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(formatter));
		objectMapper.registerModule(javaTimeModule);


		objectMapper.findAndRegisterModules();
		objectMapper.registerModule(new ParameterNamesModule())
		.registerModule(new Jdk8Module())
		.registerModule(new JavaTimeModule());
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);*/
		//	objectMapper.registerModule(new JavaTimeModule()); // Register JavaTimeModule for Java 8 date/time types
	}

	@Test
	public void testImportDeals_validRequest() throws Exception {
		List<Deal> deals = Arrays.asList(
				new Deal("deal1", "USD", "EUR", new Date(), 100.0),
				new Deal("deal2", "EUR", "JPY", new Date(), 200.0),
				new Deal("deal3", "GBP", "USD", new Date(), 300.0)
				);

		Mockito.doNothing().when(dealService).importDeals(deals);

		mockMvc.perform(MockMvcRequestBuilders.post("/deals/import")
				.contentType(MediaType.APPLICATION_JSON)
				.content(convertToJson(deals)))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(print());
	}

	@Test
	public void testImportDeals_DuplicateDeal_ExceptionThrown() throws Exception {
		// Create duplicate deal
		Deal deal = new Deal("deal1", "USD", "EUR", new Date(), 100.0);
		List<Deal> deals = Collections.singletonList(deal);

		// Mock the service method to throw DuplicateDealException
		doThrow(DealValidationException.class).when(dealService).importDeals(any());

		// Perform the request
		mockMvc.perform(post("/deals/import")
				.contentType(MediaType.APPLICATION_JSON)
				.content(convertToJson(deals)))
		.andExpect(status().isBadRequest());
	}


	@Test
	public void testImportDeals_InvalidDeal_ValidationErrorThrown() throws Exception {
		// Create invalid deal with missing required fields
		Deal deal = new Deal("deal1", null, "EUR", new Date(), 100.0);
		List<Deal> deals = Collections.singletonList(deal);

		// Mock the service method to throw DealValidationException
		doThrow(DealValidationException.class).when(dealService).importDeals(any());

		// Perform the request
		mockMvc.perform(post("/deals/import")
				.contentType(MediaType.APPLICATION_JSON)
				.content(convertToJson(deals)))
		.andExpect(status().isBadRequest());
	}


	private String convertToJson(Object object) throws JsonProcessingException {
		return objectMapper.writeValueAsString(object);
	}
}
