<#list table.columns as column>
<#if column.isKey==true>
<#assign type>${column.columnJavaType}</#assign>
<#assign id>${column.javaBeanName}</#assign>
</#if>
</#list>
package ${pPackage}.controller;

import ${pPackage}.common.PageResult;
import ${pPackage}.dto.*;
import ${pPackage}.entity.${className};
import ${pPackage}.service.${className}Service;
import ${pPackage}.common.Result;
import ${pPackage}.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ${table.tableComment}控制器
 * author: ${author}
 * data: ${.now?date}
 */
@RestController
@RequestMapping("${className?uncap_first}")
@Api(tags="${table.tableComment}")
public class ${className}Controller {

    @Autowired
    private ${className}Service ${className?uncap_first}Service;

    @PostMapping()
    @ApiOperation("添加${table.tableComment}")
    public Result create${className}(@Valid @RequestBody ${className}CreateDto dto){
        ${className} ${className?uncap_first}=new ${className}();
        BeanUtils.copyProperties(dto,${className?uncap_first});
        ${className?uncap_first}Service.save(${className?uncap_first});
        return Result.ok();

    }
    @PutMapping
    @ApiOperation("修改${table.tableComment}")
    public Result update${className}(@Valid @RequestBody ${className}UpdateDto dto){
        ${className} ${className?uncap_first}=new ${className}();
        BeanUtils.copyProperties(dto,${className?uncap_first});
        ${className?uncap_first}Service.update(${className?uncap_first});
        return Result.ok();
    }
    @DeleteMapping("/{id}")
    @ApiOperation("删除${table.tableComment}")
    public Result Delete${className}(@PathVariable ${type} ${id}){
        ${className?uncap_first}Service.delete(${id});
        return Result.ok();
    }

    @GetMapping("findPage")
    @ApiOperation("条件查询${table.tableComment}")
    public Result<PageResult<${className}FindByPageVo>> findBypage(${className}FindByPageDto dto){
        ${className} ${className?uncap_first}=new ${className}();
        BeanUtils.copyProperties(dto,${className?uncap_first});
        Page<${className}> ${className?uncap_first}Page = ${className?uncap_first}Service.findPage(dto);
        List<${className}FindByPageVo> list = ${className?uncap_first}Page.getContent().stream().map(d -> {
            ${className}FindByPageVo vo = new ${className}FindByPageVo();
            BeanUtils.copyProperties(d,vo);
            return vo;
        }).collect(Collectors.toList());
        PageResult<${className}FindByPageVo> pages=new PageResult<>();
        pages.setPage(dto.getPage());
        pages.setLimit(dto.getLimit());
        pages.setTotalPage(${className?uncap_first}Page.getTotalPages());
        pages.setTotal(${className?uncap_first}Page.getTotalElements());
        pages.setItems(list);
        return Result.ok(pages);
    }

    @GetMapping
    @ApiOperation("分布条件查询${table.tableComment}所有的数据")
    public Result<List<${className}AllVo>> findAll(${className}AllDto dto){
        List<${className}> dictionaries = ${className?uncap_first}Service.findAll(dto);
        List<${className}AllVo> list = dictionaries.stream().map(d -> {
            ${className}AllVo vo = new ${className}AllVo();
            BeanUtils.copyProperties(d,vo);
            return vo;
        }).collect(Collectors.toList());
        return Result.ok(list);
    }

}
