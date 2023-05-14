/**
 * 
 */
package com.example.demo.util;

/**
 * @author sudeepshakya
 *
 */
import java.util.ArrayList;
import java.util.List;

import com.example.demo.entity.Deal;

public class DealValidationResult {

	private final String dealId;
	private final List<String> validationErrors;
	private boolean isValid;
	private Deal deal;

	public DealValidationResult(String dealId, Deal deal) {
		this.dealId = dealId;
		this.validationErrors = new ArrayList<>();
		this.isValid = true;
		this.deal = deal;
	}
	
	public DealValidationResult(String dealId, boolean isValid) {
		this.dealId = dealId;
		this.validationErrors = new ArrayList<>();
		this.isValid = isValid;
	}

	public String getDealId() {
		return dealId;
	}

	public List<String> getValidationErrors() {
		return validationErrors;
	}

	public void addValidationError(String error) {
		validationErrors.add(error);
	}

	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean valid) {
		isValid = valid;
	}

	public Deal getDeal() {
		return deal;
	}
}
