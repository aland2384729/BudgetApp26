package com.example.budgetapp26.batch.natwest;

import jakarta.persistence.EntityManagerFactory;

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
public class NatwestTransactionParser {

    private static final String NATWEST_TXN_FILE_PATH = BudgetAppConstants.STATEMENTS_DIR + "/" +
            BudgetAppConstants.NATWEST_STATEMENT_FILE_NAME;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Bean
    public FlatFileItemReader<TransactionData> nwTxnReader() {
        FlatFileItemReader<TransactionData> itemReader = new FlatFileItemReaderBuilder<TransactionData>()
                .name("nwTxnReader")
                .resource(new FileSystemResource(NATWEST_TXN_FILE_PATH))
                .delimited()
                .names(new String[] {"txnDate", "type", "description", "value", "balance", "accountName", "accountNumber", "last"})
                .fieldSetMapper(new NatwestTransactionDataFieldSetMapper())
                .build();

        // Allow no files
        itemReader.setStrict(false);

        // Skip header and white space lines
        itemReader.setLinesToSkip(3);

        return itemReader;
    }

    @Bean
    public TransactionDataItemProcessor nwTxnProcessor() {
        return new TransactionDataItemProcessor();
    }

    @Bean
    public JpaItemWriter<Transaction> nwTxnWriter() {
        return TransactionParserUtils.transactionWriter(entityManagerFactory);
    }

}