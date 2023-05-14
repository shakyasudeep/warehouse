/**
 * 
 */
package com.example.demo.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.example.demo.entity.Deal;

@Component
public class DealValidator {

	public List<DealValidationResult> validateDeals(List<Deal> deals) {

		List<DealValidationResult> validationResults = new ArrayList<>();
		Set<String> dealIds = new HashSet<>();

		for (Deal deal : deals) {
			DealValidationResult validationResult = new DealValidationResult(deal.getDealId(),deal);
			boolean isValid = true;

			// Validate unique deal ID
			if (dealIds.contains(deal.getDealId())) {
				isValid = false;
				validationResult.addValidationError("Duplicate deal ID found");
			} else {
				dealIds.add(deal.getDealId());
			}

			// Validate required fields
			if (deal.getDealId() == null || deal.getFromCurrency() == null || deal.getToCurrency() == null ||
					deal.getTimestamp() == null || deal.getAmount() == null) {
				isValid = false;
				validationResult.addValidationError("Missing required fields");
			}

			// Validate field formats (additional validation logic can be added here)
			if (!isValidDealIdFormat(deal.getDealId())) {
				isValid = false;
				validationResult.addValidationError("Invalid deal ID format");
			}

			if (!isValidCurrencyFormat(deal.getFromCurrency())) {
				isValid = false;
				validationResult.addValidationError("Invalid 'from' currency format");
			}

			if (!isValidCurrencyFormat(deal.getToCurrency())) {

				isValid = false;
				validationResult.addValidationError("Invalid 'to' currency format");
			}

			if (!isValidTimestampFormat(deal.getTimestamp())) {
				isValid = false;
				validationResult.addValidationError("Invalid timestamp format");
			}

			if (!isValidAmount(deal.getAmount().doubleValue())) {
				isValid = false;
				validationResult.addValidationError("Invalid amount format");
			}

			validationResult.setValid(isValid);
			validationResults.add(validationResult);
		}

		return validationResults;
	}

	private boolean isValidDealIdFormat(String dealId) {
		// Add your deal ID validation logic here
		return dealId != null && !dealId.isEmpty();
	}

	private boolean isValidCurrencyFormat(String currency) {
		// Add your currency validation logic here
		return currency != null && currency.matches("[A-Z]{3}");
	}

	private boolean isValidTimestampFormat(Date timestamp) {
		// Add your timestamp validation logic here
		return timestamp != null; // Modify this based on your timestamp validation requirements
	}

	private boolean isValidAmount(Double amount) {
		// Add your amount validation logic here
		return amount != null && amount > 0;
	}
}
