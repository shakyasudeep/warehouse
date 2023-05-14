/**
 * 
 */
package com.example.demo;

/**
 * @author sudeepshakya
 *
 */
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.demo.entity.Deal;
import com.example.demo.util.DealValidationResult;
import com.example.demo.util.DealValidator;

public class DealValidatorTest {

	private DealValidator dealValidator;

	@BeforeEach
	public void setUp() {
		dealValidator = new DealValidator();
	}

	@Test
	public void testValidateDeals_withValidDeals_shouldReturnValidResults() {
		// Arrange
		List<Deal> deals = createValidDeals();

		// Act
		List<DealValidationResult> validationResults = dealValidator.validateDeals(deals);

		// Assert
		assertEquals(deals.size(), validationResults.size());
		for (DealValidationResult validationResult : validationResults) {
			assertTrue(validationResult.isValid());
			assertTrue(validationResult.getValidationErrors().isEmpty());
		}
	}

	@Test
	public void testValidateDeals_withDuplicateDealId_shouldReturnInvalidResult() {
		// Create sample deals
		List<Deal> deals = new ArrayList<>();
		deals.add(new Deal("D1", "USD", "EUR", new Date(), 100.0));
		deals.add(new Deal("D2", "EUR", "USD", new Date(), 200.0));
		deals.add(new Deal("D1", "USD", "GBP", new Date(), 300.0));

		// Validate the deals
		List<DealValidationResult> validationResults = dealValidator.validateDeals(deals);

		// Assert that the validation results contain an invalid result for the duplicate deal ID
		assertEquals(3, validationResults.size());

		DealValidationResult validationResult1 = validationResults.get(0);
		assertEquals("D1", validationResult1.getDealId());
		assertTrue(validationResult1.isValid());
		assertEquals(0, validationResult1.getValidationErrors().size());

		DealValidationResult validationResult2 = validationResults.get(1);
		assertEquals("D2", validationResult2.getDealId());
		assertTrue(validationResult2.isValid());
		assertEquals(0, validationResult2.getValidationErrors().size());

		DealValidationResult validationResult3 = validationResults.get(2);
		assertEquals("D1", validationResult3.getDealId());
		assertFalse(validationResult3.isValid());
		assertEquals(1, validationResult3.getValidationErrors().size());
		assertEquals("Duplicate deal ID found", validationResult3.getValidationErrors().get(0));
	}



	@Test
	public void testValidateDeals_withMissingRequiredFields_shouldReturnInvalidResult() {
		// Arrange
		List<Deal> deals = createValidDeals();
		Deal dealWithMissingFields = new Deal(null, "USD", "EUR", new Date(), new BigDecimal("100.00"));
		deals.add(dealWithMissingFields);

		// Act
		List<DealValidationResult> validationResults = dealValidator.validateDeals(deals);

		// Assert
		assertEquals(deals.size(), validationResults.size());
		for (DealValidationResult validationResult : validationResults) {
			if (validationResult.getDeal().equals(dealWithMissingFields)) {
				assertFalse(validationResult.isValid());
				assertEquals(2, validationResult.getValidationErrors().size());
				assertTrue(validationResult.getValidationErrors().contains("Missing required fields"));
			} else {
				assertTrue(validationResult.isValid());
				assertTrue(validationResult.getValidationErrors().isEmpty());
			}
		}
	}

	// Add more test methods to cover other scenarios

	private List<Deal> createValidDeals() {
		List<Deal> deals = new ArrayList<>();
		deals.add(new Deal("DEAL1", "USD", "EUR", new Date(), new BigDecimal("100.00")));
		deals.add(new Deal("DEAL2", "EUR", "GBP", new Date(), new BigDecimal("200.00")));
		deals.add(new Deal("DEAL3", "GBP", "USD", new Date(), new BigDecimal("300.00")));
		return deals;
	}
}
