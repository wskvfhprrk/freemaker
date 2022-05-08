package ${pPackage}.vo;

import lombok.Data;

@Data
public class ${className}UpdateVo {
    <#list table.columns as column>
    @ApiModelProperty(value = "${column.columnComment}")
    private ${column.columnJavaType} ${column.javaBeanName};
    </#list>
}
