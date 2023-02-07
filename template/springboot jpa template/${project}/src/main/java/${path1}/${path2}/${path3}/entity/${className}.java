package ${pPackage}.entity;

import lombok.*;
import java.io.Serializable;
import javax.persistence.*;

/**
 * ${table.tableComment}实体类
 * author: ${author}
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
    <#if column.isAutoincrement==true>
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    </#if>
    </#if>
    <#if column.isAutoincrement==true>
    </#if>
    @Column(
            name = "${column.columnName}",
            nullable = <#if column.isNullable==0>false<#else>true</#if>,
            columnDefinition=<#if column.columnJavaType=='String'>"varchar(${column.columnDisplaySize})"<#elseif column.columnJavaType=='Integer'>"int"<#elseif column.columnJavaType=='Long'>"bigint"<#elseif column.columnJavaType=='Boolean'>"bit"<#else>"date"</#if>+" COMMENT '${column.columnComment}'"
    )
    private ${column.columnJavaType} ${column.javaBeanName};
    </#list>
    <#if table.importedKeys??>
    <#list table.importedKeys as pk>
    /**
     * 外键表——${pk.pkTableName}中的字段${pk.pkColumnName}
     */
    @ManyToOne
    @JoinColumn(name = "${pk.fkColumnName}",insertable = false,updatable = false)
    private ${pk.javaBeanName?cap_first} ${pk.javaBeanName};
    </#list>
    </#if>
}
