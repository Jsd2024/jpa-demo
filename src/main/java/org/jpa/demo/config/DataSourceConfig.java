package org.jpa.demo.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    // DataSource for Database 1 (db1)
    @Bean(name = "dataSource1")
    @ConfigurationProperties(prefix = "spring.datasource.db1")
    public DataSource dataSource1() {
        return DataSourceBuilder.create().build();
    }

    // DataSource for Database 2 (db2)
    @Bean(name = "dataSource2")
    @ConfigurationProperties(prefix = "spring.datasource.db2")
    public DataSource dataSource2() {
        return DataSourceBuilder.create().build();
    }

    // JdbcTemplate for Database 1 (db1)
    @Bean(name = "jdbcTemplate1")
    public JdbcTemplate jdbcTemplate1(@Qualifier("dataSource1") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    // JdbcTemplate for Database 2 (db2)
    @Bean(name = "jdbcTemplate2")
    public JdbcTemplate jdbcTemplate2(@Qualifier("dataSource2") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}




//@Configuration
//public class DataSourceConfig {
//
//    @Bean(name = "dataSource1")
//    @Primary
//    @ConfigurationProperties(prefix = "spring.datasource.db1")
//    public DataSource dataSource1() {
//        return DataSourceBuilder.create().build();
//    }
//
//    @Bean(name = "dataSource2")
//    @ConfigurationProperties(prefix = "spring.datasource.db2")
//    public DataSource dataSource2() {
//        return DataSourceBuilder.create().build();
//    }
//
//    @Bean(name = "jdbcTemplate1")
//    public JdbcTemplate jdbcTemplate1(@Qualifier("dataSource1") DataSource dataSource) {
//        return new JdbcTemplate(dataSource);
//    }
//
//    @Bean(name = "jdbcTemplate2")
//    public JdbcTemplate jdbcTemplate2(@Qualifier("dataSource2") DataSource dataSource) {
//        return new JdbcTemplate(dataSource);
//    }
//
//    @Bean(name = "transactionManager1")
//    public DataSourceTransactionManager transactionManager1(@Qualifier("dataSource1") DataSource dataSource) {
//        return new DataSourceTransactionManager(dataSource);
//    }
//
//    @Bean(name = "transactionManager2")
//    public DataSourceTransactionManager transactionManager2(@Qualifier("dataSource2") DataSource dataSource) {
//        return new DataSourceTransactionManager(dataSource);
//    }
//
//}
