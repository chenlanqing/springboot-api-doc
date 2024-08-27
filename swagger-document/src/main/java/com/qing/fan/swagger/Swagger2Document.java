package com.qing.fan.swagger;

import io.github.swagger2markup.GroupBy;
import io.github.swagger2markup.Language;
import io.github.swagger2markup.Swagger2MarkupConfig;
import io.github.swagger2markup.Swagger2MarkupConverter;
import io.github.swagger2markup.builder.Swagger2MarkupConfigBuilder;
import io.github.swagger2markup.markup.builder.MarkupLanguage;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author QingFan
 * @version 1.0.0
 * @date 2024年08月27日 21:02
 */
public class Swagger2Document {

    public static void main(String[] args) throws Exception {
        // 用于转换的swagger.json可以来自于本地文件，也可以来http 获取
        // Path localSwaggerFile = Paths.get("/path/to/swagger.json");
        URL remoteSwaggerFile = new URL("http://localhost:8080/v2/api-docs");

        // asciidoc 输出文件夹
        Path outputDir = Paths.get("./doc/asciidoc/generated");

        // 转换配置设置
        // 配置书写方式有多种，可参考 Swagger2MarkupConfigBuilder 构造函数
        // 此处可配置输出格式为 markdown 文档
        Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder()
                .withMarkupLanguage(MarkupLanguage.ASCIIDOC)
                .withOutputLanguage(Language.ZH)
                .withPathsGroupedBy(GroupBy.TAGS)
                .build();

        Swagger2MarkupConverter.from(remoteSwaggerFile)
                .withConfig(config)
                .build()
                .toFolder(outputDir);
    }
}
