package com.example.budgetapp26.batch.amex2026;

import com.example.budgetapp26.batch.TransactionData;
import com.example.budgetapp26.batch.TransactionDataItemProcessor;
import com.example.budgetapp26.batch.amex.AmexTransactionDataFieldSetMapper;
import com.example.budgetapp26.batch.util.TransactionParserUtils;
import com.example.budgetapp26.entities.Transaction;
import com.example.budgetapp26.util.BudgetAppConstants;
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

@Configuration
public class Amex2026TransactionParser {

    private static final Logger log = LoggerFactory.getLogger(Amex2026TransactionParser.class);

    private static final String AMEX_2026_TXN_FILE_PATH = BudgetAppConstants.STATEMENTS_DIR + "/"
            + BudgetAppConstants.AMEX_2026_STATEMENT_FILE_NAME;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Bean
    public FlatFileItemReader<TransactionData> amex2026TxnReader() {
        FlatFileItemReader<TransactionData> itemReader = new FlatFileItemReaderBuilder<TransactionData>()
                .name("amexTxnReader")
                .resource(new FileSystemResource(AMEX_2026_TXN_FILE_PATH))
                .delimited()
                .names(new String[] { "txnDate", "description", "cardMember", "accNum", "value" })
                .fieldSetMapper(new Amex2026TransactionDataFieldSetMapper())
                .build();

        // Ignore header
        itemReader.setLinesToSkip(1);

        // Allow no AMEX 2026 CSV file to be present in the statements directory
        itemReader.setStrict(false);

        return itemReader;
    }

    @Bean
    public TransactionDataItemProcessor amex2026TxnProcessor() {
        return new TransactionDataItemProcessor();
    }

    @Bean
    public JpaItemWriter<Transaction> amex2026TxnWriter() {
        return TransactionParserUtils.transactionWriter(entityManagerFactory);
    }

}
