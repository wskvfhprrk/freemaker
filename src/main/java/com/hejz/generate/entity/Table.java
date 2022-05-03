package com.hejz.generate.entity;

import lombok.Data;

import java.util.List;

/**
 * 数据表类
 */
@Data
public class Table {
    /**
     * 表名称
     */
    private String name;
    /**
     * 处理后的表名称
     */
    private String name2;
    /**
     * 介绍
     */
    private String comment;
    /**
     * 主键列
     */
    private String key;
    /**
     * 列集合
     */
    private List<Column> columns;
}
