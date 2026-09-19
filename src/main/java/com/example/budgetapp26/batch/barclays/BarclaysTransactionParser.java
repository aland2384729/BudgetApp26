package com.example.budgetapp26.batch.barclays;

import jakarta.persistence.EntityManagerFactory;

import org.springframework.batch.infrastructure.item.database.JpaItemWriter;
import org.springframework.batch.infrastructure.item.file.FlatFileItemReader;
import org.springframework.batch.infrastructure.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.infrastructure.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.infrastructure.item.file.transform.DelimitedLineTokenizer;
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
public class BarclaysTransactionParser {

    private static final String BARCLAYS_TXN_FILE_PATH = BudgetAppConstants.STATEMENTS_DIR + "/" +
            BudgetAppConstants.BARCLAYS_STATEMENT_FILE_NAME;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Bean
    public FlatFileItemReader<TransactionData> barclaysTxnReader() {
        DefaultLineMapper<TransactionData> lineMapper = new DefaultLineMapper<>();
        //DelimitedLineTokenizer defaults to comma as its delimiter
        lineMapper.setLineTokenizer(new DelimitedLineTokenizer());
        lineMapper.setFieldSetMapper(new BarclaysTransactionDataFieldSetMapper());

        // .names(new String[] {"number", "txnDate", "account", "value", "subcategory", "description"})
        //.fieldSetMapper(new BarclaysTransactionDataFieldSetMapper())
        FlatFileItemReader<TransactionData> itemReader = new FlatFileItemReaderBuilder<TransactionData>()
                .name("barclaysTxnReader")
                .resource(new FileSystemResource(BARCLAYS_TXN_FILE_PATH))
                .lineMapper(lineMapper)
                .build();

        // Allow no files
        itemReader.setStrict(false);

        // Skip header and white space lines
        itemReader.setLinesToSkip(1);

        return itemReader;
    }

    @Bean
    public TransactionDataItemProcessor barclaysTxnProcessor() {
        return new TransactionDataItemProcessor();
    }

    @Bean
    public JpaItemWriter<Transaction> barclaysTxnWriter() {
        return TransactionParserUtils.transactionWriter(entityManagerFactory);
    }

}