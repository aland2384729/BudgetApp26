package com.example.budgetapp26.batch.tesco;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.budgetapp26.batch.TransactionData;
import com.example.budgetapp26.batch.TransactionProps;
import com.example.budgetapp26.entities.Transaction;
import com.example.budgetapp26.util.BudgetAppConstants;

public class TescoTransactionData extends TransactionData {

    private static final Logger log = LoggerFactory.getLogger(TescoTransactionData.class);

    @Override
    public Transaction createTransaction(Transaction transaction, TransactionProps props) {
        if (this.getDescription().contains(BudgetAppConstants.AMAZON_CREDIT_CARD_TXN_ID)
                && props.isSkipAmazonPayments()) {
            log.debug("Skipping transaction: Payment to Amazon credit card detected");
            return null;
        } else if (this.getDescription().contains(BudgetAppConstants.AMEX_CREDIT_CARD_TXN_ID)
                && props.isSkipAmexPayments()) {
            log.debug("Skipping transaction: Payment to Amex credit card detected");
            return null;
        }

        return transaction;
    }

}