package com.example.budgetapp26.batch.tesco;

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
public class TescoTransactionParser {

    private static final String TESCO_TXN_FILE_PATH = BudgetAppConstants.STATEMENTS_DIR
            + "/" + BudgetAppConstants.TESCO_STATEMENT_FILE_NAME;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Bean
    public FlatFileItemReader<TransactionData> tescoTxnReader() {
        FlatFileItemReader<TransactionData> itemReader = new FlatFileItemReaderBuilder<TransactionData>()
                .name("tescoTxnReader")
                .resource(new FileSystemResource(TESCO_TXN_FILE_PATH))
                .delimited()
                .names(new String[] {"txnDate", "number", "description", "debit", "credit", "balance"})
                .fieldSetMapper(new TescoTransactionDataFieldSetMapper())
                .build();

        itemReader.setLinesToSkip(1);
        itemReader.setStrict(false);

        return itemReader;
    }

    @Bean
    public TransactionDataItemProcessor tescoTxnProcessor() {
        return new TransactionDataItemProcessor();
    }

    @Bean
    public JpaItemWriter<Transaction> tescoTxnWriter() {
        return TransactionParserUtils.transactionWriter(entityManagerFactory);
    }

}