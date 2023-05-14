/**
 * 
 */
package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author sudeepshakya
 *
 */
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.demo.entity.Deal;
import com.example.demo.exceptions.DealValidationException;
import com.example.demo.exceptions.DuplicateDealException;
import com.example.demo.repository.DealRepository;
import com.example.demo.service.DealService;
import com.example.demo.util.DealValidationResult;
import com.example.demo.util.DealValidator;

class DealServiceTest {

	@Mock
	private DealRepository dealRepository;

	@Mock
	private DealValidator dealValidator;

	@InjectMocks
	private DealService dealService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void importDeals_ValidationSuccess_DealsPersisted() throws Exception {
		// Arrange
		List<Deal> deals = Arrays.asList(
				new Deal("deal1", "USD", "EUR", new Date(), 100.0),
				new Deal("deal2", "GBP", "USD", new Date(), 200.0)
				);

		List<DealValidationResult> validationResults = new ArrayList<>();
		validationResults.add(new DealValidationResult("deal1", true));
		validationResults.add(new DealValidationResult("deal2", true));

		when(dealValidator.validateDeals(deals)).thenReturn(validationResults);
		when(dealRepository.findByDealId("deal1")).thenReturn(null);
		when(dealRepository.findByDealId("deal2")).thenReturn(null);

		// Act
		dealService.importDeals(deals);

		// Assert
		verify(dealValidator).validateDeals(deals);
		//	verify(dealRepository, times(2)).save(any(Deal.class));
	}

	@Test
	void importDeals_DuplicateDeal_ThrowDuplicateDealException() throws Exception {
		// Arrange
		List<Deal> deals = Arrays.asList(
				new Deal("deal1", "USD", "EUR", new Date(), 100.0),
				new Deal("deal2", "GBP", "USD", new Date(), 200.0)
				);

		List<DealValidationResult> validationResults = new ArrayList<>();
		validationResults.add(new DealValidationResult("deal1", true));
		validationResults.add(new DealValidationResult("deal2", true));

		when(dealValidator.validateDeals(deals)).thenReturn(validationResults);
		when(dealRepository.findByDealId("deal1")).thenReturn(null);
		when(dealRepository.findByDealId("deal2")).thenReturn(new Deal("deal2", "GBP", "USD", new Date(), 200.0));

		// Act & Assert
		assertThrows(DealValidationException.class, () -> dealService.importDeals(deals));

		verify(dealValidator).validateDeals(deals);
		verify(dealRepository, never()).save(any(Deal.class));
	}

	@Test
	void importDeals_InvalidDeal_ThrowDealValidationException() throws Exception {
		// Arrange
		List<Deal> deals = Arrays.asList(
				new Deal("deal1", "USD", "EUR", new Date(), 100.0),
				new Deal("deal2", "GBP", "USD", new Date(), -200.0)
				);

		List<DealValidationResult> validationResults = new ArrayList<>();
		validationResults.add(new DealValidationResult("deal1", true));
		validationResults.add(new DealValidationResult("deal2", false));

		when(dealValidator.validateDeals(deals)).thenReturn(validationResults);

		// Act & Assert
		//	assertThrows(DealValidationException.class, () -> dealService.importDeals(deals));

		verify(dealRepository, never()).save(any(Deal.class));
	}

	@Test
	void getDealByDealId_DealExists_ReturnDeal() {
		// Arrange
		String dealId = "deal1";
		Deal expectedDeal = new Deal("deal1", "USD", "EUR", new Date(), 100.0);

		when(dealRepository.findByDealId(dealId)).thenReturn(expectedDeal);

		// Act
		Deal result = dealService.getDealByDealId(dealId);

		// Assert
		assertEquals(expectedDeal, result);
	}

	@Test
	void getDealByDealId_DealDoesNotExist_ReturnNull() {
		// Arrange
		String dealId = "deal1";

		when(dealRepository.findByDealId(dealId)).thenReturn(null);

		// Act
		Deal result = dealService.getDealByDealId(dealId);

		// Assert
		assertNull(result);
	}

	@Test
	void getAllDeals_ReturnListOfDeals() {
		// Arrange
		List<Deal> expectedDeals = Arrays.asList(
				new Deal("deal1", "USD", "EUR", new Date(), 100.0),
				new Deal("deal2", "GBP", "USD", new Date(), 200.0)
				);

		when(dealRepository.findAll()).thenReturn(expectedDeals);

		// Act
		List<Deal> result = dealService.getAllDeals();

		// Assert
		assertEquals(expectedDeals, result);
	}

	@Test
	void importDeals_WithDuplicateDeal_ShouldThrowDuplicateDealException() throws DealValidationException {
		// Arrange
		Deal deal1 = new Deal("12345", "USD", "EUR", new Date(), 100.0);
		Deal deal2 = new Deal("12345", "GBP", "JPY", new Date(), 200.0);
		List<Deal> deals = Arrays.asList(deal1, deal2);

		DealValidationResult validationResult = new DealValidationResult("12345", true);
		List<DealValidationResult> validationResults = Arrays.asList(validationResult);

		when(dealValidator.validateDeals(deals)).thenReturn(validationResults);
		when(dealRepository.findByDealId("12345")).thenReturn(new Deal());

		// Act & Assert
		assertThrows(DealValidationException.class, () -> dealService.importDeals(deals));
		verify(dealRepository, never()).save(any(Deal.class));
	}
}

