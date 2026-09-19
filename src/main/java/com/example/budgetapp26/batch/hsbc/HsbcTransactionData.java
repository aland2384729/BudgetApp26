package com.example.budgetapp26.batch.hsbc;

import com.example.budgetapp26.batch.TransactionData;
import com.example.budgetapp26.batch.TransactionProps;
import com.example.budgetapp26.entities.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HsbcTransactionData extends TransactionData {

    private static final Logger log = LoggerFactory.getLogger(HsbcTransactionData.class);

    @Override
    public Transaction createTransaction(Transaction transaction, TransactionProps props) {
        if (this.getValue() > 0 && props.isSkipAmexPayments()) {
            log.debug("Skipping transaction: Amex credit card payment detected");
            return null;
        }

        return transaction;
    }

}
