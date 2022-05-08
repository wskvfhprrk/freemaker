package ${pPackage}.dto;

import lombok.Data;

/**
 * 更新入参——必须带主键
 */
@Data
public class ${className}UpdateDto {
    <#list table.columns as column>
    @ApiModelProperty(value = "${column.columnComment}"<#if column.isNullable==0>,required = true</#if>)
    <#if column.isNullable==0>
    @NotEmpty
    </#if>
    private ${column.columnJavaType} ${column.javaBeanName};
    </#list>
}
