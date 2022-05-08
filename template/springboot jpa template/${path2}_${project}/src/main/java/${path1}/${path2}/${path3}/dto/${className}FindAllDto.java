package ${pPackage}.dto;

import lombok.Data;

@Data
public class ${className}FindAllDto {
    <#list table.columns as column>
    <#if column.isKey==false>
    @ApiModelProperty(value = "${column.columnComment}")
    private ${column.columnJavaType} ${column.javaBeanName};
    </#if></#list>
}
