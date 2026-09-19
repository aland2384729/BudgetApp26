package com.example.budgetapp26.batch.amex2026;

import com.example.budgetapp26.batch.TransactionData;
import com.example.budgetapp26.batch.TransactionProps;
import com.example.budgetapp26.entities.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Amex2026TransactionData extends TransactionData {

    private static final Logger log = LoggerFactory.getLogger(Amex2026TransactionData.class);

    @Override
    public Transaction createTransaction(Transaction transaction, TransactionProps props) {
        if (this.getValue() > 0 && props.isSkipAmexPayments()) {
            log.debug("Skipping transaction: Amex credit card payment detected");
            return null;
        }

        return transaction;
    }

}
