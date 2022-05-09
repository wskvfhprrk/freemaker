package ${pPackage}.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ${className}AllVo {
    <#list table.columns as column>
    <#if column.isKey==false>
    @ApiModelProperty(value = "${column.columnComment}")
    <#if column.columnJavaType=='java.util.Date'>
    @com.fasterxml.jackson.annotation.JsonFormat(pattern ="yyyy-MM-dd HH:mm:ss",timezone ="GMT+8")
    </#if>
    private ${column.columnJavaType} ${column.javaBeanName};
    </#if></#list>
}
