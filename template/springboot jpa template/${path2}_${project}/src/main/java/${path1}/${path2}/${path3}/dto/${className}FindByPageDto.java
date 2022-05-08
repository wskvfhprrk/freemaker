package ${pPackage}.dto;

import ${pPackage}.common.Page;
import lombok.Data;

@Data
public class ${className}FindByPageDto extends Page {
    <#list table.columns as column>
    <#if column.isKey==false>
    @ApiModelProperty(value = "${column.columnComment}")
    private ${column.columnJavaType} ${column.javaBeanName};
    </#if></#list>
}
