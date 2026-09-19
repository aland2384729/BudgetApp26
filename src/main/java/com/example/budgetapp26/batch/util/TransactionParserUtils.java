package com.example.budgetapp26.batch.util;

import jakarta.persistence.EntityManagerFactory;

import org.springframework.batch.infrastructure.item.database.JpaItemWriter;

import com.example.budgetapp26.entities.Transaction;

public class TransactionParserUtils {

    public static JpaItemWriter<Transaction> transactionWriter(EntityManagerFactory entityManagerFactory) {
        JpaItemWriter<Transaction> jpaItemWriter = new JpaItemWriter<>(entityManagerFactory);
        return jpaItemWriter;
    }

}