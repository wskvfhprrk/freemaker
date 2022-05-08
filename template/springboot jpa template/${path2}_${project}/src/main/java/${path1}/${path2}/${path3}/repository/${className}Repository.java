package ${pPackage}.repository;

import ${pPackage}.entity.${className};
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * ${table.tableComment} dao层
 * author: ${author}
 * data: ${.now?date}
 */
public interface ${className}Repository extends JpaRepository<${className},<#list table.columns as column><#if column.isKey==true>${column.columnJavaType}</#if></#list>>,JpaSpecificationExecutor<${className}> {
}
