package com.demons.manager.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author : Outro
 * Description : manager加载数据库配置类
 **/
@Configuration
public class ManagerDb {

  private static final Logger logger = LoggerFactory.getLogger(ManagerDb.class);

  @Value("${db.manager.driver-class-name}")
  String driver;
  @Value("${db.manager.url}")
  String url;
  @Value("${db.manager.username}")
  String userName;
  @Value("${db.manager.password}")
  String password;

  @Bean("managerDatasource")
  public DataSource createDatasource() {
    logger.info("manager db info: driver:{} url:{}, userName:{} password:{}", driver, url, userName, password);
    return DataSourceBuilder.create()
        .driverClassName(driver)
        .url(url)
        .username(userName)
        .password(password)
        .build();
  }

  @Primary
  @Bean("managerSqlSessionFactory")
  @DependsOn("managerDatasource")
  public SqlSessionFactory createSqlSessionFactory(DataSource dataSource) throws Exception {
    SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
    factoryBean.setDataSource(dataSource);
    ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
    Resource[] mappers = resourceResolver.getResources("classpath:mappers/*.xml");
    List<Resource> resources = new ArrayList<>(Arrays.asList(mappers));
    factoryBean.setMapperLocations(resources.toArray(new Resource[0]));
    Objects.requireNonNull(factoryBean.getObject()).getConfiguration().setMapUnderscoreToCamelCase(true);
    return factoryBean.getObject();
  }

}
