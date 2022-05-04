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
@Entity
@Table(name = "${table.name}")
public class ${className} implements Serializable{
    <#list table.columns as column>

    /**
     * ${column.columnComment}
     */
    <#if column.isKey==true>
    @Id
    </#if>
    <#if column.isAutoincrement==true>
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    </#if>
    private ${column.columnJavaType} ${column.javaBeanName};
    </#list>
}
