package com.example.budgetapp26.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.budgetapp26.categorization.TransactionCategory;
import com.example.budgetapp26.categorization.TxnCategorizationUtil;
import com.example.budgetapp26.entities.Transaction;

public class TransactionDataItemProcessor implements ItemProcessor<TransactionData, Transaction> {

    private static final Logger log = LoggerFactory.getLogger(TransactionDataItemProcessor.class);

    @Autowired
    private TransactionPropertyHelperService txnPropertyHelper;

    @Override
    public Transaction process(final TransactionData transactionData) {
        log.debug("Processing " + transactionData);

        Transaction transaction = null;

        // If the transaction is not Pending
        if (transactionData.getTxnDate() != null) {
            transaction = new Transaction();
            transaction.setDate(transactionData.getTxnDate());
            transaction.setDescription(transactionData.getDescription());
            transaction.setTheValue(transactionData.getValue());

            TransactionCategory txnCategory = TxnCategorizationUtil.getInstance()
                    .getCategory(transactionData.getDescription());
            transaction.setCategory(txnCategory);

            // Handle specific transaction source processing
            transaction = transactionData.createTransaction(transaction, txnPropertyHelper.getTransactionProps());
        }

        log.debug("Creating Transaction: " + transaction);

        return transaction;
    }

}