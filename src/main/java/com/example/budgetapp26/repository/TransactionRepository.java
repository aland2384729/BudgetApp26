package com.example.budgetapp26.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.budgetapp26.entities.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    public List<Transaction> findByTheValueLessThan(Double value);

}