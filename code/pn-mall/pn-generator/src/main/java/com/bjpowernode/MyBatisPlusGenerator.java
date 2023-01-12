package com.bjpowernode;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.TemplateType;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MyBatisPlusGenerator {

    public static final String URL = "jdbc:mysql://192.168.100.21:3306/pn_mall?useSSL=false";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "123456";
    // 代码输出位置
    public static final String OUT_PUT_DIR = "D:\\mybatis";
    // 要过滤的表前缀
    public static final String TABLE_PRE_FIX = "pms_";

    public static void main(String[] args) {
        FastAutoGenerator.create(URL, USERNAME, PASSWORD)
                // 全局配置
                .globalConfig((scanner, builder) -> builder.author("jack")
                        // 使用 java.util.date
                        .dateType(DateType.ONLY_DATE)
                        // 指定输出目录
                        .outputDir(OUT_PUT_DIR)
                )
                // 包配置
                .packageConfig((scanner, builder) -> builder.parent(scanner.apply("请输入包名："))
                        // .moduleName(scanner.apply("请输入模块名："))
                        // 设置mapperXml生成路径
                        .pathInfo(Collections.singletonMap(OutputFile.xml, OUT_PUT_DIR))
                )
                // 策略配置
                .strategyConfig((scanner, builder) -> builder
                        .addInclude(getTables(scanner.apply("请输入表名，多个英文逗号分隔，所有输入 all")))
                        .addTablePrefix(TABLE_PRE_FIX) // 过滤表前缀
                        .serviceBuilder().formatServiceFileName("%sService")
                        // 生成Lombok注解
                        .entityBuilder().enableLombok()
                        .mapperBuilder().enableBaseResultMap().enableBaseColumnList()
                        .build()
                )
                // 模版配置
                .templateConfig((scanner, builder) -> builder
                        // 不生成Controller层
                        .disable(TemplateType.CONTROLLER)
                        .build()
                )
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }

    // 处理 all 情况
    protected static List<String> getTables(String tables) {
        return "all".equals(tables) ? Collections.emptyList() : Arrays.asList(tables.split(","));
    }
}