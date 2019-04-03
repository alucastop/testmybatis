package fr.hardis.mybatis.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.tomcat.util.file.ConfigurationSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

@Configuration
@MapperScan("fr.hardis.mybatis")
public class MyBatisProjectConf {

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(getDataSource());
        return sessionFactory.getObject();
    }

    @Bean
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(getDataSource());
    }

    /*@ConfigurationProperties(prefix="spring.datasource")
    @Bean
    public DataSource getDataSource() {

        return DataSourceBuilder
                .create()
                .build();
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/mybatisbdd?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Europe/Paris");
        dataSource.setUsername("tony");
        dataSource.setPassword("");
        return dataSource;*//*
    }*/

    @ConfigurationProperties(prefix="spring.datasource")
    @Bean(name = "dataSource", destroyMethod="")
    public DataSource getDataSource() {

        return DataSourceBuilder
                .create()
                .build();

    }

    @Bean
    public DataSourceInitializer dataSourceInitializer() {

        // schema init
        Resource initSchema = new ClassPathResource("scripts/schema-h2.sql");
        Resource initData = new ClassPathResource("scripts/data-h2.sql");
        DatabasePopulator databasePopulator = new ResourceDatabasePopulator(initSchema, initData);
        //DatabasePopulatorUtils.execute(databasePopulator, getDataSource());


        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        resourceDatabasePopulator.addScripts(initSchema, initData);

        DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();

        // the call to the above method
        dataSourceInitializer.setDataSource(getDataSource());


        dataSourceInitializer.setDatabasePopulator(resourceDatabasePopulator);

        return dataSourceInitializer;
    }
}
