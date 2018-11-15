package com.ice.bshop;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import redis.clients.jedis.JedisCluster;

import javax.sql.DataSource;
import java.io.IOException;

@SpringBootApplication
@ComponentScan
@MapperScan("com.ice.bshop.dao")
public class BShopApplication {

    @Bean("druidDataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource getDataSource(){
        return new DruidDataSource();
    }

    @Bean
    public SqlSessionFactory sqlSessionFactoryBean() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(this.getDataSource());
        sqlSessionFactoryBean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath:/mybatis/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    public PlatformTransactionManager transactionManager(){
        return new DataSourceTransactionManager(this.getDataSource());
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.redis")
    public JedisClusterSource getJedisClusterSource(){
        return new JedisClusterSource();
    }
    @Bean
    public JedisCluster jedisClusterFactory(){
        JedisClusterSource jedisClusterSource = getJedisClusterSource();
        return new JedisCluster(jedisClusterSource.getJedisClusterNodes());
    }

    public static void main(String[] args) {
        SpringApplication.run(BShopApplication.class, args);
    }
}
