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
     * 外键表——${column.pkTableName}中的字段${column.pkColumnName}
     */
    @ManyToOne
    @JoinColumn(name = "${column.fkColumnName}",insertable = false,updatable = false)
    private ${column.pkJavaBeanName?cap_first} ${column.javaBeanName};
    </#if>
</#list>
public ${className}(<#list table.columns as column><#if column.isKey==false> <#if column.isImportedKey==false>${column.columnJavaType} ${column.javaBeanName}<#else>${column.pkJavaBeanName?cap_first} ${column.javaBeanName}</#if></#if><#if column_has_next && column_index != 0>,<#else></#if></#list>){
    <#list table.columns as column><#if column.isKey==false> <#if column.isImportedKey==false>this.${column.javaBeanName}=${column.javaBeanName}<#else>this.${column.javaBeanName}=${column.javaBeanName}</#if></#if><#if column_has_next && column_index != 0>;<#else></#if></#list>
    }

}
