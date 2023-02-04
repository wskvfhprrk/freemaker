package com.hejz.generate.core;

import com.hejz.generate.utils.FileUtils;
import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.Data;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 代码生成器的核心处理类
 *      使用Freemarker完成文件生成
 *          数据模型+模板
 * 数据:
 *      数据模型
 *      模板的位置
 *      生成文件的路径
 */
@Data
public class Generator {
    /**
     * 模版路径
     */
    private String templatePath;
    /**
     * 输出路径
     */
    private String outPath;
    /**
     * Configuration
     */
    private Configuration cfg;

    public Generator(String templatePath, String outPath) throws Exception {
        this.templatePath = templatePath;
        this.outPath = outPath;
        //实例化Configuration
         cfg = new Configuration(Configuration.VERSION_2_3_31);
         //模版加载器
        FileTemplateLoader loader = new FileTemplateLoader(new File(templatePath));
        cfg.setTemplateLoader(loader);
    }

    /**
     * 代码生成——核心方法
     *      1 .扫描模板路径下的所有模板
     *      2.对每个模板进行文件生成(数据模型)
     */
    public void  scanAndGenerator(Map<String,Object> dataModel) throws Exception {
        //1.根据模板文夹下的找到此文件下的所有模版文件
        List<File> files = FileUtils.searchAllFile(new File(templatePath));
        for (File templateFile : files) {
            //2.对每个模板进行文件生成(数据模型)
            executeGenertor(dataModel,templateFile);
        }

    }

    /**
     * 对每个模板进行文件生成
     * @param dataModel 数据模型
     * @param templateFile 模版文件
     */
    private void executeGenertor(Map<String, Object> dataModel, File templateFile) throws Exception {
        //1.文件路径处理     (E:\模板\${path1}${path2}\${path3}\${ClassName}.java)
        // templatePath为: E:\模板\
        String templateFilePath = templateFile.getAbsolutePath().replace(templatePath,"");
        String outFileName = processTemplateString(templateFilePath, dataModel);
        //2、模版处理
        Template template = cfg.getTemplate(templateFilePath);
        template.setOutputEncoding("UTF-8");//指定生成文件字符集编码
        //3、创建文件
        File file = FileUtils.mkdir(outPath, outFileName);
        //4、模版处理
        FileWriter fileWriter = new FileWriter(file);
        template.process(dataModel,fileWriter);
        //关闭
        fileWriter.close();

    }
    public String processTemplateString(String templateString,Map dataModel) throws Exception {
        StringWriter out = new StringWriter();
        Template template = new Template( "ts",new StringReader(templateString), cfg);
        template.process(dataModel,out);
        return out.toString();
    }

    public static void main(String[] args) throws Exception {
        String templatePath="D:\\javaTest\\freemaker\\template\\springboot jpa template";
        String outPath="D:\\javaTest\\freemaker\\generator code";
        Generator generator = new Generator(templatePath, outPath);
        Map<String,Object> dataModel=new HashMap();
        dataModel.put("username","张三");
        generator.scanAndGenerator(dataModel);
    }
}
