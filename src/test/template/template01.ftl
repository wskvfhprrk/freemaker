<#--assign定义公共变量-->
<#assign name="zhangsan">
${name}
您好：
${username}
<#--if判断语句-->
<#if flag==1>
flag=1
    <#elseif flag==2>
flag=2
    <#else>
flag=其它
</#if>
<#-- list数组-->
<#list list as item>
   ${item_index} ${item}
</#list>
<#--模版包含 incloud 一般用于公共模版使用-->
<#--<#include "template02.ftl" >-->
