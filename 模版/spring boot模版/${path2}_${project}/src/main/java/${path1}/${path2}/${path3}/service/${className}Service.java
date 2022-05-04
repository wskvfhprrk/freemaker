package ${pPackage}.service;

import ${pPackage}.entity.${className};
import org.springframework.data.domain.Page;

import java.util.List;

public interface ${className}Service {
    //根据信息搜索用户
    List<${className}> getAllByOther(${className} ${className?uncap_first});

    // 普通分页
    Page<${className}> getPage(Integer pageNum, Integer pageLimit);

    // 排序分页
    Page<${className}> getPageSort(Integer pageNum, Integer pageLimit);

    //保存用户
    void sava${className}(${className} ${className?uncap_first});
}
