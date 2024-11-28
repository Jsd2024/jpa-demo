package org.jpa.demo.config;



import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class DataSourceConfig {

    // DataSource for Database 1 (db1)
    @Bean(name = "dataSource1")
    @Primary
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

    @Primary
    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(dataSource1());
        factory.setPackagesToScan("org.jpa.demo"); // Replace with your package
        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factory.setJpaProperties(getJpaProperties());
        return factory;
    }

    @Bean(name = "entityDb2ManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityDb2ManagerFactory() {
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(dataSource2());
        factory.setPackagesToScan("org.jpa.demo"); // Replace with your package
        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factory.setJpaProperties(getJpaProperties());
        return factory;
    }

    @Bean(name = "transactionManager")
    public JpaTransactionManager transactionManager(@Qualifier("entityManagerFactory") LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory.getObject());
        return transactionManager;
    }

    @Bean(name = "transactionManager2")
    public JpaTransactionManager transactionManager2(@Qualifier("entityDb2ManagerFactory") LocalContainerEntityManagerFactoryBean entityDb2ManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityDb2ManagerFactory.getObject());
        return transactionManager;
    }

    private Properties getJpaProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.show_sql", "true");
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        return properties;
    }
}



//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;
//
//import javax.sql.DataSource;
//
//@Configuration
//public class DataSourceConfig {
//
//    @Bean(name = "db1DataSource")
//    @ConfigurationProperties(prefix = "spring.datasource.db1")
//    public DataSource db1DataSource() {
//        return new DriverManagerDataSource();
//    }
//
//    @Bean(name = "db2DataSource")
//    @ConfigurationProperties(prefix = "spring.datasource.db2")
//    public DataSource db2DataSource() {
//        return new DriverManagerDataSource();
//    }
//
//    @Bean(name = "jdbcTemplate1")
//    @Primary
//    @ConfigurationProperties(prefix = "spring.datasource.db1")
//    public JdbcTemplate jdbcTemplate1(DataSource db1DataSource) {
//        return new JdbcTemplate(db1DataSource);
//    }
//
//    @Bean(name = "jdbcTemplate2")
//    @ConfigurationProperties(prefix = "spring.datasource.db2")
//    public JdbcTemplate jdbcTemplate2(DataSource db2DataSource) {
//        return new JdbcTemplate(db2DataSource);
//    }
//}


//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;
//
//import javax.sql.DataSource;
//
//@Configuration
//public class DataSourceConfig {
//
//    @Bean(name = "db1DataSource")
//    @ConfigurationProperties(prefix = "spring.datasource.db1")
//    public DataSource db1DataSource() {
//        return new DriverManagerDataSource();
//    }
//
//    @Bean(name = "db2DataSource")
//    @ConfigurationProperties(prefix = "spring.datasource.db2")
//    public DataSource db2DataSource() {
//        return new DriverManagerDataSource();
//    }
//
//    @Bean(name = "jdbcTemplate1")
//    public JdbcTemplate jdbcTemplate1(@Qualifier("db1DataSource") DataSource db1DataSource) {
//        return new JdbcTemplate(db1DataSource);
//    }
//
//    @Bean(name = "jdbcTemplate2")
//    public JdbcTemplate jdbcTemplate2(@Qualifier("db2DataSource") DataSource db2DataSource) {
//        return new JdbcTemplate(db2DataSource);
//    }
//}


//package org.jpa.demo.config;
//
//import jakarta.annotation.PostConstruct;

//import org.jpa.demo.domain.User;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
//
//import javax.sql.DataSource;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Properties;
//
//@Configuration
//public class DataSourceConfig {
//
//
//    // DataSource for Database 1 (db1)
//    @Bean(name = "dataSource1")
//    @Primary
//    @ConfigurationProperties(prefix = "spring.datasource.db1")
//    public DataSource dataSource1() {
//        return DataSourceBuilder.create().build();
//    }
//
//    // DataSource for Database 2 (db2)
//    @Bean(name = "dataSource2")
//    @ConfigurationProperties(prefix = "spring.datasource.db2")
//    public DataSource dataSource2() {
//        return DataSourceBuilder.create().build();
//    }
//
//    // JdbcTemplate for Database 1 (db1)
//    @Bean(name = "jdbcTemplate1")
//    public JdbcTemplate jdbcTemplate1(@Qualifier("dataSource1") DataSource dataSource) {
//        return new JdbcTemplate(dataSource);
//    }
//
//    // JdbcTemplate for Database 2 (db2)
//    @Bean(name = "jdbcTemplate2")
//    public JdbcTemplate jdbcTemplate2(@Qualifier("dataSource2") DataSource dataSource) {
//        return new JdbcTemplate(dataSource);
//    }
//
//    @Bean(name = "entityDb1ManagerFactory")
//    @Primary
//    public LocalContainerEntityManagerFactoryBean entityDb1ManagerFactory() {
//        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
//        factory.setDataSource(dataSource1());
//        factory.setPackagesToScan("org.jpa.demo");
//        // Replace with your package
//        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
//        factory.setJpaProperties(getJpaProperties());
//        return factory;
//    }
//
//    @Bean(name = "entityDb2ManagerFactory")
//    public LocalContainerEntityManagerFactoryBean entityDb2ManagerFactory() {
//        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
//        factory.setDataSource(dataSource2());
//        factory.setPackagesToScan("org.jpa.demo");
//        // Replace with your package
//        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
//        factory.setJpaProperties(getJpaProperties());
//        return factory;
//    }
//
//    private Properties getJpaProperties() {
//        Properties properties = new Properties();
//        properties.setProperty("hibernate.show_sql", "true");
//        properties.setProperty("hibernate.hbm2ddl.auto", "update");
//        return properties;
//    }
//
//    @Bean(name = "transactionManager")
//    public JpaTransactionManager transactionManager(@Qualifier("entityManagerFactory") LocalContainerEntityManagerFactoryBean entityManagerFactory) {
//        JpaTransactionManager transactionManager = new JpaTransactionManager();
//        transactionManager.setEntityManagerFactory(entityManagerFactory.getObject());
//        return transactionManager;
//    }
//
//    @Bean(name = "transactionManager2")
//    public JpaTransactionManager transactionManager2(@Qualifier("entityDb2ManagerFactory") LocalContainerEntityManagerFactoryBean entityDb2ManagerFactory) {
//        JpaTransactionManager transactionManager = new JpaTransactionManager();
//        transactionManager.setEntityManagerFactory(entityDb2ManagerFactory.getObject());
//        return transactionManager;
//    }
//
//}
//
//
//
//
////@Configuration
////public class DataSourceConfig {
////
////    @Bean(name = "dataSource1")
////    @Primary
////    @ConfigurationProperties(prefix = "spring.datasource.db1")
////    public DataSource dataSource1() {
////        return DataSourceBuilder.create().build();
////    }
////
////    @Bean(name = "dataSource2")
////    @ConfigurationProperties(prefix = "spring.datasource.db2")
////    public DataSource dataSource2() {
////        return DataSourceBuilder.create().build();
////    }
////
////    @Bean(name = "jdbcTemplate1")
////    public JdbcTemplate jdbcTemplate1(@Qualifier("dataSource1") DataSource dataSource) {
////        return new JdbcTemplate(dataSource);
////    }
////
////    @Bean(name = "jdbcTemplate2")
////    public JdbcTemplate jdbcTemplate2(@Qualifier("dataSource2") DataSource dataSource) {
////        return new JdbcTemplate(dataSource);
////    }
////
////    @Bean(name = "transactionManager1")
////    public DataSourceTransactionManager transactionManager1(@Qualifier("dataSource1") DataSource dataSource) {
////        return new DataSourceTransactionManager(dataSource);
////    }
////
////    @Bean(name = "transactionManager2")
////    public DataSourceTransactionManager transactionManager2(@Qualifier("dataSource2") DataSource dataSource) {
////        return new DataSourceTransactionManager(dataSource);
////    }
////
////}
