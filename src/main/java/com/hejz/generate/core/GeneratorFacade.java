package com.hejz.generate.core;

import com.hejz.generate.entity.Database;
import com.hejz.generate.entity.Settings;
import com.hejz.generate.entity.Table;
import com.hejz.generate.utils.DataBaseUtils;
import com.hejz.generate.utils.PropertiesUtils;
import com.hejz.generate.utils.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 1.采集用户UI界面输入的数据
 *      横板位置
 *      代码生成路径
 *      工程配置对象setting
 *      数据库对象  DataBase
 * 2.准备数据模型
 *      1.自定义配置
 *      2.元数据
 *      3. setting
 * 3.调用核心处理类完成代码生成工作
 *      方法: Generator
 */
public class GeneratorFacade {
    //模板位置
        private String templatePath;
        //代码生成路径
        private String outPath;
        //工程配置对象
        private Settings settings;
        //数据库对象
        private Database db;
        private Generator generator;

    /**
     * 1.准备数据模型
     * 2.调用核心处理类完成代码生成工作
     */
        public GeneratorFacade(String templatePath, String outPath, Settings settings, Database db) throws Exception {
            this.templatePath = templatePath;
            this.outPath = outPath;
            this.settings = settings;
            this.db = db;
            //实例化generator
            generator=new Generator(templatePath,outPath);
        }

    /**
     * 1.准备数据模型
     * 2.调用核心处理类完成代码生成工作
     */
    public void generatorByDataBase() throws Exception {
            List<Table> tables = DataBaseUtils.getDbInfo(db);
                //对每一个table进行表生成
                for(Table table:tables) {
                    //数据模型
                    Map<String, Object> dataModel = getDataModel(table);
                    //测试
//                    dataModel.forEach((k,v)-> System.out.println(k+"--->"+v));
//                    System.out.println("______________________________________");
                    //调用代码生成方法，把数据模型传过去，进行生成
                    generator.scanAndGenerator(dataModel);
                }
        }
        /**
         * 根据table对象获取数据模型
         * @param table
         * @return
         */
        private Map<String,Object> getDataModel(Table table) {
            Map<String,Object> dataModel=new HashMap<>();
            //1、自定义配置
            dataModel.putAll(PropertiesUtils.customMap);
            //2、元数据
            dataModel.put("table",table);
            //3、settings——利用反射机制获取
            dataModel.putAll(settings.getSettingMap());
//            dataModel.putAll(StringUtils.conver2Map(settings));
            //类名
            dataModel.put("className",table.getClassName());
            return dataModel;
        }
    //可作为生成代码的入口即可
    public static void main(String[] args) throws Exception {
        Settings settings = new Settings("", "com.hejz", "模版生成系统", "hejz");
        Database db=new Database("MYSQL","demo");
        db.setPassword("123456");
        db.setUserName("root");
        String templatePaeh="C:\\Users\\Administrator\\IdeaProjects\\freemaker\\模版";
        String outPath="C:\\Users\\Administrator\\IdeaProjects\\freemaker\\生成代码";
        GeneratorFacade generatorFacade = new GeneratorFacade(templatePaeh, outPath, settings, db);
        generatorFacade.generatorByDataBase();
    }
}
