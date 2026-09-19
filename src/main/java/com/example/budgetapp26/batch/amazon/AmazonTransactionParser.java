package com.example.budgetapp26.batch.amazon;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManagerFactory;

import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.batch.infrastructure.item.database.JdbcBatchItemWriter;
import org.springframework.batch.infrastructure.item.database.JpaItemWriter;
import org.springframework.batch.infrastructure.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.infrastructure.item.database.builder.JpaItemWriterBuilder;
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

import javax.sql.DataSource;

@Configuration
public class AmazonTransactionParser {

    private static final String AMAZON_TXN_FILE_PATH = BudgetAppConstants.STATEMENTS_DIR +
            "/" + BudgetAppConstants.AMAZON_STATEMENT_FILE_NAME;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Bean
    public FlatFileItemReader<TransactionData> amazonTxnReader() {
        FlatFileItemReader<TransactionData> itemReader = new FlatFileItemReaderBuilder<TransactionData>()
                .name("amazonTxnItemReader")
                .resource(new FileSystemResource(AMAZON_TXN_FILE_PATH))
                .delimited()
                .names(new String[] {"txnDate", "description", "value"})
                .fieldSetMapper(new AmazonTransactionDataFieldSetMapper())
                .build();

        // Allow no files
        itemReader.setStrict(false);

        // Skip header
        itemReader.setLinesToSkip(1);

        return itemReader;
    }

    @Bean
    public TransactionDataItemProcessor amazonTxnProcessor() {
        return new TransactionDataItemProcessor();
    }

    @Bean
    public JpaItemWriter<Transaction> amazonTxnWriter() {
        return new JpaItemWriterBuilder<Transaction>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

}