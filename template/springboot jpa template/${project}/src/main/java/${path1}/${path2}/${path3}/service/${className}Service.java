<#list table.columns as column>
<#if column.isKey==true>
<#assign type>${column.columnJavaType}</#assign>
<#assign id>${column.javaBeanName}</#assign>
</#if>
</#list>
package ${pPackage}.service;

import ${pPackage}.dto.${className}FindByPageDto;
import ${pPackage}.entity.${className};
import org.springframework.data.domain.Page;

import java.util.List;

/**
 *
 */
public interface ${className}Service {

    ${className} save(${className} ${className?uncap_first});

    ${className} update(${className} ${className?uncap_first});

    void delete(${type} ${id});

    ${className} findById(${type} ${id});

    Page<${className}> findPage(${className}FindByPageDto dto);

    List<${className}> findAll(${className} ${className?uncap_first});
}
