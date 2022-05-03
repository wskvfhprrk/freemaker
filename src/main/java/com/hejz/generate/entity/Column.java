package com.hejz.generate.entity;

import lombok.Data;

/**
 * 表中列参数类
 */
@Data
public class Column {
    /**
     * 列名称
     */
    private String columnName;
    /**
     * 列名称(处理后的列名称)
     */
    private String columnName2;
    /**
     * 列类型
     */
    private String columnType;
    /**
     * 列数据库类型
     */
    private String columnDbType;
    /**
     * 列备注
     */
    private String columnComment;
    /**
     * 是否是主键
     */
    private String columnKey;
    /**
     * 最大数据长度
     */
    private int length;
}
