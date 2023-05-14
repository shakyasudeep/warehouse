package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Deal;

@Repository
public interface DealRepository extends JpaRepository<Deal, Long> {

	Deal findByDealId(String dealId);

}
