package ${pPackage}.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.NotEmpty;

@Data
public class ${className}FindAllDto {
    <#list table.columns as column>
    @ApiModelProperty(value = "${column.columnComment}")
    private ${column.columnJavaType} ${column.javaBeanName};
    </#list>
}
