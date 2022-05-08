package ${pPackage}.service;

import ${pPackage}.entity.${className};
import org.springframework.data.domain.Page;

import java.util.List;

/**
 *
 */
public interface ${className}Service {
    ${className} Save(${className} ${className?uncap_first});
    void delete(Long id);
    ${className} findById(Long id);
    Page<${className}> findPage(${className} ${className?uncap_first}, int pageNo,int pageSize);
    List<${className}> findAll(${className} ${className?uncap_first});
}
