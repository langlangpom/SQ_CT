package com.evian.sqct.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.evian.sqct.util.DES.EvianHelp_DES;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @date   2018年9月28日 下午2:32:23
 * @author XHX
 * @Description 配置druid多数据源
 */
@Configuration
public class DruidDataSourceConfig {
	
	@Autowired  
    private Environment env; 
	
	
	@Bean(name = "primaryDataSource")
	@Primary//配置该数据源为主数据源
    @Qualifier("primaryDataSource")
    public DataSource primaryDataSource(){
        System.out.println("sdfsdfsdf+="+env.getProperty("spring.datasource.url"));

		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setUrl(EvianHelp_DES.decrypt_move(env.getProperty("spring.datasource.url"),EvianHelp_DES.java_net_key,true)); //解密后的连接地址
        dataSource.setUsername(EvianHelp_DES.decrypt_move(env.getProperty("spring.datasource.username"),EvianHelp_DES.java_net_key,true));//解密后的用户名  
        dataSource.setPassword(EvianHelp_DES.decrypt_move(env.getProperty("spring.datasource.password"),EvianHelp_DES.java_net_key,true));//解密后的密码
        dataSource.setDriverClassName(env.getProperty("spring.datasource.driverClassName"));  
        dataSource.setInitialSize(Integer.valueOf(env.getProperty("spring.datasource.initialSize")));  
        dataSource.setMaxActive(Integer.valueOf(env.getProperty("spring.datasource.maxActive")));   
        dataSource.setMinIdle(Integer.valueOf(env.getProperty("spring.datasource.minIdle")));  
        dataSource.setMaxWait(Integer.valueOf(env.getProperty("spring.datasource.maxWait")));    
        dataSource.setValidationQuery("SELECT 1");   
        dataSource.setTestOnBorrow(Boolean.getBoolean(env.getProperty("spring.datasource.testOnBorrow")));  
        dataSource.setTestWhileIdle(Boolean.getBoolean(env.getProperty("spring.datasource.testWhileIdle"))); 
        dataSource.setPoolPreparedStatements(Boolean.getBoolean(env.getProperty("spring.datasource.poolPreparedStatements")));
        return dataSource;
    }
	
	@Bean(name = "vendorDataSource")
	@Qualifier("vendorDataSource")
	public DataSource vendorDataSource(){
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setUrl(EvianHelp_DES.decrypt_move(env.getProperty("spring.datasource.vendor.url"),EvianHelp_DES.java_net_key,true));  //解密后的连接地址
		dataSource.setUsername(EvianHelp_DES.decrypt_move(env.getProperty("spring.datasource.username"),EvianHelp_DES.java_net_key,true));//解密后的用户名  
        dataSource.setPassword(EvianHelp_DES.decrypt_move(env.getProperty("spring.datasource.password"),EvianHelp_DES.java_net_key,true));//解密后的密码  
        dataSource.setDriverClassName(env.getProperty("spring.datasource.driverClassName")); 
        dataSource.setInitialSize(Integer.valueOf(env.getProperty("spring.datasource.initialSize")));  
        dataSource.setMaxActive(Integer.valueOf(env.getProperty("spring.datasource.maxActive")));   
        dataSource.setMinIdle(Integer.valueOf(env.getProperty("spring.datasource.minIdle")));  
        dataSource.setMaxWait(Integer.valueOf(env.getProperty("spring.datasource.maxWait")));    
        dataSource.setValidationQuery("SELECT 1");   
        dataSource.setTestOnBorrow(Boolean.getBoolean(env.getProperty("spring.datasource.testOnBorrow")));  
        dataSource.setTestWhileIdle(Boolean.getBoolean(env.getProperty("spring.datasource.testWhileIdle"))); 
        dataSource.setPoolPreparedStatements(Boolean.getBoolean(env.getProperty("spring.datasource.poolPreparedStatements")));
        return dataSource;
	}
	
	// 使用JDBCTemplate时要区分各个数据源
	@Bean(name="primaryJdbcTemplate")
	public JdbcTemplate  primaryJdbcTemplate(
			@Qualifier("primaryDataSource") DataSource dataSource) {
		
		return new JdbcTemplate(dataSource);
	}
	
	@Bean(name="vendorTemplate")
    public JdbcTemplate  vendorTemplate(
            @Qualifier("vendorDataSource") DataSource dataSource) {

        return new JdbcTemplate(dataSource);
    }




}
