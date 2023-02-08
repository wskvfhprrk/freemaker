package com.hejz.generate.utils;

import com.hejz.generate.entity.Column;
import com.hejz.generate.entity.Database;
import com.hejz.generate.entity.ImportedKey;
import com.hejz.generate.entity.Table;

import java.sql.*;
import java.util.*;

public class DataBaseUtils {
    //获取数据库连接
    public static Connection getConnection(Database db) throws Exception {
        java.util.Properties info = new Properties();
        info.setProperty("user", db.getUserName());
        info.setProperty("password", db.getPassword());
        info.setProperty("useInformationSchema", "true");//设置可以获取tables remarks信息,不设置获取不到remarks
        //获取连接
        Class.forName(db.getDriver());//注册驱动
        Connection connection = DriverManager.getConnection(db.getUrl(), info);
        return connection;
    }

    //获取数据库列表
    public static List<String> getShemas(Database db) throws Exception {
        Connection connection = getConnection(db);
        //获取元数据
        DatabaseMetaData metaData = connection.getMetaData();
        //获取所有数据库列表
        ResultSet resultSet = metaData.getCatalogs();
        List<String> list = new ArrayList<>();
        while (resultSet.next()) {
            list.add(resultSet.getString(1));
        }
        resultSet.close();
        connection.close();
        return list;
    }

    /**
     * 获取数据库中的表和字段构造实体类
     * Table对象
     * 1.参数
     * DataBase数据库对象
     * 2.操作步骤
     * 1.获取连接
     * 2.获取databasemetaData
     * 3.获取当前数据库中的所有表
     * 4.获取每个表中的所有字段
     * 5.封装到java对象中即可
     *
     * @param database
     * @return
     * @throws Exception
     */
    public static List<Table> getDbInfo(Database database) throws Exception {
        //获取连接
        Connection connection = getConnection(database);
        //获取元数据
        DatabaseMetaData metaData = connection.getMetaData();
        List<Table> list = new ArrayList<>();
        //获取当前数据库的所有表
        ResultSet tables = metaData.getTables(database.getDb(), null, null, new String[]{"TABLE"});
        while (tables.next()) {
            //表名
            String table_name = tables.getString("TABLE_NAME");
            //构造生成对应实体类的类名
            String className = removePrefix(table_name);
            //主键
            ResultSet primaryKeys = metaData.getPrimaryKeys(null, null, table_name);
            //备注说明
            String remarks = tables.getString("REMARKS");
            if(remarks==null||remarks.length()==0)continue;
            //对主键遍历的原因（或许一张表有多个主键）
            String keys = "";
            while (primaryKeys.next()) {
                String keyName = primaryKeys.getString("COLUMN_NAME");
                keys += keyName + ",";
            }
            // TODO: 2022/5/6  查询UNIQUE唯一索引
            Table tab = new Table();
            tab.setName(table_name);
            tab.setClassName(className);
            tab.setTableComment(remarks);
            tab.setKey(keys);
            //处理外键
            ResultSet importedKeys = metaData.getImportedKeys(database.getDb(), null, tab.getName());
            List<ImportedKey> listPk = new ArrayList<>();
            while (importedKeys.next()) {
                ImportedKey importedKey = new ImportedKey();
                importedKey.setFkColumnName(importedKeys.getString("FKCOLUMN_NAME"));
                String pktableName = importedKeys.getString("PKTABLE_NAME");
                importedKey.setPkTableName(pktableName);
                //去掉前缀名
                importedKey.setPkJavaBeanName(removePrefix(pktableName));
                importedKey.setPkColumnName(importedKeys.getString("PKCOLUMN_NAME"));
                listPk.add(importedKey);
            }
            //处理表中的所有字段——与视频不同，视频从元数据拿东西，此处从元数据拿备注信息，其它从结果集中取得
            //查询参数个数
            //元数据不精确，但能查出注释放进map
            Map columnMap=new HashMap();//把备注信息放进map字段
            ResultSet columns = metaData.getColumns(database.getDb(), null, table_name, null);
            while (columns.next()) {
                //不需要清理，新的会把旧的给覆盖
                columnMap.put(columns.getString("COLUMN_NAME"),columns.getString("REMARKS"));
            }
            //主要使用结果集来处理列参数——比较精确一点，与元数据的备注信息合并
            String sql = "select * from " + database.getDb() + "." + table_name;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            //查询
            ResultSet resultSet = preparedStatement.executeQuery();
            //结果集原数据
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int count = resultSetMetaData.getColumnCount();
            List<Column> cols = new ArrayList<>();
            for (int i = 1; i < count + 1; i++) {
                Column column = new Column();
                column.setColumnName(resultSetMetaData.getColumnName(i));
                column.setColumnComment(columnMap.get(resultSetMetaData.getColumnName(i)).toString());
                column.setColumnDbType(resultSetMetaData.getColumnTypeName(i));
                column.setJavaBeanName(StringUtils.javaBeanName(column.getColumnName()));
                column.setIsAutoincrement(resultSetMetaData.isAutoIncrement(i));
                Boolean columnKey = false;
                for (String s : keys.split(",")) {
                    if (column.getColumnName().equals(s)) {
                        columnKey = true;
                        break;
                    }
                }
                column.setIsKey(columnKey);
                //是要配置里包含了字段就可以
                String javaType = null;
                for (String s : column.getColumnDbType().split(" ")) {
                    if (StringUtils.isNotBlank(PropertiesUtils.customMap.get(s))) {
                        javaType = PropertiesUtils.customMap.get(s);
                        break;
                    }
                }
                if (javaType == null) {
                    throw new Exception(column.getColumnDbType() + "数据库类型无法找到对应的java类型,请在配置文件中添加");
                }
                column.setColumnJavaType(javaType);
                column.setIsNullable(resultSetMetaData.isNullable(i));
                column.setColumnDisplaySize(resultSetMetaData.getColumnDisplaySize(i));
                //判断是不是外键字段
                if(!listPk.isEmpty()){
                    listPk.stream().forEach(pk->{
                        if(pk.getFkColumnName().equals(column.getColumnName())){
                            column.setIsImportedKey(true);
                            column.setFkColumnName(pk.getFkColumnName());
                            column.setPkJavaBeanName(pk.getPkJavaBeanName());
                            column.setPkTableName(pk.getPkTableName());
                            column.setPkColumnName(pk.getPkColumnName());
                        }
                    });
                }
                cols.add(column);
            }
            //先把列加入表中
            tab.setColumns(cols);
            list.add(tab);
            //关闭连接，释放资源
            columns.close();
            primaryKeys.close();
        }
        tables.close();
        connection.close();
        return list;

    }

    //根据表名的截取操作生成类名
    public static String removePrefix(String tableName) {
        //从自定义的配置文件中拿到前缀的配置
        String prefixes = PropertiesUtils.customMap.get("tableRemovePrefixes");
        //截取前缀
        for (String prefix : prefixes.split(",")) {
            tableName = tableName.replace(prefix, "");
        }
        //首字母大写
        String className = StringUtils.makeA11WordFirstLetterUpperCase(tableName);
        return className;
    }

    // 测试 获取数据库中的所有表和字段并构造实体类 的方法是否可用
    public static void main(String[] args) throws Exception {
        Database db = new Database("MYSQL", "mall");
        db.setUserName("root");
        db.setPassword("123456");
        List<Table> dbInfo = DataBaseUtils.getDbInfo(db);
        for (Table table : dbInfo) {
            System.out.println(table);
        }
    }
}