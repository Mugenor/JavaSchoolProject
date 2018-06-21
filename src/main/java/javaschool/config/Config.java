package javaschool.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
//@EnableWebMvc
@ComponentScan("javaschool")
@PropertySource("classpath:application.properties")
public class Config {
    private static final String DRIVER_CLASS_NAME = "db.driver";
    private static final String DATABASE_URL = "db.url";
    private static final String DATABASE_USERNAME = "db.username";
    private static final String DATABASE_PASSWORD = "db.password";
    private static final String HIBERNATE_DIALECT = "hibernate.dialect";
    private static final String HIBERNATE_SHOW_SQL = "hibernate.show_sql";
    private static final String ENTITY_PACKAGES_TO_SCAN = "entity.package";

    @Resource
    private Environment env;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getRequiredProperty(DRIVER_CLASS_NAME));
        dataSource.setUrl(env.getRequiredProperty(DATABASE_URL));
        dataSource.setUsername(env.getRequiredProperty(DATABASE_USERNAME));
        dataSource.setPassword(env.getRequiredProperty(DATABASE_PASSWORD));

        return dataSource;
    }

    //    @Bean
//    public LocalSessionFactoryBean sessionFactory() {
//        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
//
//        sessionFactory.setHibernateProperties(hibernateProperties());
//        sessionFactory.setDataSource(dataSource());
//        sessionFactory.setPackagesToScan(env.getRequiredProperty(ENTITY_PACKAGES_TO_SCAN));
//
//        return sessionFactory;
//    }
//@Bean
//public HibernateTransactionManager transactionManager() {
//    HibernateTransactionManager transactionManager = new HibernateTransactionManager();
//    transactionManager.setSessionFactory(sessionFactory().getObject());
//
//    return transactionManager;
//}
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setPackagesToScan(env.getRequiredProperty(ENTITY_PACKAGES_TO_SCAN));

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        entityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);
        entityManagerFactoryBean.setJpaProperties(hibernateProperties());

        return entityManagerFactoryBean;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);

        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }


    private Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.put(HIBERNATE_DIALECT, env.getRequiredProperty(HIBERNATE_DIALECT));
        hibernateProperties.put(HIBERNATE_SHOW_SQL, env.getRequiredProperty(HIBERNATE_SHOW_SQL));
        hibernateProperties.put("hibernate.transaction.flush_before_completion", "true");
        hibernateProperties.put("hibernate.hbm2ddl.auto", "create");

        return hibernateProperties;
    }
}
