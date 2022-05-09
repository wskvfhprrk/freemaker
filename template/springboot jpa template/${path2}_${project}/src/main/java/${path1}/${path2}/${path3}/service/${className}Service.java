<#list table.columns as column>
<#if column.isKey==true>
<#assign type>${column.columnJavaType}</#assign>
<#assign id>${column.javaBeanName}</#assign>
</#if>
</#list>
package ${pPackage}.service;

import ${pPackage}.entity.${className};
import org.springframework.data.domain.Page;

import java.util.List;

/**
 *
 */
public interface ${className}Service {
    ${className} Save(${className} ${className?uncap_first});
    void delete(${type} ${id});
    ${className} findById(${type} ${id});
    Page<${className}> findPage(${className} ${className?uncap_first}, int pageNo,int pageSize);
    List<${className}> findAll(${className} ${className?uncap_first});
}
