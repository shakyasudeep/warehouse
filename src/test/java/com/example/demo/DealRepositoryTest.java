/**
 * 
 */
package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author sudeepshakya
 *
 */
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import com.example.demo.entity.Deal;
import com.example.demo.repository.DealRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {
		"spring.datasource.driver-class-name=org.h2.Driver",
		"spring.datasource.url=jdbc:h2:mem:demo;MODE=MYSQL;DB_CLOSE_ON_EXIT=TRUE",
		"spring.jpa.defer-datasource-initialization=true",
		"spring.jpa.show-sql=true",
		"spring.jpa.properties.hibernate.format_sql=true"
})
public class DealRepositoryTest {

	@Autowired
	private DealRepository dealRepository;

	@Test
	public void testSaveDeal() {
		// Test saving a new deal
		String dealId = "DEAL001";
		String fromCurrency = "USD";
		String toCurrency = "EUR";
		Date timestamp = new Date();
		Double amount = 1000.0;

		Deal deal = new Deal(dealId, fromCurrency, toCurrency, timestamp, amount);
		Deal savedDeal = dealRepository.save(deal);

		assertNotNull(savedDeal.getId());
		assertEquals(dealId, savedDeal.getDealId());
		assertEquals(fromCurrency, savedDeal.getFromCurrency());
		assertEquals(toCurrency, savedDeal.getToCurrency());
		assertEquals(timestamp, savedDeal.getTimestamp());
		assertEquals(BigDecimal.valueOf(amount).setScale(0), savedDeal.getAmount());

		// Test updating an existing deal
		String updatedFromCurrency = "GBP";
		savedDeal.setFromCurrency(updatedFromCurrency);
		Deal updatedDeal = dealRepository.save(savedDeal);

		assertEquals(updatedFromCurrency, updatedDeal.getFromCurrency());
	}

	@Test
	public void testFindAllDeals() {
		// Test retrieving all deals
		Deal deal1 = new Deal("DEAL001", "USD", "EUR", new Date(), 1000.0);
		dealRepository.save(deal1);

		Deal deal2 = new Deal("DEAL002", "GBP", "USD", new Date(), 2000.0);
		dealRepository.save(deal2);

		List<Deal> deals = dealRepository.findAll();

		assertEquals(2, deals.size());
	}

	@Test
	public void testFindById() {
		// Test retrieving a deal by ID
		Deal deal = new Deal("DEAL001", "USD", "EUR", new Date(), 1000.0);
		Deal savedDeal = dealRepository.save(deal);

		Optional<Deal> foundDeal = dealRepository.findById(savedDeal.getId());

		assertTrue(foundDeal.isPresent());
		assertEquals(savedDeal.getId(), foundDeal.get().getId());
		assertEquals(savedDeal.getDealId(), foundDeal.get().getDealId());
	}

	@Test
	public void testDeleteDeal() {
		// Test deleting a deal
		Deal deal = new Deal("DEAL001", "USD", "EUR", new Date(), 1000.0);
		Deal savedDeal = dealRepository.save(deal);

		dealRepository.deleteById(savedDeal.getId());

		assertFalse(dealRepository.existsById(savedDeal.getId()));
	}

	// Add more test methods as needed
}
