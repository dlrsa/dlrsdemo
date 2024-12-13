//package com.dlrs.dlrsdemo.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;
//
//@Configuration
//public class AppConfig {
//
//    @Bean
//    public JdbcTemplate jdbcTemplate() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName("org.postgresql.Driver");
//        dataSource.setUrl("jdbc:postgresql://localhost:5433/dlrsDB");
//        dataSource.setUsername("postgres");
//        dataSource.setPassword("gaurav");
//
//        return new JdbcTemplate(dataSource);
//    }
//}
