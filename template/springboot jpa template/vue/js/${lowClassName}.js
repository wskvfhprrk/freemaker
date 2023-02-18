import request from '@/utils/request'

export function fetchList(query) {
  return request({
    url: '/${className?uncap_first}/findPage',
    method: 'get',
    params: query
  })
}


export function create${className}(data){
  return request({
    url: '/${className?uncap_first}',
    method: 'post',
    data
  })
}

export function update${className}(data){
  return request({
    url: '/${className?uncap_first}',
    method: 'put',
    data
  })
}

export function delete${className}(id) {
  return request({
    url: '/${className?uncap_first}/' + id,
    method: 'delete'
  })
}

<#list table.columns as column><#if column.isImportedKey == true>
export function ${column.javaBeanName}Options(data) {
  return request({
    // todu 修改此处，不要id
    url: '/${column.javaBeanName}/findAll',
    method: 'get',
    params: data
  })
}
</#if></#list>
