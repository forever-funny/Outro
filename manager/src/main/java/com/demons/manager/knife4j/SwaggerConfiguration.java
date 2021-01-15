package com.demons.manager.knife4j;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author : Outro
 * Description : swagger在线文档配置
 **/
@Configuration
@EnableSwagger2
@EnableKnife4j
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfiguration {

  @Bean(value = "Api-V1")
  public Docket defaultApi1() {
    return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(apiInfo())
        // 分组名称
        .groupName("1.0版本")
        .select()
        // 这里指定Controller扫描包路径(项目路径也行)
        .apis(RequestHandlerSelectors.basePackage("com.demons.manager.api"))
        .paths(PathSelectors.any())
        .build();
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .title("Manager工程接口界面")
        .description("This is manager's swagger online document")
        .termsOfServiceUrl("http://localhost:8080/")
        .contact(new Contact("forever-funny",
            "https://github.com/forever-funny/Outro",
            "1306205691@qq.com"))
        .version("1.0.0")
        .build();
  }
}
