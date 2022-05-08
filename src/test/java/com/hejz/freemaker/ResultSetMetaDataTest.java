package com.hejz.freemaker;

import org.junit.Before;
import org.junit.Test;

import java.sql.*;
import java.util.Properties;

/**
 * 结果集原数据
 */
public class ResultSetMetaDataTest {
    private Connection connection;
    @Before
    public void inti() throws Exception {
        //1、获取数据库连接
        //流程驱动
        String driver="com.mysql.cj.jdbc.Driver";
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
    @Test
    public void test() throws SQLException {
        String sql="select * from demo.${className?uncap_first}";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        //查询
        ResultSet resultSet = preparedStatement.executeQuery();
        //结果集原数据
        ResultSetMetaData metaData = resultSet.getMetaData();
        //查询参数个数
        int count = metaData.getColumnCount();
        for (int i = 1; i < count+1; i++) {
            System.out.println(metaData.isNullable(i)+"----"+metaData.getColumnName(i)+"-----"+metaData.getColumnTypeName(i)+"-----"+metaData.getColumnDisplaySize(i));
        }

    }

}
