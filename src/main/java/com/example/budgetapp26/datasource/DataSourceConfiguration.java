package com.example.budgetapp26.datasource;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfiguration {

    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder().addScript("/org/springframework/batch/core/schema-drop-hsqldb.sql")
                .addScript("/org/springframework/batch/core/schema-hsqldb.sql")
                .generateUniqueName(true)
                .build();
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean
    public TransactionTemplate transactionTemplate(JpaTransactionManager transactionManager) {
        return new TransactionTemplate(transactionManager);
    }

}
