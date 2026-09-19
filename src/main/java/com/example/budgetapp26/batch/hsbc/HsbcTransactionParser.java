package com.example.budgetapp26.batch.hsbc;

import com.example.budgetapp26.batch.TransactionData;
import com.example.budgetapp26.batch.TransactionDataItemProcessor;
import com.example.budgetapp26.batch.util.TransactionParserUtils;
import com.example.budgetapp26.entities.Transaction;
import com.example.budgetapp26.util.BudgetAppConstants;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.infrastructure.item.database.JpaItemWriter;
import org.springframework.batch.infrastructure.item.file.FlatFileItemReader;
import org.springframework.batch.infrastructure.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import java.io.File;

@Configuration
public class HsbcTransactionParser {

    private static final String HSBC_TXN_FILE_PATH = BudgetAppConstants.STATEMENTS_DIR +
            File.separator +
            BudgetAppConstants.HSBC_STATEMENT_FILE_NAME;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Bean
    public FlatFileItemReader<TransactionData> hsbcTxnReader() {
        FlatFileItemReader<TransactionData> itemReader = new FlatFileItemReaderBuilder<TransactionData>()
                .name("hsbcTxnReader")
                .resource(new FileSystemResource(HSBC_TXN_FILE_PATH))
                .delimited()
                .names(new String[] { "txnDate", "description", "value" })
                .fieldSetMapper(new HsbcTransactionDataFieldSetMapper())
                .build();

        // Allow no CSV file to be present in the statements directory
        itemReader.setStrict(false);

        return itemReader;
    }

    @Bean
    public TransactionDataItemProcessor hsbcTxnProcessor() {
        return new TransactionDataItemProcessor();
    }

    @Bean
    public JpaItemWriter<Transaction> hsbcTxnWriter() {
        return TransactionParserUtils.transactionWriter(entityManagerFactory);
    }

}
