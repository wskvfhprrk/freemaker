package ${pPackage}.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.NotEmpty;

/**
 * 更新入参——必须带主键
 */
@Data
public class ${className}UpdateDto {
    <#list table.columns as column>
    @ApiModelProperty(value = "${column.columnComment}"<#if column.isNullable==0>,required = true</#if><#if column.columnJavaType=='Integer'>,example = "1"</#if>)
    <#if column.isNullable==0>
    @NotEmpty
    </#if>
    private ${column.columnJavaType} ${column.javaBeanName};
    </#list>
}
