package ${pPackage}.vo;

import ${pPackage}.common.Page;
import lombok.Data;

@Data
public class ${className}FindByPageVo extends Page {
    <#list table.columns as column>
    @ApiModelProperty(value = "${column.columnComment}")
    private ${column.columnJavaType} ${column.javaBeanName};
    </#list>
}
