package com.hejz.freemaker;

import org.junit.Before;
import org.junit.Test;

import java.sql.*;
import java.util.Properties;

/**
 * 元数据测试类
 */
public class DatabaseMetaDatetest {
    private  Connection connection;
    @Before
    public void inti() throws Exception {
        //1、获取数据库连接
        //流程驱动
        String driver="com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306";
        String username="root";
        String password="123456";
        Properties properties=new Properties();
        properties.setProperty("remarks" ,"true");//remarks
        properties.setProperty("useInformationSchema", "true");//设置可以获取tables remarks信息
        properties.setProperty("user",username);
        properties.setProperty("password",password);
        Class.forName(driver);//获取数据驱动
        connection = DriverManager.getConnection(url, properties);
    }
    /**
     * 测试获取数据库基本信息
     */
    @Test
    public void test01() throws Exception {
        //2、获取元数据
        DatabaseMetaData metaData = connection.getMetaData();
        //3、获取数据库基本信息
        //获取数据库名称
        System.out.println("获取数据库的产品名称——"+metaData.getDatabaseProductName());
        System.out.println("获取数据库的版本号——"+metaData.getDatabaseProductName());
        System.out.println("获取数据库的用户名——"+metaData.getUserName());
        System.out.println("获取数据库连接的URL——"+metaData.getURL());
        System.out.println("获取数据库的驱动名称——"+metaData.getDriverName());
        System.out.println("获取数据库的驱动名称——"+metaData.getDriverName());
        System.out.println("获取数据库的驱动版本号——"+metaData.getDriverVersion());
        System.out.println("查看数据库是否只允许读操作——"+metaData.isReadOnly());
        System.out.println("查看数据库是否支持事务——"+metaData.supportsTransactions());
        /**
         * catalog目录——目录名称；必须与存储在数据库中的目录名称匹配；
         * schemaPattern 检索那些没有目录的； null 表示不应使用目录名称来缩小搜索范围
         * schemaPattern - 一种模式名称模式；必须与存储在数据库中的模式名称匹配；检索那些没有模式的； null 表示不应使用模式名称来缩小搜索范围
         * tableNamePattern - 一种表名模式；必须与存储在数据库类型中的表名匹配 - 表类型列表，必须来自 getTableTypes 返回的表类型列表，才能包含； null 返回所有类型
         */
    }
}
