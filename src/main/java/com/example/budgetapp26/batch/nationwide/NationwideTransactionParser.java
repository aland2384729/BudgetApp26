package com.example.budgetapp26.batch.nationwide;

import com.example.budgetapp26.batch.TransactionEntityToCsvPrinter;
import jakarta.persistence.EntityManagerFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.infrastructure.item.database.JpaItemWriter;
import org.springframework.batch.infrastructure.item.file.FlatFileItemReader;
import org.springframework.batch.infrastructure.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import com.example.budgetapp26.batch.TransactionData;
import com.example.budgetapp26.batch.TransactionDataItemProcessor;
import com.example.budgetapp26.batch.util.TransactionParserUtils;
import com.example.budgetapp26.entities.Transaction;
import com.example.budgetapp26.util.BudgetAppConstants;

@Configuration
public class NationwideTransactionParser {

    private static final Logger log = LoggerFactory.getLogger(NationwideTransactionParser.class);

    private static final String NATIONWIDE_TXN_FILE_PATH = BudgetAppConstants.STATEMENTS_DIR
            + "/" + BudgetAppConstants.NATIONWIDE_STATEMENT_FILE_NAME;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Bean
    public FlatFileItemReader<TransactionData> nationwideTxnReader() {
        log.debug("inside nationwideTxnReader");

        FlatFileItemReader<TransactionData> itemReader = new FlatFileItemReaderBuilder<TransactionData>()
                .name("nationwideTxnReader")
                .resource(new FileSystemResource(NATIONWIDE_TXN_FILE_PATH))
                .delimited()
                .names(new String[] { "txnDate", "transactionType", "description", "debit", "credit", "balance" })
                .fieldSetMapper(new NationwideTransactionDataFieldSetMapper())
                .build();

        log.debug("created itemReader");

        itemReader.setLinesToSkip(5);
        itemReader.setStrict(false);

        log.debug("returning itemReader");

        return itemReader;
    }

    @Bean
    public TransactionDataItemProcessor nationwideTxnProcessor() {
        return new TransactionDataItemProcessor();
    }

    @Bean
    public JpaItemWriter<Transaction> nationwideTxnWriter() {
        return TransactionParserUtils.transactionWriter(entityManagerFactory);
    }

}