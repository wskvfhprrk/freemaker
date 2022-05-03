package com.hejz.generate.entity;

import lombok.Data;

/**
 * 数据库配置类
 */
@Data
public class Database {
    private static String mysqlUrl="jdbc:mysql://[ip]:[port]/[db]?useUnicode=true&characterEncoding=utf-8&serverTimezone=UTC";
    private static String oracleUrl="jdbc:oracle:thin:@[ip]:[port]:[db]";

    private String dbType;//数据库类型
    private String userName;
    private String password;
    private String driver;
    private String url;

    public Database(){}
    public Database(String dbType){
        this(dbType,"127.0.0.1","3306","");
    }
    public Database(String dbType,String db){
        this(dbType,"127.0.0.1","3306",db);
    }
    /**
     *
     * @param dbType    数据库类型 mysql/oracle
     * @param ip        ip
     * @param port      3306
     * @param db        数据库名称 test
     */
    public Database(String dbType,String ip,String port,String db){
        this.dbType=dbType;
        if("MYSQL".equals(dbType.toUpperCase())){
            this.driver="com.mysql.cj.jdbc.Driver";
            this.url=mysqlUrl.replace("[ip]",ip).replace("[port]",port).replace("[db]",db);
        }else{
            this.driver="oracle.jdbc.driver.OracleDriver";
            this.url=oracleUrl.replace("[ip]",ip).replace("[port]",port).replace("[db]",db);
        }
    }
}