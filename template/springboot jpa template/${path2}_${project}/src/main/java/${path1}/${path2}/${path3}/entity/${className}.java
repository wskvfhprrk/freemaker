package ${pPackage}.entity;

import lombok.Data;
import java.io.Serializable;
import javax.persistence.*;

/**
 * ${table.tableComment}实体类
 * author: ${author}
 * data: ${.now?date}
 */
@Data
@Entity(name = "${table.name}")
public class ${className} implements Serializable{
    <#list table.columns as column>

    <#if column.isKey==true>
    @Id
    <#if column.isAutoincrement==true>
    @SequenceGenerator(
            name = "${table.name}_sequence",
            sequenceName = "${table.name}_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "${table.name}_sequence"
    )
    </#if>
    </#if>
    <#if column.isAutoincrement==true>
    </#if>
    @Column(
            name = "${column.columnName}",
            nullable = <#if column.isNullable==0>false<#else>true</#if>,
            columnDefinition=<#if column.columnJavaType=='String'>"varchar(${column.columnDisplaySize})"<#elseif column.columnJavaType=='Integer'>"int"<#elseif column.columnJavaType=='Long'>"bigint"<#else>"date"</#if>+" COMMENT '${column.columnComment}'"
    )
    private ${column.columnJavaType} ${column.javaBeanName};
    </#list>
}
