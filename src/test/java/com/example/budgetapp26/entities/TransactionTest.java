package com.example.budgetapp26.entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Date;
import com.example.budgetapp26.categorization.TransactionCategory;

public class TransactionTest {

    @Test
    public void testSetAndGetId() {
        Transaction transaction = new Transaction();
        Integer id = 1;
        transaction.setId(id);
        assertEquals(id, transaction.getId(), "The ID should be correctly set and retrieved.");
    }

    @Test
    public void testSetAndGetDescription() {
        Transaction transaction = new Transaction();
        String description = "Grocery shopping";
        transaction.setDescription(description);
        assertEquals(description, transaction.getDescription(), "The description should be correctly set and retrieved.");
    }

    @Test
    public void testSetAndGetValue() {
        Transaction transaction = new Transaction();
        Double value = 50.0;
        transaction.setTheValue(value);
        assertEquals(value, transaction.getTheValue(), "The value should be correctly set and retrieved.");
    }

    @Test
    public void testSetAndGetDate() {
        Transaction transaction = new Transaction();
        Date date = new Date();
        transaction.setDate(date);
        assertEquals(date, transaction.getDate(), "The date should be correctly set and retrieved.");
    }

    @Test
    public void testSetAndGetAccount() {
        Transaction transaction = new Transaction();
        Account account = new Account();
        transaction.setAccount(account);
        assertEquals(account, transaction.getAccount(), "The account should be correctly set and retrieved.");
    }

    @Test
    public void testSetAndGetCategory() {
        Transaction transaction = new Transaction();
        transaction.setCategory(TransactionCategory.FOOD_AND_DRINK);
        assertEquals(transaction.getCategory(), TransactionCategory.FOOD_AND_DRINK, "The category should be equal to FOOD_AND_DRINK.");
    }
}