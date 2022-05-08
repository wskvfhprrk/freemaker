package ${pPackage}.vo;

import lombok.Data;

@Data
public class ${className}AllVo {
    <#list table.columns as column>
    <#if column.isKey==false>
    @ApiModelProperty(value = "${column.columnComment}")
    private ${column.columnJavaType} ${column.javaBeanName};
    </#if></#list>
}
