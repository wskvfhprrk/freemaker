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
        String driver="com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/demo";
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
        System.out.println("获取数据库的版本号——"+metaData.getDriverVersion());
        System.out.println("获取数据库的用户名——"+metaData.getUserName());
        System.out.println("获取数据库连接的URL——"+metaData.getURL());
        System.out.println("获取数据库的驱动名称——"+metaData.getDriverName());
        System.out.println("获取数据库的驱动名称——"+metaData.getDriverName());
        System.out.println("获取数据库的驱动版本号——"+metaData.getDriverVersion());
        System.out.println("查看数据库是否只允许读操作——"+metaData.isReadOnly());
        System.out.println("查看数据库是否支持事务——"+metaData.supportsTransactions());
    }

    /**
     * 获取数据库列表
     */
    @Test
    public void test03() throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet resultSet = metaData.getCatalogs();
        while (resultSet.next()){
            System.out.println(resultSet.getString(1));
        }
        resultSet.close();
        connection.close();
    }

    /**
     * 获取表信息
     * catalog目录——目录名称；必须与存储在数据库中的目录名称匹配,如果连接后面跟了数据库名称，可填写为空；
     *  mysql:数据库名称
     *  orical:数据库名称
     * schemaPattern 检索那些没有目录的； null 表示不应使用目录名称来缩小搜索范围
     *  mysql:null
     *  orical:表示用户名(大写)
     * tableNamePattern - 一种表名模式；必须与存储在数据库类型中的表名匹配 - 表类型列表，必须来自
     * mysql:表名
     * orical:表名
     * getTableTypes 返回的表类型列表，才能包含； null 返回所有类型
     * TABLE：表
     * VIEW：视频
     * @throws SQLException
     */
    @Test
    public void test04() throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet tables = metaData.getTables("demo", null, null, new String[]{"TABLE"});
        while (tables.next()){
            System.out.println(tables.getString("TABLE_NAME"));
            System.out.println(tables.getString("REMARKS"));
        }
        tables.close();
        connection.close();
    }

    /**
     * 获取表中所有数据
     * String catalog, String schemaPattern, String tableNamePattern, String columnNamePattern
     * catalog目录——目录名称；必须与存储在数据库中的目录名称匹配,如果连接后面跟了数据库名称，可填写为空；
     * schemaPattern 检索那些没有目录的； null 表示不应使用目录名称来缩小搜索范围；
     * mysql:null
     * oracle:用户名（大写）
     * tableNamePattern - 一种表名模式；必须与存储在数据库类型中的表名匹配 - 表类型列表，必须来自;
     * columnNamePattern ——列名模式；必须与存储在数据库中的列名匹配
     */
    @Test
    public void test05() throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet columns = metaData.getColumns("demo", null, "dictionary", null);
        while (columns.next()){
            //TABLE_NAME String =>表名
            System.out.println(columns.getString("TABLE_NAME"));
            //COLUMN_NAME字符串=>列名
            System.out.println(columns.getString("COLUMN_NAME"));
            //COLUMN_SIZE int =>列大小
            System.out.println(columns.getString("COLUMN_SIZE"));
            //CHAR_OCTET_LENGTH int =>用于char类型列中最大字节数
            System.out.println(columns.getString("CHAR_OCTET_LENGTH"));
            //IS_NULLABLE字符串=> ISO规则用于确定列的可空性。
            //YES ---如果列可以包含NULL
            //否---如果列不能包含NULL
            //空字符串---如果列的可空性是未知的
            System.out.println(columns.getString("IS_NULLABLE"));
//            System.out.println(columns.getString("SOURCE_DATA_TYPE"));
            //IS_AUTOINCREMENT 字符串=>指示此列是否自动递增
            //YES ---如果列自动递增
            //否---如果列不自动递增
            //空字符串---如果无法确定列是否自动递增
            System.out.println(columns.getString("IS_AUTOINCREMENT"));
        }
        columns.close();
        connection.close();
    }

    /**
     * 表外键
     * @throws SQLException
     */
    @Test
    public void test06() throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet resultSet = metaData.getCatalogs();
        // TODO: 2022/5/3 测试外键
        ResultSet rs = metaData.getImportedKeys("demo", null, "tb_user");
        while (rs.next()){
            System.out.println("导入的主键表名—"+rs.getString("FKCOLUMN_NAME"));
            System.out.println("导入的主键表名—"+rs.getString("PKTABLE_NAME"));
            System.out.println("导入主键列名称-"+rs.getString("PKCOLUMN_NAME"));
//            ResultSetMetaData rsMetaData = rs.getMetaData();
//            int columnCount = rsMetaData.getColumnCount();
//            System.out.println(columnCount);

        }
        resultSet.close();
        connection.close();
    }
}
