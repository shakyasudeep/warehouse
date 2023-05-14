/**
 * 
 */
package com.example.demo.controller;

import java.util.List;

/**
 * @author sudeepshakya
 *
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Deal;
import com.example.demo.exceptions.DealValidationException;
import com.example.demo.service.DealService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/deals")
public class DealController {
	private final DealService dealService;

	@Autowired
	public DealController(DealService dealService) {
		this.dealService = dealService;
	}

	@PostMapping("/import")
	public ResponseEntity<String> importDeals(@RequestBody List<Deal> deals) {
		try {
			dealService.importDeals(deals);
			log.info("Deals imported successfully.");
			return ResponseEntity.ok("Deals imported successfully.");
		} catch (DealValidationException e) {
			log.error("Deal validation failed: {}", e.getMessage());
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}

