package com.example.budgetapp26.batch.tsb;

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
public class TsbTransactionParser {

    private static final String TSB_STATEMENT_TXN_FILE = BudgetAppConstants.STATEMENTS_DIR +
            File.separator + BudgetAppConstants.TSB_STATEMENT_FILE_NAME;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Bean
    public FlatFileItemReader<TransactionData> tsbTxnReader() {
        FlatFileItemReader<TransactionData> itemReader = new FlatFileItemReaderBuilder<TransactionData>()
                .name("tsbTxnReader")
                .resource(new FileSystemResource(TSB_STATEMENT_TXN_FILE))
                .delimited()
                .names(new String[] { "type", "description", "date", "currency", "idCreditCard", "dateEntered",
                        "reference", "amount" })
                .fieldSetMapper(new TsbTransactionDataFieldSetMapper())
                .build();

        // IMPORTANT NOTE: it is assumed there is just a one line header to skip, there is a bunch of boilerplate by default which
        // should already be removed by the time you hit this in processing.

        // skip header
        itemReader.setLinesToSkip(1);

        // Allow no CSV file to be present in the statements directory
        itemReader.setStrict(false);

        return itemReader;
    }

    @Bean
    public TransactionDataItemProcessor tsbTxnProcessor() {
        return new TransactionDataItemProcessor();
    }

    @Bean
    public JpaItemWriter<Transaction> tsbTxnWriter() {
        return TransactionParserUtils.transactionWriter(entityManagerFactory);
    }

}
