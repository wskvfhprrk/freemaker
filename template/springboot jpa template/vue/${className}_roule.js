{
    path: '${className?uncap_first}',
        component: () => import('@/views/dtu/${className?uncap_first}'),
    name: '${className?uncap_first}',
    meta: { title: '${table.tableComment}' },
    roles: ['admin','${className?uncap_first}']
},