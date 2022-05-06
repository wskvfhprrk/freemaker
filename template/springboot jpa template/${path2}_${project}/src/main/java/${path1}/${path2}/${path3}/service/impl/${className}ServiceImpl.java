package ${pPackage}.service.impl;

import ${pPackage}.entity.${className};
import ${pPackage}.repository.${className}Repository;
import ${pPackage}.service.${className}Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ${className}ServiceImpl implements ${className}Service {
    @Autowired
    private ${className}Repository ${className?uncap_first}Repository;

    @Override
    public List<${className}> getAllByOther(${className} ${className?uncap_first}) {
        ${className?uncap_first}.setUsername("he");
        List<${className}> ${className?uncap_first}s = ${className?uncap_first}Repository.findAll(Example.of(${className?uncap_first}));
        return ${className?uncap_first}s;
    }

    @Override
    public Page<${className}> getPage(Integer pageNum, Integer pageLimit) {
        Pageable pageable =new PageRequest(pageNum - 1,pageLimit);
        return ${className?uncap_first}Repository.findAll(pageable);
    }

    @Override
    public Page<${className}> getPageSort(Integer pageNum, Integer pageLimit) {
        Sort sort = new Sort(Sort.Direction.DESC,"old");
        Pageable pageable =new PageRequest(pageNum - 1, pageLimit, sort);
        return ${className?uncap_first}Repository.findAll(pageable);
    }
    @Override
    public void sava${className}(${className} ${className?uncap_first}) {
        ${className?uncap_first}Repository.save(${className?uncap_first});
    }
}
