/**
 * 
 */
package com.example.demo.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author sudeepshakya
 *
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Deal;
import com.example.demo.exceptions.DealValidationException;
import com.example.demo.exceptions.DuplicateDealException;
import com.example.demo.repository.DealRepository;
import com.example.demo.util.DealValidationResult;
import com.example.demo.util.DealValidator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DealService {

	private final DealRepository dealRepository;
	private final DealValidator dealValidator;

	@Autowired
	public DealService(DealRepository dealRepository, DealValidator dealValidator) {
		this.dealRepository = dealRepository;
		this.dealValidator = dealValidator;
	}

	public void importDeals(List<Deal> deals) throws DealValidationException {

		List<String> validationErrors = new ArrayList<>();
		Set<String> existingDealIds = new HashSet<>();

		List<DealValidationResult> validationResults = dealValidator.validateDeals(deals);

		for (DealValidationResult validationResult : validationResults) {

			String dealId = validationResult.getDealId();
			if (!existingDealIds.contains(dealId)) {
				Deal existingDeal = getDealByDealId(dealId);
				if (existingDeal == null) {
					dealRepository.save(validationResult.getDeal());
					existingDealIds.add(dealId);
					log.info("Deal with ID {} imported successfully.", dealId);
				} else {
					log.warn("Deal with ID {} already exists. Skipping import.", dealId);
					validationErrors.add("Deal with ID " + dealId + " already exists.");
				}
			} else {
				log.error("Invalid deal found: {}", validationResult.getValidationErrors());
				validationErrors.addAll(validationResult.getValidationErrors());
			}
		}
		if (!validationErrors.isEmpty()) {
			throw new DealValidationException("Invalid deals found: " + validationErrors);
		}
	}

	public Deal getDealByDealId(String dealId) {
		return dealRepository.findByDealId(dealId);
	}

	public List<Deal> getAllDeals() {
		return dealRepository.findAll();
	}
}
