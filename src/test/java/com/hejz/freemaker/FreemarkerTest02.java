package com.hejz.freemaker;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.Test;

import java.io.PrintWriter;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

/**
 * 字符串模版
 */
public class FreemarkerTest02 {
    @Test
    public void  test() throws Exception {
        //1、创建对象
        Configuration cfg=new Configuration(Configuration.VERSION_2_3_31);
        //2、指定加载器
        cfg.setTemplateLoader(new StringTemplateLoader());
        //3、创建字符串模版
        String templateString="欢迎您：${username}";
        Template template = new Template("name1",new StringReader(templateString),cfg);
        //4、构造数据
        Map<String,Object> map = new HashMap<>();
        map.put("username","张三");
        //5、处理模版输入
        template.process(map,new PrintWriter(System.out));
    }
}
