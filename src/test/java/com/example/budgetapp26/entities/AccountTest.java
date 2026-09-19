package com.example.budgetapp26.entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AccountTest {

    @Test
    public void testSetAndGetId() {
        Account account = new Account();
        Integer id = 123;
        account.setId(id);
        assertEquals(id, account.getId(), "The ID should be correctly set and retrieved.");
    }
}