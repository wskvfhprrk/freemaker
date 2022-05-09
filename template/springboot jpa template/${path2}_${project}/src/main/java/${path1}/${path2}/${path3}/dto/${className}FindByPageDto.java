package ${pPackage}.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import ${pPackage}.common.Page;
import lombok.Data;
import javax.validation.constraints.NotEmpty;

@Data
public class ${className}FindByPageDto extends Page {
    <#list table.columns as column>
    <#if column.isKey==false>
    @ApiModelProperty(value = "${column.columnComment}")
    private ${column.columnJavaType} ${column.javaBeanName};
    </#if></#list>
}
