package com.example.budgetapp26.batch;

import com.example.budgetapp26.batch.amex2026.Amex2026TransactionParser;
import com.example.budgetapp26.batch.barclays.BarclaysTransactionParser;
import com.example.budgetapp26.batch.hsbc.HsbcTransactionParser;
import com.example.budgetapp26.batch.tsb.TsbTransactionParser;
import com.example.budgetapp26.datasource.DataSourceConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.EnableJdbcJobRepository;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.parameters.RunIdIncrementer;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.budgetapp26.batch.amazon.AmazonTransactionParser;
import com.example.budgetapp26.batch.amex.AmexTransactionParser;
import com.example.budgetapp26.batch.chart.BarChartCreatorTasklet;
import com.example.budgetapp26.batch.chart.PieChartCreatorTasklet;
import com.example.budgetapp26.batch.nationwide.NationwideTransactionParser;
import com.example.budgetapp26.batch.natwest.NatwestTransactionParser;
import com.example.budgetapp26.batch.tesco.TescoTransactionParser;
import com.example.budgetapp26.entities.Transaction;
import org.springframework.context.annotation.Import;
import org.springframework.orm.jpa.JpaTransactionManager;


@Configuration
@EnableBatchProcessing
@EnableJdbcJobRepository
@Import(DataSourceConfiguration.class)
public class TransactionProcessorJob {

    public static final Logger log = LoggerFactory.getLogger(TransactionProcessorJob.class);

    @Autowired
    private AmazonTransactionParser amazonTransactionParser;

    @Autowired
    private NatwestTransactionParser natwestTransactionParser;

    @Autowired
    private AmexTransactionParser amexTransactionParser;

    @Autowired
    private Amex2026TransactionParser amex2026TransactionParser;

    @Autowired
    private TsbTransactionParser tsbTransactionParser;

    @Autowired
    private BarclaysTransactionParser barclaysTransactionParser;

    @Autowired
    private HsbcTransactionParser hsbcTransactionParser;

    @Autowired
    private NationwideTransactionParser nationwideTransactionParser;

    @Autowired
    private TescoTransactionParser tescoTransactionParser;

    @Autowired
    private TransactionEntityToCsvPrinter txnEntityToCsvPrinter;

    @Autowired
    private BarChartCreatorTasklet barChartCreatorTasklet;

    @Autowired
    private PieChartCreatorTasklet expensesPieChartTasklet;

    @Bean
    public Job importTxnJob(JobRepository jobRepository,
                            @Qualifier("amazonTxnImportStep") Step amazonTxnImportStep,
                            @Qualifier("natwestTxnImportStep") Step natwestTxnsImportStep,
                            @Qualifier("amexTxnImportStep") Step amexTxnImportStep,
                            @Qualifier("nationwideTxnImportStep") Step nationwideTxnImportStep,
                            @Qualifier("tescoTxnImportStep") Step tescoTxnImportStep,
                            @Qualifier("amex2026TxnImportStep") Step amex2026TxnImportStep,
                            @Qualifier("barclaysTxnImportStep") Step barclaysTxnImportStep,
                            @Qualifier("hsbcTxnImportStep") Step hsbcTxnImportStep,
                            @Qualifier("tsbTxnImportStep") Step tsbTxnImportStep,
                            @Qualifier("printTxnsStep") Step printTxnsStep,
                            @Qualifier("createIncomeExpensesChart") Step createIncomeExpensesChart,
                            @Qualifier("createExpensesPieChart") Step createExpensesPieChart) {
        return new JobBuilder("processTxnsJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(amazonTxnImportStep)
                .next(natwestTxnsImportStep)
                .next(amexTxnImportStep)
                .next(nationwideTxnImportStep)
                .next(tescoTxnImportStep)
                .next(amex2026TxnImportStep)
                .next(barclaysTxnImportStep)
                .next(hsbcTxnImportStep)
                .next(tsbTxnImportStep)
                .next(printTxnsStep)
                .next(createIncomeExpensesChart)
                .next(createExpensesPieChart)
                .build();
    }

    @Bean
    public Step amazonTxnImportStep(JobRepository jobRepository, JpaTransactionManager transactionManager) {
        return new StepBuilder("amazonTxnImportStep", jobRepository)
                .<TransactionData,Transaction> chunk(100)
                .transactionManager(transactionManager)
                .reader(amazonTransactionParser.amazonTxnReader())
                .processor(amazonTransactionParser.amazonTxnProcessor())
                .writer(amazonTransactionParser.amazonTxnWriter())
                .build();
    }

    @Bean
    public Step natwestTxnImportStep(JobRepository jobRepository, JpaTransactionManager transactionManager) {
        return new StepBuilder("natwestTxnImportStep", jobRepository)
                .<TransactionData,Transaction> chunk(100)
                .transactionManager(transactionManager)
                .reader(natwestTransactionParser.nwTxnReader())
                .processor(natwestTransactionParser.nwTxnProcessor())
                .writer(natwestTransactionParser.nwTxnWriter())
                .build();
    }

    @Bean
    public Step amexTxnImportStep(JobRepository jobRepository, JpaTransactionManager transactionManager) {
        return new StepBuilder("amexTxnImportStep", jobRepository)
                .<TransactionData,Transaction> chunk(100)
                .transactionManager(transactionManager)
                .reader(amexTransactionParser.amexTxnReader())
                .processor(amexTransactionParser.amexTxnProcessor())
                .writer(amexTransactionParser.amexTxnWriter())
                .build();
    }

    @Bean
    public Step amex2026TxnImportStep(JobRepository jobRepository, JpaTransactionManager transactionManager) {
        return new StepBuilder("amex2026TxnImportStep", jobRepository)
                .<TransactionData,Transaction> chunk(100)
                .transactionManager(transactionManager)
                .reader(amex2026TransactionParser.amex2026TxnReader())
                .processor(amex2026TransactionParser.amex2026TxnProcessor())
                .writer(amex2026TransactionParser.amex2026TxnWriter())
                .build();
    }

    @Bean
    public Step tsbTxnImportStep(JobRepository jobRepository, JpaTransactionManager transactionManager) {
        return new StepBuilder("tsbTxnImportStep", jobRepository)
                .<TransactionData,Transaction> chunk(100)
                .transactionManager(transactionManager)
                .reader(tsbTransactionParser.tsbTxnReader())
                .processor(tsbTransactionParser.tsbTxnProcessor())
                .writer(tsbTransactionParser.tsbTxnWriter())
                .build();
    }

    @Bean
    public Step barclaysTxnImportStep(JobRepository jobRepository, JpaTransactionManager transactionManager) {
        return new StepBuilder("barclaysTxnImportStep", jobRepository)
                .<TransactionData,Transaction> chunk(100)
                .transactionManager(transactionManager)
                .reader(barclaysTransactionParser.barclaysTxnReader())
                .processor(barclaysTransactionParser.barclaysTxnProcessor())
                .writer(barclaysTransactionParser.barclaysTxnWriter())
                .build();
    }

    @Bean
    public Step hsbcTxnImportStep(JobRepository jobRepository, JpaTransactionManager transactionManager) {
        return new StepBuilder("hsbcTxnImportStep", jobRepository)
                .<TransactionData,Transaction> chunk(100)
                .transactionManager(transactionManager)
                .reader(hsbcTransactionParser.hsbcTxnReader())
                .processor(hsbcTransactionParser.hsbcTxnProcessor())
                .writer(hsbcTransactionParser.hsbcTxnWriter())
                .build();
    }

    @Bean
    public Step nationwideTxnImportStep(JobRepository jobRepository, JpaTransactionManager transactionManager) {
        log.debug("inside nationwideTxnImportStep");
        return new StepBuilder("nationwideTxnImportStep", jobRepository)
                .<TransactionData,Transaction> chunk(100)
                .transactionManager(transactionManager)
                .reader(nationwideTransactionParser.nationwideTxnReader())
                .processor(nationwideTransactionParser.nationwideTxnProcessor())
                .writer(nationwideTransactionParser.nationwideTxnWriter())
                .build();
    }

    @Bean
    public Step tescoTxnImportStep(JobRepository jobRepository, JpaTransactionManager transactionManager) {
        return new StepBuilder("tescoTxnImportStep", jobRepository)
                .<TransactionData,Transaction> chunk(100)
                .transactionManager(transactionManager)
                .reader(tescoTransactionParser.tescoTxnReader())
                .processor(tescoTransactionParser.tescoTxnProcessor())
                .writer(tescoTransactionParser.tescoTxnWriter())
                .build();
    }

    @Bean
    public Step printTxnsStep(JobRepository jobRepository, JpaTransactionManager transactionManager) {
        return new StepBuilder("printTxnsStep", jobRepository)
                .<Transaction, Transaction> chunk(100)
                .transactionManager(transactionManager)
                .reader(txnEntityToCsvPrinter.reader())
                .processor(txnEntityToCsvPrinter.processor())
                .writer(txnEntityToCsvPrinter.writer())
                .build();
    }

    @Bean
    public Step createIncomeExpensesChart(JobRepository jobRepository) {
        return new StepBuilder("createIncomeExpensesChart", jobRepository)
                .tasklet(barChartCreatorTasklet)
                .build();
    }

    @Bean
    public Step createExpensesPieChart(JobRepository jobRepository) {
        return new StepBuilder("createExpensesPieChart", jobRepository)
                .tasklet(expensesPieChartTasklet)
                .build();
    }

}