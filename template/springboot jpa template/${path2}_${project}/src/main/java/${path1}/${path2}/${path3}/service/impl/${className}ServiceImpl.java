<#list table.columns as column>
<#if column.isKey==true>
<#assign type>${column.columnJavaType}</#assign>
<#assign id>${column.javaBeanName}</#assign>
</#if>
</#list>
package ${pPackage}.service.impl;

import ${pPackage}.entity.${className};
import ${pPackage}.repository.${className}Repository;
import ${pPackage}.service.${className}Service;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ${className}ServiceImpl implements ${className}Service {

    @Autowired
    private ${className}Repository ${className?uncap_first}Repository;

    @Override
    public ${className} Save(${className} ${className?uncap_first}) {
        return ${className?uncap_first}Repository.save(${className?uncap_first});
    }

    @Override
    public void delete(${type} ${id}) {
        ${className?uncap_first}Repository.deleteById( ${id});
    }

    @Override
    public ${className} findById(${type} ${id}) {
       return ${className?uncap_first}Repository.findById( ${id}).orElse(null);
    }

    @Override
    public Page<${className}> findPage(${className} ${className?uncap_first}, int pageNo,int pageSize) {
        Specification<${className}> sp= (root, query, cb)-> {
            List<Predicate> predicates = new ArrayList<>();
            <#list table.columns as column>
            <#if column.columnJavaType=='String'>
            if(StringUtils.isNotBlank(${className?uncap_first}.get${column.javaBeanName?cap_first}())) {
                predicates.add(cb.like(root.get("${column.javaBeanName}"), "%"+${className?uncap_first}.get${column.javaBeanName?cap_first}()+"%"));
            }
            <#elseif (column.columnJavaType=='Long'||column.columnJavaType=='Integer')>
            if(${className?uncap_first}.get${column.javaBeanName?cap_first}()!=null && ${className?uncap_first}.get${column.javaBeanName?cap_first}()!=0) {
            predicates.add(cb.equal(root.get("${column.javaBeanName}"), ${className?uncap_first}.get${column.javaBeanName?cap_first}()));
            }
            <#elseif (column.columnJavaType=='java.util.Date')>
            if(${className?uncap_first}.get${column.javaBeanName?cap_first}()!=null) {
            predicates.add(cb.equal(root.get("${column.javaBeanName}"), ${className?uncap_first}.get${column.javaBeanName?cap_first}()));
            }
            <#else>
            if(StringUtils.isNotEmpty(${className?uncap_first}.get${column.javaBeanName?cap_first}())) {
                predicates.add(cb.equal(root.get("${column.javaBeanName}"), ${className?uncap_first}.get${column.javaBeanName?cap_first}()));
            }
            </#if>
            </#list>
            Predicate[] andPredicate = new Predicate[predicates.size()];
            return cb.and(predicates.toArray(andPredicate));
        };
        Page<${className}> all = ${className?uncap_first}Repository.findAll(sp, PageRequest.of(pageNo,pageSize));
        System.out.println(all);
        return all;
    }

    @Override
    public List<${className}> findAll(${className} ${className?uncap_first}) {
        Specification<${className}> spec= (root, query, cb)-> {
            List<Predicate> predicates = new ArrayList<>();
            <#list table.columns as column>
            <#if column.columnJavaType=='String'>
            if(StringUtils.isNotBlank(${className?uncap_first}.get${column.javaBeanName?cap_first}())) {
            predicates.add(cb.like(root.get("${column.javaBeanName}"), "%"+${className?uncap_first}.get${column.javaBeanName?cap_first}()+"%"));
            }
            <#elseif (column.columnJavaType=='Long'||column.columnJavaType=='Integer')>
            if(${className?uncap_first}.get${column.javaBeanName?cap_first}()!=null && ${className?uncap_first}.get${column.javaBeanName?cap_first}()!=0) {
            predicates.add(cb.equal(root.get("${column.javaBeanName}"), ${className?uncap_first}.get${column.javaBeanName?cap_first}()));
            }
            <#elseif (column.columnJavaType=='java.util.Date')>
            if(${className?uncap_first}.get${column.javaBeanName?cap_first}()!=null) {
            predicates.add(cb.equal(root.get("${column.javaBeanName}"), ${className?uncap_first}.get${column.javaBeanName?cap_first}()));
            }
            <#else>
            if(StringUtils.isNotEmpty(${className?uncap_first}.get${column.javaBeanName?cap_first}())) {
            predicates.add(cb.equal(root.get("${column.javaBeanName}"), ${className?uncap_first}.get${column.javaBeanName?cap_first}()));
            }
            </#if>
            </#list>
            Predicate[] andPredicate = new Predicate[predicates.size()];
            return cb.and(predicates.toArray(andPredicate));
        };
        List<${className}> all = ${className?uncap_first}Repository.findAll(spec);
        return all;
    }
}
