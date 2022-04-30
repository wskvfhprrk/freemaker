package com.hejz.freemaker;

import freemarker.cache.FileTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * 第一个freemaker程序——了解操作步骤
 */
public class FreemakerTest01 {
    @Test
    public void test01() throws IOException, TemplateException {
        //1.创建FreeMarker的配置类——直接new对象的已经取消了
        Configuration cfg=new Configuration(Configuration.VERSION_2_3_31);
        //2.指定模板加载器:将模板存入缓存中
        // 文件路径加载器
        TemplateLoader ftl=new FileTemplateLoader(new File("src/test/template"));
        cfg.setTemplateLoader(ftl);
        //2.每当应用程序需要模板时（因此您可能会经常这样做，并且来自多个线程）：
        Template myTemplate = cfg.getTemplate("template01.ftl");
        // 4.构造数据模型
        Map map=new HashMap();
        map.put("username", "中国人");
        // 5.文件输出:FileWriter文件输出，PrintWriter输出到哪里
//        myTemplate.process(map, new FileWriter("D:/1.txt"));
        //new PrintWriter(System.out)——输出到控制台上
        myTemplate.process(map,new PrintWriter(System.out));
    }

}
