package ${pPackage}.entity;

import lombok.*;
import java.io.Serializable;
import javax.persistence.*;

/**
 * ${table.tableComment}实体类
 * author: ${author}
 * 以下是三种解决循环依赖导致栈溢出的情况：
 * 1、不使用@Data，原因不要toString（）方法，在jpa中表依赖，易出现循环依赖导致栈溢出情况，这是解决之一，
 * 2、另一种方法在表依速中加入注解@JsonIgnoreProperties(value = {"${className?uncap_first}"})或@JsonIgnoreProperties(value = {"${className?uncap_first}s"})
 * 3、不要使用自查询——解决办法增加一个主键字段不用关键即可——一般情况这个字段可为空字段，查询时可根据id查询即可
 * 4、双向hashCode和equels方法会导致循环依赖——需要时才在一方使用（手动添加）
 * data: ${.now?date}
 */
@Data
@Entity(name = "${table.name}")
@NoArgsConstructor
@AllArgsConstructor
@org.hibernate.annotations.Table(appliesTo = "${table.name}", comment = "${table.tableComment}")
public class ${className} implements Serializable{
<#list table.columns as column>

    <#if column.isKey==true>
    @Id
    @SequenceGenerator(
            name = "${table.name}_sequence",
            sequenceName = "${table.name}_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "${table.name}_sequence"
    )
    @Column(
            name = "${column.columnName}",
            nullable = <#if column.isNullable==0>false<#else>true</#if>,
            columnDefinition=<#if column.columnJavaType=='String'>"varchar(${column.columnDisplaySize})"<#elseif column.columnJavaType=='Integer'>"int"<#elseif column.columnJavaType=='Long'>"bigint"<#elseif column.columnJavaType=='Boolean'>"bit"<#else>"date"</#if>+" COMMENT '${column.columnComment}'"
    )
    private ${column.columnJavaType} ${column.javaBeanName};
    <#elseif column.isImportedKey==false>
    @Column(
            name = "${column.columnName}",
            nullable = <#if column.isNullable==0>false<#else>true</#if>,
            columnDefinition=<#if column.columnJavaType=='String'>"varchar(${column.columnDisplaySize})"<#elseif column.columnJavaType=='Integer'>"int"<#elseif column.columnJavaType=='Long'>"bigint"<#elseif column.columnJavaType=='Boolean'>"bit"<#else>"date"</#if>+" COMMENT '${column.columnComment}'"
    )
    private ${column.columnJavaType} ${column.javaBeanName};
    <#else>
    /**
     * 外键表——${column.pkTableName}中的字段${column.pkColumnName}，
     * 1、@ManyToOne也可能是@OneToOne情况——@ManyToOne外键表中没有外键，
     *      而@OneToOne在外键表中可能有相对对应的外键也可能没有，
     *      根据业务需要另一方另一方需要带出可设立外键，否则不用设置外键,
     *      代码自动生成为了区分@OneToOne另一端则设立外键,一方面不需要带出另一方时可以删除
     * 2、@ManyToMany需要根据中间表判断—
     * 3、FetchType.LAZY一般设置为赖加载类型，除非需要改为急切类型：EAGER
     * 4、如果要去重复数据需要手动重写hashcode()和equels()方法——否则hashSet()会失效，
     *     但是，双向hashCode和equels方法会导致循环依赖——需要时才在一方使用（手动添加）
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "${column.fkColumnName}")
    private ${column.pkJavaBeanName?cap_first} ${column.javaBeanName};
    </#if>
</#list>

    //没有主键的构造器
    public ${className}(<#list table.columns as column><#if column.isKey==false> <#if column.isImportedKey==false>${column.columnJavaType} ${column.javaBeanName}<#else>${column.pkJavaBeanName?cap_first} ${column.javaBeanName}</#if></#if><#if column_has_next && column_index != 0>,<#else></#if></#list>){
    <#list table.columns as column><#if column.isKey==false> <#if column.isImportedKey==false>this.${column.javaBeanName}=${column.javaBeanName}<#else>this.${column.javaBeanName}=${column.javaBeanName}</#if></#if><#if column_has_next && column_index != 0>;<#else></#if></#list>
    }

}
