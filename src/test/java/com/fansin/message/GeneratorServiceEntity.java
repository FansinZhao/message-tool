package com.fansin.message;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 测试生成代码
 * </p>
 *
 * @author K神
 * @date 2017/12/18
 */
public class GeneratorServiceEntity {

    String packageName = "com.fansin.message";
    String dbUrl = "jdbc:mysql://172.16.0.2:3306/test";
    String user = "root";
    String passwd = "root";
    String outputDir = "/home/zhaofeng/IdeaProjects/message-tool/src/main/java";
    String xmlDir = "/home/zhaofeng/IdeaProjects/message-tool/src/main/resources/mybatis/";
    String author = "zhaofeng";


    @Test
    public void generateCode() {

        boolean serviceNameStartWithI = false;//user -> UserService, 设置成true: user -> IUserService
        //TODO 添加表名
        generateByTables(serviceNameStartWithI, packageName, "Person");
    }

    private void generateByTables(boolean serviceNameStartWithI, String packageName, String... tableNames) {
        GlobalConfig config = new GlobalConfig();
//        String dbUrl = "jdbc:mysql://192.168.2.234:3306/pcs";
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL)
                .setUrl(dbUrl)
                .setUsername(user)
                .setPassword(passwd)
                .setDriverName("com.mysql.jdbc.Driver");
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig
                .setCapitalMode(true)
                .setEntityLombokModel(false)
                .setDbColumnUnderline(true)
                .setNaming(NamingStrategy.underline_to_camel)
                .setInclude(tableNames);//修改替换成你需要的表名，多个表名传数组
        config.setActiveRecord(false)
                .setAuthor(author)
                .setOutputDir(outputDir)
                .setFileOverride(true);
        if (!serviceNameStartWithI) {
            config.setServiceName("%sService");
        }
        AutoGenerator autoGenerator = new AutoGenerator();
        List<FileOutConfig> fileOutConfigList = new ArrayList<>(1);
        fileOutConfigList.add(new FileOutConfig("/templates/mapper.xml.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return xmlDir + tableInfo.getEntityName() + "Mapper.xml";
            }
        });
        new AutoGenerator().setGlobalConfig(config)
                .setDataSource(dataSourceConfig)
                .setStrategy(strategyConfig)
                .setPackageInfo(
                        new PackageConfig()
                                .setParent(packageName)
                                .setController("controller")
                                .setEntity("entity")
                ).setTemplate(new TemplateConfig()
                //关闭默认xml
                .setXml(null))
                .setCfg(new InjectionConfig() {
                    @Override
                    public void initMap() {
                        //自定义配置
                        Map<String, Object> map = new HashMap<String, Object>();
                        this.setMap(map);
                    }
                }.setFileOutConfigList(fileOutConfigList)).execute();
    }

    private void generateByTables(String packageName, String... tableNames) {
        generateByTables(true, packageName, tableNames);
    }
}
