package org.poem.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.Arrays;

/**
 * @author 曹莉
 */
@Configuration
@MapperScan(basePackages = DataSourceConfig.PACKAGE, sqlSessionFactoryRef = "SqlSessionFactory")
public class DataSourceConfig implements EnvironmentAware {
    static final String PACKAGE = "org.poem.dao";

    /**
     * 日志管理
     */
    private final Logger log = LoggerFactory.getLogger(DataSourceConfig.class);

    /**
     * 环境变量
     */
    private Environment env;
    private String driveClassName;
    private String url;
    private String userName;
    private String password;
    private String maximumPoolSize;
    private String dataSourceClassName;

    /**
     * 环境变量
     * @param env
     */
    @Override
    public void setEnvironment(Environment env) {
        this.env = env;
        this.url = env.getProperty("spring.datasource.url");
        this.userName= env.getProperty("spring.datasource.username");
        this.password = env.getProperty("spring.datasource.password");
        this.driveClassName = env.getProperty("spring.datasource.driver-class-name");
        this.maximumPoolSize = env.getProperty("spring.datasource.hikari.maximum-pool-size");
    }

    /**
     * 数据库配置
     * @return
     */
    @Bean(destroyMethod = "shutdown")
    public DataSource dataSource() {
        log.debug("Configuring Datasource");
        if ( url  == null &&  driveClassName == null) {
            log.error("Your database connection pool configuration is incorrect! The application cannot start. Please check your Spring profile, current profiles are: {}",
                    Arrays.toString(env.getActiveProfiles()));

            throw new ApplicationContextException("Database connection pool is not configured correctly");
        }
        HikariConfig config = new HikariConfig();
        config.setPassword(password);
        config.setUsername(userName);
        config.setJdbcUrl(url);
        config.setDataSourceClassName(dataSourceClassName);
        config.setMaximumPoolSize(Integer.parseInt(maximumPoolSize));
        return new HikariDataSource(config);
    }


    @Bean(name = "TransactionManager")
    @Primary
    public DataSourceTransactionManager dbTransactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean(name = "SqlSessionFactory")
    @Primary
    public SqlSessionFactory dbSqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        return sessionFactory.getObject();
    }
}