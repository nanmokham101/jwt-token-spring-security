package com.mokham.security.dbconfig;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@Log4j2
public class DB3Config {

    private final Environment env;

    @Autowired
    public DB3Config(Environment env) {
        this.env = env;
    }

    @Primary
    @Bean(name = "orderDataSource")
    public DataSource dataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setUrl(env.getProperty("spring.order.datasource.url"));
        ds.setUsername(env.getProperty("spring.order.datasource.username"));
        ds.setPassword(env.getProperty("spring.order.datasource.password"));
        ds.setDriverClassName(env.getProperty("spring.order.datasource.driver-class-name"));
        return ds;
    }

    @Primary
    @Bean(name = "orderJdbcTemplate")
    public JdbcTemplate jdbcTemplate(@Qualifier("orderDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
