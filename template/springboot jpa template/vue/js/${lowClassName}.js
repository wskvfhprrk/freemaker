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
