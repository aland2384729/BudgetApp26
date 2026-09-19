package com.example.budgetapp26.batch.tsb;

import com.example.budgetapp26.batch.TransactionData;
import com.example.budgetapp26.batch.TransactionProps;
import com.example.budgetapp26.entities.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TsbTransactionData extends TransactionData {

    private static final Logger log = LoggerFactory.getLogger(TsbTransactionData.class);

    @Override
    public Transaction createTransaction(Transaction transaction, TransactionProps props) {
        return transaction;
    }
}
