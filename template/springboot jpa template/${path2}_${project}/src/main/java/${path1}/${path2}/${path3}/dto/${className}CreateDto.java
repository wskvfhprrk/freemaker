package ${pPackage}.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;


@Data
@ApiModel
public class ${className}CreateDto {
    <#list table.columns as column>
    <#if column.isKey==false>
    @ApiModelProperty(value = "${column.columnComment}"<#if column.isNullable==0>,required = true</#if>)
    <#if column.isNullable==0>
    @NotEmpty
    </#if>
    private ${column.columnJavaType} ${column.javaBeanName};
    </#if></#list>
}
