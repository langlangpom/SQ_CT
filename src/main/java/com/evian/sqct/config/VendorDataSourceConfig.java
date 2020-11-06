package com.evian.sqct.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.evian.sqct.util.DES.EvianHelp_DES;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * ClassName:VendorDataSourceConfig
 * Package:com.evian.sqct.config
 * Description:驿站数据库 配置druid多数据源
 *
 * @Date:2020/9/30 11:09
 * @Author:XHX
 */
@Configuration
@MapperScan(basePackages="com.evian.sqct.dao.mybatis.vendorDataSource.dao", sqlSessionFactoryRef = "vendorSqlSessionFactory")
public class VendorDataSourceConfig {
    @Autowired
    private Environment env;

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

    @Bean(name = "vendorSqlSessionFactory")
    // @Qualifier表示查找Spring容器中名字为test1DataSource的对象
    public SqlSessionFactory vendorSqlSessionFactory(@Qualifier("vendorDataSource") DataSource datasource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(datasource);
        bean.setMapperLocations(
                // 设置mybatis的xml所在位置
                new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/vendorDataSource/*.xml"));
        return bean.getObject();
    }
    @Bean("vendorSqlSessionTemplate")
    public SqlSessionTemplate vendorsqlsessiontemplate(
            @Qualifier("vendorSqlSessionFactory") SqlSessionFactory sessionfactory) {
        return new SqlSessionTemplate(sessionfactory);
    }

    @Bean(name="vendorTemplate")
    public JdbcTemplate vendorTemplate(
            @Qualifier("vendorDataSource") DataSource dataSource) {

        return new JdbcTemplate(dataSource);
    }

}
